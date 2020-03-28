package software.jevera.measurementcontroller.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * The scheduling configuration.
 */
@Configuration
public class SchedulingConfiguration {

    /**
     * Creates the thread pool  task scheduler.
     * @param applicationProperties the application properties
     * @return the scheduler
     */
    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler(ApplicationProperties applicationProperties) {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(applicationProperties.getScheduler().getThreadPoolSize());
        threadPoolTaskScheduler.setThreadNamePrefix("mc-sc-thread");
        return threadPoolTaskScheduler;
    }

}
