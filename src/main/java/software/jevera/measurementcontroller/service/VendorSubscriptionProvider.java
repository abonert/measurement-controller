package software.jevera.measurementcontroller.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import software.jevera.measurementcontroller.config.ApplicationProperties;
import software.jevera.measurementcontroller.config.ApplicationProperties.VendorConfiguration;

import java.util.List;
import java.util.Map;

import static java.util.Optional.ofNullable;

@Component
@RequiredArgsConstructor
public class VendorSubscriptionProvider {

    private final List<VendorSubscriptionTask> vendorSubscriptionTasks;
    private final ApplicationProperties applicationProperties;

    /**
     * Subscribe user to specific vendor
     * @param userId the user identifier
     * @param vendor the vendor identifier
     */
    public void subscribe(Long userId, String vendor) {
        Map<String, VendorConfiguration> configurations = applicationProperties.getVendorConfigurations();
        VendorConfiguration configuration = ofNullable(configurations.get(vendor)).orElseThrow();

        VendorSubscriptionTask task = vendorSubscriptionTasks.stream()
                        .filter(t -> t.supports(configuration.getSubscriptionType()))
                        .findFirst()
                        .orElseThrow();
        task.execute(userId, configuration);
    }
}
