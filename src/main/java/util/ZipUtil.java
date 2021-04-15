package util;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.log4j.Log4j;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import service.CosRemoteService;

import java.io.*;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Log4j
public class ZipUtil {
    
    /**
     * 使用GBK编码可以避免压缩中文文件名乱码 
     */
    private static final String CHINESE_CHARSET = "GBK";

    /**
     * 文件读取缓冲区大小 
     */
    private static final int CACHE_SIZE = 1024;

    private ZipUtil(){
        // 私用构造主法.因为此类是工具类.
    }

    private static Map<String, String> successMap = Maps.newHashMap();

    private static List<String> failures = Lists.newArrayList();

    private static CosRemoteService cosRemoteService = new CosRemoteService();

    public static Integer count = 0;

    /**
     * <p> 
     * 压缩文件 
     * </p> 
     *
     * @param sourceFolder 需压缩文件 或者 文件夹 路径
     * @param zipFilePath 压缩文件输出路径 
     * @throws Exception
     */
    public static void zip(String sourceFolder, String zipFilePath) throws Exception {
        log.debug("开始压缩 ["+sourceFolder+"] 到 ["+zipFilePath+"]");
        OutputStream out = new FileOutputStream(zipFilePath);
        BufferedOutputStream bos = new BufferedOutputStream(out);
        ZipOutputStream zos = new ZipOutputStream(bos);
        // 解决中文文件名乱码  
        File file = new File(sourceFolder);
        String basePath = null;
        if (file.isDirectory()) {
            basePath = file.getPath();
        } else {
            basePath = file.getParent();
        }
        zipFile(file, basePath, zos);
        zos.closeEntry();
        zos.close();
        bos.close();
        out.close();
        log.debug("压缩 ["+sourceFolder+"] 完成！");
    }

    /**
     * <p> 
     * 压缩文件 
     * </p> 
     *
     * @param sourceFolders 一组 压缩文件夹 或 文件
     * @param zipFilePath 压缩文件输出路径 
     * @throws Exception
     */
    public static void zip(String[] sourceFolders, String zipFilePath) throws Exception {
        OutputStream out = new FileOutputStream(zipFilePath);
        BufferedOutputStream bos = new BufferedOutputStream(out);
        ZipOutputStream zos = new ZipOutputStream(bos);

        for (int i = 0; i < sourceFolders.length; i++) {
            log.debug("开始压缩 ["+sourceFolders[i]+"] 到 ["+zipFilePath+"]");
            File file = new File(sourceFolders[i]);
            String basePath = null;
            basePath = file.getParent();
            zipFile(file, basePath, zos);
        }

        zos.closeEntry();
        zos.close();
        bos.close();
        out.close();
    }

    /**
     * <p> 
     * 递归压缩文件 
     * </p> 
     *
     * @param parentFile
     * @param basePath
     * @param zos
     * @throws Exception
     */
    private static void zipFile(File parentFile, String basePath, ZipOutputStream zos) throws Exception {
        File[] files = new File[0];
        if (parentFile.isDirectory()) {
            files = parentFile.listFiles();
        } else {
            files = new File[1];
            files[0] = parentFile;
        }
        String pathName;
        InputStream is;
        BufferedInputStream bis;
        byte[] cache = new byte[CACHE_SIZE];
        for (File file : files) {
            if (file.isDirectory()) {
                log.debug("目录："+file.getPath());

                basePath = basePath.replace('\\', '/');
                if(basePath.substring(basePath.length()-1).equals("/")){
                    pathName = file.getPath().substring(basePath.length()) + "/";
                }else{
                    pathName = file.getPath().substring(basePath.length() + 1) + "/";
                }

                zos.putNextEntry(new ZipEntry(pathName));
                zipFile(file, basePath, zos);
            } else {
                pathName = file.getPath().substring(basePath.length()) ;
                pathName = pathName.replace('\\', '/');
                if(pathName.substring(0,1).equals("/")){
                    pathName = pathName.substring(1);
                }

                log.debug("压缩："+pathName);

                is = new FileInputStream(file);
                bis = new BufferedInputStream(is);
                zos.putNextEntry(new ZipEntry(pathName));
                int nRead = 0;
                while ((nRead = bis.read(cache, 0, CACHE_SIZE)) != -1) {
                    zos.write(cache, 0, nRead);
                }
                bis.close();
                is.close();
            }
        }
    }

    /**
     * 解压zip文件
     *
     * @param zipFileName
     *            待解压的zip文件路径，例如：c:\\a.zip
     *
     * @param outputDirectory
     *            解压目标文件夹,例如：c:\\a\
     */
    public static void unZip(String zipFileName, String outputDirectory)
            throws Exception {

        Map<String, String> successMap = Maps.newHashMap();
        List<String> failures = Lists.newArrayList();
        int count = 0;

        log.debug("开始解压 ["+zipFileName+"] 到 ["+outputDirectory+"]");
        ZipFile zipFile = new ZipFile(zipFileName);

        try {

            Enumeration<?> e = zipFile.getEntries();

            ZipEntry zipEntry = null;

            createDirectory(outputDirectory, "");

            while (e.hasMoreElements()) {

                zipEntry = (ZipEntry) e.nextElement();

                log.debug("解压：" + zipEntry.getName());

                if (zipEntry.isDirectory()) {

                    String name = zipEntry.getName();

                    name = name.substring(0, name.length() - 1);

                    File f = new File(outputDirectory + File.separator + name);

                    f.mkdir();

                    log.debug("创建目录：" + outputDirectory + File.separator + name);

                } else {

                    String fileName = zipEntry.getName();

                    fileName = fileName.replace('\\', '/');

                    if (fileName.indexOf("/") != -1) {

                        createDirectory(outputDirectory, fileName.substring(0,
                                fileName.lastIndexOf("/")));

                        fileName = fileName.substring(
                                fileName.lastIndexOf("/") + 1,
                                fileName.length());

                    }

                    if (fileName.contains("._")) {
                        // 过滤临时文件
                        continue;
                    }

                    File f = new File(outputDirectory + File.separator
                            + zipEntry.getName());

                    f.createNewFile();

                    upload(f, fileName);

                    InputStream in = zipFile.getInputStream((ZipArchiveEntry) zipEntry);

                    FileOutputStream out = new FileOutputStream(f);

                    byte[] by = new byte[1024];

                    int c;

                    while ((c = in.read(by)) != -1) {

                        out.write(by, 0, c);

                    }

                    in.close();

                    out.close();

                }

            }
            log.debug("解压 ["+zipFileName+"] 完成！");

        } catch (Exception ex) {

            System.out.println(ex.getMessage());

        } finally {
            zipFile.close();
        }

    }

    private static void upload(File f, String name) {
        boolean isPhoto = Lists.newArrayList("jpg", "png", "GIF", "BMP", "pdf").stream().map(String::toLowerCase)
                .anyMatch(name::contains);
        if (!isPhoto) {
            log.error(name + "不是图片！");
            return;
        }
        if (cosRemoteService != null) {
            try {

                String url = cosRemoteService.uploadPublicFile(f, "", true);
                if (!url.contains("http://public-10000230.file.myqcloud.com/")) {
                    log.error("url 错误: " + url);
                }
                successMap.put(name, url.replace("http://public-10000230.file.myqcloud.com/", "https://pubimg.xingren.com/"));
                if (count % 100 == 0) {
                    try {
                        count++;
                        Thread.sleep(1000);
                    } catch (InterruptedException ie) {
                        ie.printStackTrace();
                    }
                }
            } catch (Exception ex) {
                failures.add(name);
            }
        }
    }

    /**
     * 创建目录
     * @author hezhao
     * @Time   2017年7月28日 下午7:10:05
     * @param directory
     * @param subDirectory
     */
    private static void createDirectory(String directory, String subDirectory) {

        String dir[];

        File fl = new File(directory);

        try {

            if (subDirectory == "" && fl.exists() != true) {

                fl.mkdir();

            } else if (subDirectory != "") {

                dir = subDirectory.replace('\\', '/').split("/");

                for (int i = 0; i < dir.length; i++) {

                    File subFile = new File(directory + File.separator + dir[i]);

                    if (subFile.exists() == false)

                        subFile.mkdir();

                    directory += File.separator + dir[i];

                }

            }

        } catch (Exception ex) {

            System.out.println(ex.getMessage());

        }

    }

    /**
     * 无需解压直接读取Zip文件和文件内容
     * @author hezhao
     * @Time   2017年7月28日 下午3:23:10
     * @param file 文件
     * @throws Exception
     */
    public static void readZipFile(String file) throws Exception {
        java.util.zip.ZipFile zipFile = new java.util.zip.ZipFile(file);
        InputStream in = new BufferedInputStream(new FileInputStream(file));
        java.util.zip.ZipInputStream zin = new java.util.zip.ZipInputStream(in);
        java.util.zip.ZipEntry ze;
        while ((ze = zin.getNextEntry()) != null) {
            if (ze.isDirectory()) {
            } else {
                log.info("file - " + ze.getName() + " : "
                        + ze.getSize() + " bytes");
                long size = ze.getSize();
                if (size > 0) {
                    BufferedReader br = new BufferedReader(
                            new InputStreamReader(zipFile.getInputStream(ze)));
                    String line;
                    while ((line = br.readLine()) != null) {
                        System.out.println(line);
                    }
                    br.close();
                }
                System.out.println();
            }
        }
        zin.closeEntry();
    }


    public static void main(String[] args) throws Exception {
        try {
          unZip("/Users/yanwenbo/Desktop/y-workspace/零散文件/pg.zip", "/Users/yanwenbo/Desktop/y-workspace/零散文件/pg");
            System.out.println("上传完成，总共上传：" + count);
            System.out.println("上传完成，成功：" + successMap.size());
            System.out.println("上传完成，失败：" + failures.size());
            createCSVFile(successMap);
            writeFileContext(failures);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeFileContext(List<String>  strings) throws Exception {
//        if (strings.isEmpty()) {
//            return;
//        }
        File file = new File("/Users/yanwenbo/Desktop/workspace/test/failure.txt");
        //如果没有文件就创建
        if (!file.isFile()) {
            file.createNewFile();
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter("/Users/yanwenbo/Desktop/workspace/test/failure.txt"));
        for (String l : strings) {
            writer.write(l + "\r\n");
        }
        writer.close();
    }

    public static void createCSVFile(Map<String, String> data) throws IOException {
        String[] head = { "文件名", "图片URL"};
        FileWriter out = new FileWriter("name_url_mapping.csv");
        try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT
                .withHeader(head))) {
            data.forEach((author, title) -> {
                try {
                    printer.printRecord(author, title);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

}