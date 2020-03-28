package software.jevera.measurementcontroller.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

/**
 * Schedule task manager
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SchedulingManager {

    private final ThreadPoolTaskScheduler taskScheduler;

    private Map<Long, List<ScheduledFuture<?>>> schedulers = new ConcurrentHashMap<>();

    public void schedule(Long id, Runnable task, CronTrigger cronTrigger) {
        List<ScheduledFuture<?>> scheduledFutures = schedulers.computeIfAbsent(id, (k) -> new LinkedList<>());
        scheduledFutures.add(taskScheduler.schedule(task, cronTrigger));
    }

    public void destroy(Long id) {
        log.info("Destroy scheduler tasks for subscription with ID {}", id);
        List<ScheduledFuture<?>> scheduledFutures = schedulers.get(id);
        if (CollectionUtils.isNotEmpty(scheduledFutures)) {
            scheduledFutures.forEach(f -> f.cancel(false));
            scheduledFutures.clear();
        }
    }
}
