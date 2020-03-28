package software.jevera.measurementcontroller.service;

import software.jevera.measurementcontroller.config.ApplicationProperties;
import software.jevera.measurementcontroller.domain.vendor.enumeration.VendorSubscriptionType;

/**
 * Interface for executing subscription process to vendor
 */
public interface VendorSubscriptionTask {

    /**
     * Executes subscription to data vendor
     * @param subscriptionId the subscription identifier
     * @param configuration the vendor subscription configuration
     */
    void execute(Long subscriptionId, ApplicationProperties.VendorConfiguration configuration);

    /**
     * Check whether task can process subscription
     * @param type the vendor subscription type

     * @return <code>true</code> returns <code>true</code> if task
     * supports the indicated {@link VendorSubscriptionType} object.
     */
    boolean supports(VendorSubscriptionType type);
}
