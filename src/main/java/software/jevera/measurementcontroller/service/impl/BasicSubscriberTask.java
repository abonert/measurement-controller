package software.jevera.measurementcontroller.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import software.jevera.measurementcontroller.config.ApplicationProperties;
import software.jevera.measurementcontroller.config.ApplicationProperties.VendorConfiguration.HttpConfiguration;
import software.jevera.measurementcontroller.domain.vendor.enumeration.VendorSubscriptionType;
import software.jevera.measurementcontroller.repository.VendorDataStorage;
import software.jevera.measurementcontroller.service.VendorDataStorageResolver;
import software.jevera.measurementcontroller.service.VendorSubscriptionTask;

@Service
@Slf4j
@RequiredArgsConstructor
public class BasicSubscriberTask implements VendorSubscriptionTask {

    private final VendorDataStorageResolver vendorDataStorageResolver;

    @Override
    public void subscribe(Long subscriptionId, ApplicationProperties.VendorConfiguration configuration) {
        VendorDataStorage storage = vendorDataStorageResolver.getStorage(configuration.getStorageType());
        HttpConfiguration http = configuration.getHttp();
        executeRequest(http); //subscribe to chunk


    }

    private void executeRequest(HttpConfiguration http) {
        String endpoint = http.getEndpoint();
        HttpMethod method = http.getMethod();
        log.info("HTTP Method {} endpoint {}", method, endpoint);
    }

    @Override
    public void destroy(Long subscriptionId) {

    }

    @Override
    public boolean supports(VendorSubscriptionType type) {
        return VendorSubscriptionType.SUBSCRIBER.equals(type);
    }
}
