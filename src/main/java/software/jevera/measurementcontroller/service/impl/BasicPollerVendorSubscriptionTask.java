package software.jevera.measurementcontroller.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;
import software.jevera.measurementcontroller.config.ApplicationProperties;
import software.jevera.measurementcontroller.config.ApplicationProperties.VendorConfiguration.HttpConfiguration;
import software.jevera.measurementcontroller.domain.vendor.VendorData;
import software.jevera.measurementcontroller.domain.vendor.enumeration.VendorSubscriptionType;
import software.jevera.measurementcontroller.repository.VendorDataStorage;
import software.jevera.measurementcontroller.service.SchedulingManager;
import software.jevera.measurementcontroller.service.VendorDataStorageResolver;
import software.jevera.measurementcontroller.service.VendorSubscriptionTask;

import java.time.LocalDate;
import java.util.Map;

/**
 * Basic polling strategy subscription to vendor
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class BasicPollerVendorSubscriptionTask implements VendorSubscriptionTask {

    private final SchedulingManager schedulingManager;
    private final VendorDataStorageResolver dataStorageResolver;

    @Override
    public void execute(Long subscriptionId,
                        ApplicationProperties.VendorConfiguration configuration) {
        VendorDataStorage storage = dataStorageResolver.getStorage(configuration.getStorageType());
        Runnable task = buildSubscriptionTask(subscriptionId, configuration.getHttp(), storage);
        schedulingManager.schedule(subscriptionId, task, new CronTrigger(configuration.getCronExpression()));
    }

    @Override
    public boolean supports(VendorSubscriptionType controllerType) {
        return VendorSubscriptionType.POLLER.equals(controllerType);
    }

    private Runnable buildSubscriptionTask(Long subscriptionId, HttpConfiguration config, VendorDataStorage storage) {
        return () -> {
            MDC.put("subscription_id", String.valueOf(subscriptionId));
            VendorData data = getData(config);
            storage.store(data);
        };
    }

    private VendorData getData(HttpConfiguration config) {
        log.info("Get data from vendor: {}", config.getEndpoint());
        VendorData vendorData = new VendorData();
        vendorData.setData(Map.of("date", LocalDate.now(), "temperature", 15, "scale", "Celsius"));
        return vendorData;
    }
}
