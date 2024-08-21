package task.v2.Service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import task.v2.Entity.Transaction;
import task.v2.Repository.TransactionRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction updateTransaction(Transaction transaction) {
        // Önce mevcut kaydın var olup olmadığını kontrol edin
        Optional<Transaction> existingTransaction = transactionRepository.findById(transaction.getTransactionId());

        if (existingTransaction.isPresent()) {
            // Mevcut kaydı güncelle
            Transaction existing = existingTransaction.get();

            // Burada gerekli alanları mevcut verilerle güncelleyin
            existing.setProduct(transaction.getProduct());
            existing.setUser(transaction.getUser());
            existing.setQuantity(transaction.getQuantity());
            existing.setTotalPrice(transaction.getTotalPrice());
            // Diğer alanları da ekleyebilirsiniz...

            // Güncellenmiş kaydı geri dön
            return transactionRepository.save(existing);
        } else {
            throw new EntityNotFoundException("Transaction not found with ID: " + transaction.getTransactionId());
        }
    }


    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }


    public Optional<Transaction> getTransactionById(Long id) {
        return transactionRepository.findById(id);
    }


    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }


}
