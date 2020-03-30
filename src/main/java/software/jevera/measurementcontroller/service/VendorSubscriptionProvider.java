package software.jevera.measurementcontroller.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import software.jevera.measurementcontroller.config.ApplicationProperties;
import software.jevera.measurementcontroller.config.ApplicationProperties.VendorConfiguration;
import software.jevera.measurementcontroller.domain.user.User;
import software.jevera.measurementcontroller.domain.vendor.Vendor;
import software.jevera.measurementcontroller.domain.vendor.enumeration.VendorSubscriptionType;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
        VendorConfiguration configuration = getVendorConfiguration(vendor);
        VendorSubscriptionTask task = getVendorSubscriptionTask(configuration.getSubscriptionType());
        task.subscribe(userId, configuration);
    }

    /**
     * Destroy user subscriptions
     * @param user the user entity
     */
    public void destroyAll(User user) {
        Set<VendorSubscriptionType> userVendorSubscriptionTypes = user.getVendors().stream()
                .map(Vendor::getName)
                .map(this::getVendorConfiguration)
                .map(VendorConfiguration::getSubscriptionType)
                .collect(Collectors.toSet());
        userVendorSubscriptionTypes.forEach(subscriptionType -> {
            VendorSubscriptionTask task = getVendorSubscriptionTask(subscriptionType);
            task.destroy(user.getId());
        });
    }

    private VendorSubscriptionTask getVendorSubscriptionTask(VendorSubscriptionType vendorSubscriptionType) {
        return vendorSubscriptionTasks.stream()
                .filter(t -> t.supports(vendorSubscriptionType))
                .findFirst()
                .orElseThrow();
    }

    private VendorConfiguration getVendorConfiguration(String vendor) {
        Map<String, VendorConfiguration> configurations = applicationProperties.getVendorConfigurations();
        return ofNullable(configurations.get(vendor)).orElseThrow();
    }
}
