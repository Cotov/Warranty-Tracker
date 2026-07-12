package bg.softuni.warranty_tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class WarrantyTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(WarrantyTrackerApplication.class, args);
	}
}
