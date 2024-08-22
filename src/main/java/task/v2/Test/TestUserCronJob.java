package task.v2.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Component
public class TestUserCronJob {


    @Autowired
    private TestUserService saveTestUser;
    @Scheduled(cron = "0 */2 * * * *") // Her 2 dakikada bir çalışır
    public void addTestUser() {
        TestUser testUser = new TestUser();
        testUser.setUsername("testUser" + System.currentTimeMillis());
        testUser.setEmail("testuser" + System.currentTimeMillis() + "@example.com");

        saveTestUser.saveTestUser(testUser);

        System.out.println("New test user added at " + LocalDateTime.now());
    }
}
