package util;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;

public class FileUtils {

    /**
     * 根据Url下载文件到本地
     */
    public static File urlToFile(String urlList, String ext) {
        File file = null;
        String fileName = UUID.randomUUID().toString() + ext;
        try {
            // 构造URL
            URL url = new URL(urlList);
            HostnameVerifier hv = (urlHostName, session) -> true;
            HttpsURLConnection.setDefaultHostnameVerifier(hv);
            // 打开连接
            URLConnection con = url.openConnection();
            con.setRequestProperty("User-Agent", "Mozilla/31.0 (compatible; MSIE 10.0; Windows NT; DigExt)");
            //设置请求超时为5s
            con.setConnectTimeout(5 * 1000);
            // 输入流
            InputStream is = con.getInputStream();

            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            // 输出的文件流
            file = new File(fileName);
            OutputStream os = new FileOutputStream(file);
            // 开始读取
            while ((len = is.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
            // 完毕，关闭所有链接
            os.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

}
