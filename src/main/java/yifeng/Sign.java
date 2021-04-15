package yifeng;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.xingren.v.auth.Authorization;
import com.xingren.v.auth.Signature;
import com.xingren.v.auth.utils.AuthUtils;
import lombok.Data;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Sign {
    static final String YIFENG_SECRET = "Ayx37WFoFB7MdqB3R2Gz";

    static final String accessKeyId = "VSNTANtr5d4ZPWWm";
    static final String accessKeySecret = "rDCj5qY6wphrm35pwG4hQkdiGmfFwy9E";

    public static String sign(String data, String ts) {
        String paramsStr = "data=" + data + "&source=XINGREN&ts=" + ts;
        YifengAuth auth = new YifengAuth();
        System.out.println(paramsStr);
        auth.setPayload(paramsStr);

        return AuthUtils.auth(YIFENG_SECRET, auth, Signature.HMAC_SHA256);
    }

    public static void newSign(String data) {
        JSONObject body = JSONObject.parseObject(data);
        JSONObject sign = OtpSignHelper.buildOpenXSignHeader(accessKeyId, accessKeySecret);
        body.putAll(sign);
        System.out.println(body);
    }

    public static void main(String[] args) {
//        newSign("");
        String data = "{\"refundFee\":5.80,\"recommendId\":\"1b19c569e184457eac98b2761e0698a3\",\"orderId\":\"XR2021041202100003\",\"rebate\":false,\"parentOrderCode\":\"XR2021041202100003\",\"freight\":0,\"refundStatus\":\"REFUND_SUCCESS\"}";
        System.out.println(sign(data, "1611576803"));
    }

}

@Data
class YifengAuth implements Authorization {
    private String payload;

    @Override
    public String format(String s) {
        return s;
    }

    @Override
    public String produce() {
        return payload;
    }
}