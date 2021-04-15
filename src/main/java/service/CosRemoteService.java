package service;

import com.alibaba.fastjson.JSONObject;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.request.UploadFileRequest;
import com.qcloud.cos.sign.Credentials;
import lombok.extern.log4j.Log4j;
import util.FileUtils;

import java.io.File;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Log4j
public class CosRemoteService {

    public COSClient cosClient() {
        // 客户端设置（区域）
        ClientConfig config = new ClientConfig();
        config.setRegion("sh");

        Long appId = 10000230L;
        String secretId = "AKIDSy9NYwGTFUMVXq83N73WlDSCD2ibepld";
        String secretKey = "54UTAJ6ZZls1LWWjbbEAwjjqsNTw5bH9";

        // 密钥
        Credentials credentials = new Credentials(appId, secretId, secretKey);

        return new COSClient(config, credentials);
    }

    /**
     * 根据url上传文件到COS
     */
    public String uploadUrl(String url, String ext) {
        File file = FileUtils.urlToFile(url, ext);
        return uploadFile(file, "/pharmacy/prescription/" + getCurrentTimeFormat(), true);
    }

    /**
     * 上传文件到COS
     */
    public String uploadFile(File file, String dir, Boolean isDelete) {
        String filePath = dir + "/" + file.getName();
        String url = "";
        try {
            String result = cosClient().uploadFile(
                    new UploadFileRequest("privates", filePath, Files.readAllBytes(file.toPath())));
            JSONObject jsonObject = JSONObject.parseObject(result);
            checkError(jsonObject);
            url = jsonObject.getJSONObject("data").getString("access_url");
        } catch (Exception e) {
            log.error("调用腾讯COS失败：" + e);
        } finally {
            cosClient().shutdown();
        }
        if (isDelete) {
            file.deleteOnExit();
        }
        return url;
    }

    /**
     * 上传文件到COS
     */
    public String uploadPublicFile(File file, String dir, Boolean isDelete) {
        String filePath = dir + "/" + file.getName();
        String url = "";
        try {
            String result = cosClient().uploadFile(
                    new UploadFileRequest("public", filePath, Files.readAllBytes(file.toPath())));
            JSONObject jsonObject = JSONObject.parseObject(result);
            checkError(jsonObject);
            url = jsonObject.getJSONObject("data").getString("access_url");
        } catch (Exception e) {
            log.error("调用腾讯COS失败：" + e);
            throw new RuntimeException(e);
        } finally {
            cosClient().shutdown();
        }
        if (isDelete) {
            file.deleteOnExit();
        }
        return url;
    }

    /**
     * 检查返回值
     */
    private void checkError(JSONObject jsonObject) {
        Integer errcode = jsonObject.getInteger("code");
        String errmsg = jsonObject.getString("message");
        if (errcode != 0) {
            throw new RuntimeException("COS-API-V4 Error：" + errcode + "," + errmsg);
        }
    }

    /**
     * 获取当前时间的字符串格式
     *
     * @return
     */
    public static String getCurrentTimeFormat() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return now.format(dateTimeFormatter);
    }

}
