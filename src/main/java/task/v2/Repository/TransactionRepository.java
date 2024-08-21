package task.v2.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import task.v2.Entity.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {
}
