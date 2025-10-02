package org.hospital.historical;

import net.devh.boot.grpc.server.autoconfigure.GrpcServerSecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = { GrpcServerSecurityAutoConfiguration.class })
public class HistoricalApplication {

	public static void main(String[] args) {
		SpringApplication.run(HistoricalApplication.class, args);
	}

}
