package task.v2.Component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import task.v2.Entity.RefundRequest;
import task.v2.Entity.RefundedTransaction;
import task.v2.Repository.RefundRequestRepository;
import task.v2.Repository.RefundedTransactionRepository;
import task.v2.Service.UserService;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class RefundRequestCronJob {

    @Autowired
    private RefundRequestRepository refundRequestRepository;

    @Autowired
    private RefundedTransactionRepository refundedTransactionRepository;

    @Autowired
    private UserService userService;

    @Scheduled(fixedRate = 60000) // 1 dakika
    public void processRefundRequests() {
        List<RefundRequest> approvedRequests = refundRequestRepository.findByOperatorStatus("APPROVED");

        for (RefundRequest request : approvedRequests) {
            if (request.getRefundedStatus() == null || !request.getRefundedStatus().equals("REFUNDED")) {
                // Refund işlemini gerçekleştir
                userService.creditBalance(request.getTransaction().getUser(), request.getAmount());

                // Refund işlemi tamamlandıktan sonra durumu güncelle
                request.setRefundedStatus("REFUNDED");
                refundRequestRepository.save(request);

                // RefundedTransaction tablosuna kayıt ekle
                RefundedTransaction refundedTransaction = new RefundedTransaction();
                refundedTransaction.setUser(request.getTransaction().getUser());
                refundedTransaction.setTransaction(request.getTransaction());
                refundedTransaction.setRefundAmount(request.getAmount());
                refundedTransaction.setRefundedAt(LocalDateTime.now());
                refundedTransactionRepository.save(refundedTransaction);
            }
        }
    }
}

