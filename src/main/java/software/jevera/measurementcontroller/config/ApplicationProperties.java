package software.jevera.measurementcontroller.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpMethod;
import software.jevera.measurementcontroller.domain.vendor.enumeration.VendorStorageType;
import software.jevera.measurementcontroller.domain.vendor.enumeration.VendorSubscriptionType;

import java.util.Map;

/**
 * Properties specific to {@link software.jevera.measurementcontroller.MeasurementControllerApplication}.
 * <p>
 * Properties are configured in the application.yml file.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
@Getter
@Setter
public class ApplicationProperties {

    private Scheduler scheduler;
    private Map<String, VendorConfiguration> vendorConfigurations;

    @Getter
    @Setter
    public static class Scheduler {
        private int threadPoolSize = 5;
    }

    @Getter
    @Setter
    public static class VendorConfiguration {
        private VendorSubscriptionType subscriptionType;
        private HttpConfiguration http;
        private HttpConfiguration chunk;
        private VendorStorageType storageType;
        private String cronExpression;

        @Getter
        @Setter
        public static class HttpConfiguration {
            private HttpMethod method;
            private String endpoint;
        }
    }
}
