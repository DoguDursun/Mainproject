package task.v2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import task.v2.Component.RefundRequestCronJob;
import task.v2.Component.ShippingStatusCronJob;
import task.v2.Service.FakeDataService;

@SpringBootApplication
@EnableScheduling
public class V2Application implements CommandLineRunner {

	@Autowired
 	public FakeDataService fakeDataService;
	@Autowired
	public RefundRequestCronJob returnRequestCronJob;
	@Autowired
	public ShippingStatusCronJob shippingStatusCronJob;
	public static void main(String[] args) {
		SpringApplication.run(V2Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//fakeDataService.loadFakeData();
		returnRequestCronJob.processRefundRequests();
		shippingStatusCronJob.updateShippingStatus();
	}
}
