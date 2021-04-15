package yifeng;

import com.alibaba.fastjson.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class OtpSignHelper {
    private static final String CONTENT_TYPE = "application/json";
    private static final String SIGN_HEADER = "OTP";
    public static final String RFC_2616_DAY_TIME_FORMAT = "EEE, dd MMM yyyy HH:mm:ss z";
    public static JSONObject buildOpenXSignHeader(String accessKeyId, String accessKeySecret) {
        JSONObject openXSignHeader = new JSONObject();
        JSONObject sign = buildSignHeader(accessKeyId, accessKeySecret);
        openXSignHeader.put("_openx_head", sign);
        return openXSignHeader;
    }
    public static JSONObject buildSignHeader(String accessKeyId, String accessKeySecret) {
        String contentMd5 = "";
        String apiVersion = "1.0";
        String date = getGmtString(new Date());
        String signature = sign("POST", contentMd5, CONTENT_TYPE, date, apiVersion, accessKeySecret);
        JSONObject sign = new JSONObject();
        sign.put("Authorization", String.format("%s %s:%s", SIGN_HEADER, accessKeyId, signature));
        sign.put("Content-Md5", contentMd5);
        sign.put("Content-Type", CONTENT_TYPE);
        sign.put("Date", date);
        sign.put("Api-Version", apiVersion);
        return sign;
    }
    public static String sign(String method, String contentMd5, String contentType, String date, String apiVersion, String accessKeySecret) {
        contentMd5 = contentMd5 != null ? contentMd5 : "";
        String raw = method + "\n"
                + contentMd5 + "\n"
                + contentType + "\n"
                + date + "\n"
                + apiVersion;
        return HmacSHA1.genHMACBase64Encoded(raw, accessKeySecret);
    }
    public static String getGmtString(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(RFC_2616_DAY_TIME_FORMAT, Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return dateFormat.format(date);
    }
    public static Date parseGmtTimestamp(String timestamp) {
        try {
            return new SimpleDateFormat(RFC_2616_DAY_TIME_FORMAT, Locale.US).parse(timestamp);
        } catch (ParseException e) {
            return null;
        }
    }
    public static boolean checkValidDate(Date source, Integer expiresSecond, Date target) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(source);
        ca.add(Calendar.SECOND, expiresSecond);
        return ca.getTime().compareTo(target) >= 0;
    }
}
