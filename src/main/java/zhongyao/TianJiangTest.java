package zhongyao;

import com.google.common.collect.Lists;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author yanwenbo
 * @date 2021/1/18
 */
public class TianJiangTest {

    public static void main(String[] args) {
//        String timestamp = OffsetDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
//        String nonce = UUID.randomUUID().toString().replace("-", "");
//        String token = "21964698b7df1cef";
//        String queryStr = Lists.newArrayList(token, timestamp, nonce).stream().sorted().collect(Collectors.joining());
//        String signature = DigestUtils.sha1Hex(queryStr).toLowerCase();
//
//        System.out.println("signature: " + signature);
//        System.out.println("nonce: " + nonce);
//        System.out.println("timestamp: " + timestamp);
//        System.out.println("timestamp: " + timestamp);
//        System.out.println("si: \u6392\u9664\u91cd\u590d\uff0c\u6210\u529f\u63d2\u5165");

        try {
            String st = "\u6392\u9664\u91cd\u590d\uff0c\u6210\u529f\u63d2\u5165";
            String s = new String(st.getBytes("UTF-8"));
            s.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
