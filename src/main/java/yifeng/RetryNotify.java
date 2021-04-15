package yifeng;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class RetryNotify {

    private final OkHttpClient httpClient = new OkHttpClient();

    private static Integer count = 0;

    private static String REFUND_NOTIFY = "https://gw.xingren.com/api/drug/notify/refund";

    private static String PAY_NOTIFY = "https://gw.xingren.com/api/drug/notify/pay";

    private static String DELIVER_NOTIFY = "https://gw.xingren.com/api/drug/notify/order/status";

    private static String ORDER_NOTIFY = "https://gw.xingren.com/api/drug/notify/order";

    public static void main(String[] args) {
        RetryNotify notify = new RetryNotify();

        // 支付
//        notify.retryPay();

        // 下单
//        notify.retryOrder();
        // 发货
//        notify.retryDeliver();
        // 退款
//        notify.retryRefund();
        // 审核失败
//        notify.retryRefund();
    }

    private void retryDeliver() {
        List<String> logs = FileRead.read("/Users/yanwenbo/Desktop/workspace/test/src/main/resources/delivery_retry.txt");
        List<String> orderNos = FileRead.read("/Users/yanwenbo/Desktop/workspace/test/src/main/resources/orderNo.txt");
        doRetry(logs, orderNos, DELIVER_NOTIFY);
    }

    private void retryRefund() {
        List<String> logs = FileRead.read("/Users/yanwenbo/Desktop/workspace/test/src/main/resources/refund_retry.txt");
        List<String> orderNos = FileRead.read("/Users/yanwenbo/Desktop/workspace/test/src/main/resources/orderNo.txt");
        doRetry(logs, orderNos, REFUND_NOTIFY);
    }

    private void retryAudit() {
        String url = "https://gw.xingren.com/api/drug/notify/status";
        String[] logArray = new String[]{""};
        String[] orderNoArray = new String[]{""};
        doRetry(Lists.newArrayList(logArray), Lists.newArrayList(orderNoArray), url);
    }

    private void retryOrder() {
        List<String> logs = FileRead.read("/Users/yanwenbo/Desktop/workspace/test/src/main/resources/order_retry.txt");
        List<String> orderNos = FileRead.read("/Users/yanwenbo/Desktop/workspace/test/src/main/resources/orderNo.txt");
        doRetry(logs, orderNos, ORDER_NOTIFY);
    }

    private void retryStatus() {
        List<String> logs = FileRead.read("/Users/yanwenbo/Desktop/workspace/test/src/main/resources/order_status_retry.txt");
        List<String> orderNos = FileRead.read("/Users/yanwenbo/Desktop/workspace/test/src/main/resources/orderNo.txt");
        doRetry(logs, orderNos, DELIVER_NOTIFY);
    }

    private void retryPay() {
        List<String> logs = FileRead.read("/Users/yanwenbo/Desktop/workspace/test/src/main/resources/paid_retry.txt");
        List<String> orderNos = FileRead.read("/Users/yanwenbo/Desktop/workspace/test/src/main/resources/orderNo.txt");
        doRetry(logs, orderNos, PAY_NOTIFY);
    }

    /**
     * 1 比对调用日志与待通知订单
     *
     *  log 格式 sig#ts#content(换行符)
     *  order xx,xx
     */
    private void doRetry(List<String> logs, List<String> orderNos, String url) {

        List<List<String>> todoList = Lists.newArrayList();
        Map<String, Boolean> doneMap = Maps.newHashMap();
        logs.forEach(log -> {
            if (PAY_NOTIFY.equals(url) && !log.contains("realFee")) {
                return;
            }
            if (ORDER_NOTIFY.equals(url) && !(log.contains("orderId") && log.contains("countOfSplite"))) {
                return;
            }
            if (DELIVER_NOTIFY.equals(url) && !log.contains("SHIPPING_ORDER")) {
                return;
            }
            if (REFUND_NOTIFY.equals(url) && !log.contains("REFUND_SUCCESS")) {
                return;
            }
            if (count % 30 == 0) {
                try {
                    count++;
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            List<String> content = Lists.newArrayList(log.split("#"));
            if (content.size() != 3) {
                System.out.println("参数不为三个" + content);
                return;
            }
            if (content.stream().anyMatch(StringUtils::isEmpty)) {
                System.out.println("参数为空" + content);
                return;
            }
            if (!content.get(2).contains("{") || !content.get(2).contains("}")) {
                System.out.println("请求体参数错误" + content);
                return;
            }
            Optional<String> orderOpt = orderNos.stream().filter(no -> content.stream().anyMatch(cont -> cont.contains(no))).findFirst();
            boolean hasPost = orderOpt.isPresent() && (doneMap.containsKey(content.get(2)) || (doneMap.keySet().stream().anyMatch(d -> d.contains(orderOpt.get()))));
            if (doneMap.containsKey(content.get(2))) {
                System.out.println("已经重放，不重试" + content);
                return;
            }
            if (!hasPost) {
                System.out.println(String.format("匹配成功，content = %s", content));
                todoList.add(content);
                doneMap.put(content.get(2), true);
                try {
                    doPost(content, url);
                    count++;
                } catch (Exception e) {
                    System.out.println("重放失败！");
                    e.printStackTrace();
                }
            }
        });
        System.out.println("重放订单数量" + todoList.size());
    }

    private void doPost(List<String> content, String url) throws Exception {
        String sig = content.get(0);
        String ts = content.get(1);
        String json = content.get(2);

        RequestBody body = RequestBody.create(
                json,
                MediaType.parse("application/json; charset=utf-8"));

        Request request = new Request.Builder()
                .url(url)
                .addHeader("User-Agent", "okhttp/3.3.0")
                .addHeader("ts", ts)
                .addHeader("sig", sig)
                .post(body)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {

            if (!response.isSuccessful()) {
                throw new IOException(OffsetDateTime.now().toString() + ": Unexpected code " + response);
            }

            // Get response body
            System.out.println(OffsetDateTime.now().toString() + ": " + response.body().string());
        }
    }

}

