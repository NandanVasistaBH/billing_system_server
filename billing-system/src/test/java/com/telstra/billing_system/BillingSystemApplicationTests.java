package com.telstra.billing_system;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes=BillingSystemApplication.class)
@ActiveProfiles("testtest")
class BillingSystemApplicationTests {

	@Test
	void contextLoads() {
	}

}
