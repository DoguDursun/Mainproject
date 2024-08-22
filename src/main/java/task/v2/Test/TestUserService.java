package task.v2.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;

@Service
public class TestUserService {

    @Autowired
    private TestUserRepository testUserRepository;

    public TestUser saveTestUser(TestUser testUser) {
        testUser.setCreatedAt(LocalDateTime.now());
        return testUserRepository.save(testUser);
    }
}
