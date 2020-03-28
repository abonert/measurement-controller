package software.jevera.measurementcontroller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import software.jevera.measurementcontroller.config.ApplicationProperties;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@EnableConfigurationProperties(ApplicationProperties.class)
public class MeasurementControllerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeasurementControllerApplication.class, args);
	}

}
