package task.v2.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import task.v2.Entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User findByUserId(Long userId);

}
