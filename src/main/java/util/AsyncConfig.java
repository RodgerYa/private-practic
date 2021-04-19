package util;

import com.xingren.v.auth.concurrent.DelegatingXrContextExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;

/**
 * 异步配置，以下线程池皆为全局共享，所以应比常规设置稍大
 * @author yanwenbo
 */
@Configuration
@EnableAsync(proxyTargetClass = true)
//@Slf4j
public class AsyncConfig extends AsyncConfigurerSupport {

    public static final Integer POOL_SIZE_SM = Runtime.getRuntime().availableProcessors();

    public static final Integer POOL_SIZE_MD = POOL_SIZE_SM * 2;

    public static final Integer POOL_SIZE_LG = POOL_SIZE_SM * 3;

    @Override
    public Executor getAsyncExecutor() {
        Executor executor = defaultExecutor();
        return new DelegatingXrContextExecutor(executor);
    }

    /**
     * Async的默认线程池，适用于：IO密集，执行时间短（毫秒级）
     */
    @Bean(destroyMethod = "shutdown")
    public ThreadPoolTaskExecutor defaultExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix("xr-async-default-");

        // 空闲时保留的核心线程数（不应设置过大，防止占用过多系统资源；也不应设置自动销毁，避免每次创建线程带来的开销）
        executor.setCorePoolSize(POOL_SIZE_SM * 16);

        // 最大活跃线程数（当核心线程和阻塞队列都满了之后才创建，空闲时会自动销毁；应设置上限，防止占用过多系统资源）
        executor.setMaxPoolSize(POOL_SIZE_SM * 64);

        // 阻塞队列长度（不应设置过大，防止占用过多系统资源且长时间等待；也不应设置过小，降低吞吐量）
        executor.setQueueCapacity(2048);

        /*
        上述配置的性能估算，假设99%任务执行时间为0.9s，则：
        每秒最大并发量 = maxPoolSize / 0.9 ≈ 284
        最长等待时间 = queueSize / 284 ≈ 7.2s
         */

        // 若任务数超出线程池并发极限（maxPoolSize已满，queue已满），则拒绝新任务
        executor.setRejectedExecutionHandler((r, e) -> {
            String errMsg = String.format("Task %s rejected from %s", r.toString(), e.toString());
//            log.error(errMsg);

            // 采用默认的拒绝策略，不过根据上述的性能估算，目前几乎不可能超负荷
            throw new RejectedExecutionException(errMsg);
        });

        executor.initialize();
        return executor;
    }

    /**
     * 自定义Async线程池，适用于：CPU密集，执行时间短（毫秒级）
     */
    @Bean(destroyMethod = "shutdown")
    public ThreadPoolTaskExecutor calcTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix("xr-async-calc-");
        executor.setCorePoolSize(POOL_SIZE_MD);
        executor.initialize();
        return executor;
    }

    /**
     * 自定义Async线程池，适用于：执行时间长，可接受任务长时间排队
     */
    @Bean(destroyMethod = "shutdown")
    public ThreadPoolTaskExecutor longTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix("xr-async-long-");
        executor.setCorePoolSize(POOL_SIZE_LG);
        executor.initialize();
        return executor;
    }

    public static final String LONG_TASK_EXECUTOR = "longTaskExecutor";
    public static final String CALC_TASK_EXECUTOR = "calcTaskExecutor";
}
