package util;

import jodd.util.ThreadUtil;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class RetryUtil {

    private static final Integer API_ERROR_CODE_400 = 400;
    private static final Integer API_ERROR_CODE_500 = 500;
    private static final int UNIT_TIME = 100;

    /**
     * 重试机制,当遇到指定异常后,进行重试,当重试次数达到指定次数后,提示失败。
     * @param supplier 核心逻辑
     * @param maxRetries 最大重试次数
     * @return 当前重试次数,核心逻辑执行结果
     */
    public static <T> Pair<Integer, T> retry(Supplier<T> supplier, Consumer<Exception> exConsumer, Integer maxRetries) {
        Integer retries = 0;
        Exception cause = null;
        while (retries < maxRetries) {
            //第一次不需要休眠
            if (retries != 0) {
                sleepByRetryTimes(retries);
            }
            retries++;
            try {
                return Pair.of(retries, supplier.get());
//            businessException
//            } catch (RetryableException | ClientCallException ex) {
//                cause = ex;
//                exConsumer.accept(ex);
            } catch (RuntimeException ex) {
                Optional<Integer> errCode = Optional.empty();
                errCode.ifPresent(code -> {
                    //4xx的不需要重试
                    if (code >= API_ERROR_CODE_400 && code < API_ERROR_CODE_500) {
                        throw ex;
                    }
                });
            }
        }

        String msg = "Retry " + maxRetries + " times and all failed!";
        throw new RuntimeException(msg, cause);
    }

    public static <T> Pair<Integer, T> retry(Supplier<T> supplier, Integer maxRetries) {
        return retry(supplier, ex -> System.out.println(ex.getMessage() + ex.getCause()), maxRetries);
    }

    /**
     * 线程休眠策略
     * 重试次数越多,休眠的时间越长。
     * @param retries 重试次数
     */
    private static void sleepByRetryTimes(int retries) {
        int end = retries * UNIT_TIME;
        int ms = RandomUtils.nextInt(0, end);
        ThreadUtil.sleep(ms * retries);
    }
}
