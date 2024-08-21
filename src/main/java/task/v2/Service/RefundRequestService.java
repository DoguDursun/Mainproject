package task.v2.Service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import task.v2.Entity.*;
import task.v2.EnumStatus.RefundStatusEnum;
import task.v2.EnumStatus.ShippingStatusEnum;
import task.v2.Repository.RefundRequestRepository;
import task.v2.Repository.RefundedTransactionRepository;
import task.v2.Repository.ShippingStatusRepository;
import task.v2.Repository.TransactionRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class RefundRequestService {

    @Autowired
    private RefundRequestRepository refundRequestRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private RefundedTransactionRepository refundedTransactionRepository;
    @Autowired
    private ShippingStatusRepository shippingStatusRepository;


    public RefundRequest approveRefundRequest(Long refundRequestId) {
        RefundRequest refundRequest = refundRequestRepository.findById(refundRequestId)
                .orElseThrow(() -> new RuntimeException("Refund request not found"));

        if (refundRequest.getShippingStatusEnum() == ShippingStatusEnum.DELIVERED) {
            refundRequest.setOperatorStatus("APPROVED");
            refundRequest.setUpdatedAt(LocalDateTime.now());
            return refundRequestRepository.save(refundRequest);
        } else {
            throw new RuntimeException("Shipping status is not eligible for approval");
        }
    }

    public RefundRequest rejectRefundRequest(Long refundRequestId) {
        // İade isteğini ID'ye göre bul
        RefundRequest refundRequest = refundRequestRepository.findById(refundRequestId)
                .orElseThrow(() -> new RuntimeException("Refund request not found"));

        // Operator status'u REJECTED olarak işaretle
        refundRequest.setOperatorStatus("REJECTED");
        refundRequest.setUpdatedAt(LocalDateTime.now());

        // Güncellenmiş isteği veritabanına kaydet
        return refundRequestRepository.save(refundRequest);
    }



    public RefundRequest createReturnRequest(Transaction transaction) {
        RefundRequest returnRequest = new RefundRequest();
        returnRequest.setTransaction(transaction);
        returnRequest.setReturnCode(UUID.randomUUID().toString());
        returnRequest.setRefundStatus(RefundStatusEnum.PENDING);
        returnRequest.setAmount(transaction.getTotalPrice());
        returnRequest.setCreatedAt(LocalDateTime.now());
        returnRequest.setUpdatedAt(LocalDateTime.now());

        // Product nesnesini alın
        Product product = transaction.getProduct();

        // Product nesnesini RefundRequest'e ayarla
        returnRequest.setProduct(product);

        // ShippingStatusEnum değerini doğrudan atayın
        returnRequest.setShippingStatusEnum(ShippingStatusEnum.PENDING);

        return refundRequestRepository.save(returnRequest);
    }



    public void approveAndRefundRequest(Long id) {
        RefundRequest request = refundRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Iade isteği bulunamadı"));

        if (isValidForApproval(request)) {
            request.setOperatorStatus("APPROVED");
            userService.creditBalance(request.getTransaction().getUser(), request.getAmount());
            request.setRefundedStatus("REFUNDED"); // Onay sonrası Refunded olarak işaretlenir
            request.setRefundStatus(RefundStatusEnum.REFUNDED);
            request.setUpdatedAt(LocalDateTime.now());
            refundRequestRepository.save(request);

            // RefundedTransaction kaydı oluşturulur
            RefundedTransaction refundedTransaction = new RefundedTransaction();
            refundedTransaction.setUser(request.getTransaction().getUser());
            refundedTransaction.setTransaction(request.getTransaction());
            refundedTransaction.setRefundAmount(request.getAmount());
            refundedTransaction.setRefundedAt(LocalDateTime.now());
            refundedTransactionRepository.save(refundedTransaction);
        } else {
            throw new RuntimeException("Kurallar nedeniyle istek onaylanamıyor");
        }
    }



    public void processReturnRequests() {
        List<RefundRequest> pendingRequests = refundRequestRepository.findByRefundStatus(RefundStatusEnum.PENDING);

        for (RefundRequest request : pendingRequests) {
            if (isValidForRefund(request)) {
                approveAndRefundRequest(request.getId());
            } else {
                approveAndRefundRequest(request.getId());
            }
        }
    }


    public void deleteRefundRequestById(Long id) {
        RefundRequest refundRequest = refundRequestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Refund request not found with id: " + id));
        refundRequestRepository.delete(refundRequest);
    }


    public List<RefundRequest> getRefundedRefundRequests() {
        return refundRequestRepository.findByRefundedStatus("REFUNDED");
    }


    public List<RefundRequest> getApprovedRefundRequests() {
        return refundRequestRepository.findByOperatorStatus("APPROVED");
    }





    public List<RefundRequest> findRequestsByShippingStatus(ShippingStatusEnum status) {
        return refundRequestRepository.findByShippingStatusEnum(status);
    }


    public List<RefundRequest> findPendingRequests() {
        return findRequestsByShippingStatus(ShippingStatusEnum.PENDING);
    }


    public List<RefundRequest> findShippedRequests() {
        return findRequestsByShippingStatus(ShippingStatusEnum.SHIPPED);
    }


    public List<RefundRequest> findInTransitRequests() {
        return findRequestsByShippingStatus(ShippingStatusEnum.IN_TRANSIT);
    }


    public List<RefundRequest> findDeliveredRequests() {
        return findRequestsByShippingStatus(ShippingStatusEnum.DELIVERED);
    }




    public List<RefundRequest> getReturnRequestsByUserId(Long userId) {
        return refundRequestRepository.findByTransaction_User_UserId(userId);
    }


    public RefundRequest getReturnRequestByCode(String returnCode) {
        return refundRequestRepository.findByReturnCode(returnCode);
    }

    public Transaction findById(Long id) {
        return transactionRepository.findById(id).orElse(null);
    }

    public List<RefundRequest> getAllReturnRequests() {
        return refundRequestRepository.findAll();
    }


    private boolean isValidForApproval(RefundRequest request) {
        return request.getRefundStatus() == RefundStatusEnum.PENDING || request.getRefundStatus() == RefundStatusEnum.REJECTED;
    }



    private boolean isValidForRefund(RefundRequest request) {
        return request.getRefundStatus() == RefundStatusEnum.PENDING && "APPROVED".equals(request.getOperatorStatus());
    }

}
