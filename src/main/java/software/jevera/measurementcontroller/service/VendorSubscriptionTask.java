package software.jevera.measurementcontroller.service;

import software.jevera.measurementcontroller.config.ApplicationProperties;
import software.jevera.measurementcontroller.domain.vendor.enumeration.VendorSubscriptionType;

/**
 * Interface for executing subscription process to vendor
 */
public interface VendorSubscriptionTask {

    /**
     * Subscribe to receive vendor data
     * @param subscriptionId the subscription identifier
     * @param configuration the vendor subscription configuration
     */
    void subscribe(Long subscriptionId, ApplicationProperties.VendorConfiguration configuration);

    /**
     * Destroy subscription
     * @param subscriptionId the subscription identifier
     */
    void destroy(Long subscriptionId);

    /**
     * Check whether task can process subscription
     * @param type the vendor subscription type

     * @return <code>true</code> returns <code>true</code> if task
     * supports the indicated {@link VendorSubscriptionType} object.
     */
    boolean supports(VendorSubscriptionType type);
}
