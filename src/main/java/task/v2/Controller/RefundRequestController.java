package task.v2.Controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import task.v2.Entity.RefundRequest;
import task.v2.Entity.Transaction;
import task.v2.Service.RefundRequestService;

import java.util.List;

@RestController
@RequestMapping("/api/returns")
public class RefundRequestController {

    @Autowired
    private RefundRequestService refundRequestService;

    //İade işlemlerini onaylar

    @PostMapping("/approve/{id}")
    public ResponseEntity<RefundRequest> approveRefund(@PathVariable("id") Long refundRequestId) {
        RefundRequest approvedRequest = refundRequestService.approveRefundRequest(refundRequestId);
        return ResponseEntity.ok(approvedRequest);
    }

    //iade işlemlerini reddeder

    @PostMapping("/reject/{id}")
    public ResponseEntity<RefundRequest> rejectRefundRequest(@PathVariable Long id) {
        try {
            RefundRequest rejectedRequest = refundRequestService.rejectRefundRequest(id);
            return ResponseEntity.ok(rejectedRequest);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    //Belirli bir user'a ait iade işlemlerini döndürü

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<RefundRequest>> getReturnRequestsByUserId(@PathVariable Long userId) {
        List<RefundRequest> returnRequests = refundRequestService.getReturnRequestsByUserId(userId);
        if (returnRequests.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(returnRequests);
    }

    //İade talebini siler

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRefundRequest(@PathVariable Long id) {
        try {
            refundRequestService.deleteRefundRequestById(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }


    //İade isteği oluşturur

    @PostMapping("/create")
    public ResponseEntity<RefundRequest> createReturnRequest(@RequestParam Long transactionId) {

        Transaction transaction = refundRequestService.findById(transactionId);
        if (transaction == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }


        RefundRequest returnRequest = refundRequestService.createReturnRequest(transaction);
        return ResponseEntity.status(HttpStatus.CREATED).body(returnRequest);
    }

    //İade koduna göre iade isteğini döndürür

    @GetMapping("/{returnCode}")
    public ResponseEntity<RefundRequest> getReturnStatus(@PathVariable String returnCode) {
        RefundRequest returnRequest = refundRequestService.getReturnRequestByCode(returnCode);
        if (returnRequest != null) {
            return ResponseEntity.ok(returnRequest);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    //Manuel olarak iade isteğini onaylar ve iade işlemini gerçekleştirir

    @PostMapping("/approveAndRefund/{id}")
    public ResponseEntity<String> approveAndRefundRequest(@PathVariable Long id) {
        try {
            refundRequestService.approveAndRefundRequest(id);
            return ResponseEntity.ok("Iade işlemi onaylandı ve geri ödeme yapıldı.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    //Pütün iade işlemlerinin işler

    @PostMapping("/processAll")
    public ResponseEntity<String> processAllPendingRequests() {
        try {
            refundRequestService.processReturnRequests();
            return ResponseEntity.ok("Tüm bekleyen iade talepleri işlendi.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //Bütün iade taleplerini döner

    @GetMapping("/all")
    public ResponseEntity<List<RefundRequest>> getAllReturnRequests() {
        List<RefundRequest> returnRequests = refundRequestService.getAllReturnRequests();
        return ResponseEntity.ok(returnRequests);
    }


    //Kargo Takip EndPointleri

    @GetMapping("/pending")
    public ResponseEntity<List<RefundRequest>> getPendingRequests() {
        List<RefundRequest> pendingRequests = refundRequestService.findPendingRequests();
        if (pendingRequests.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pendingRequests);
    }

    @GetMapping("/shipped")
    public ResponseEntity<List<RefundRequest>> getShippedRequests() {
        List<RefundRequest> shippedRequests = refundRequestService.findShippedRequests();
        if (shippedRequests.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(shippedRequests);
    }

    @GetMapping("/in-transit")
    public ResponseEntity<List<RefundRequest>> getInTransitRequests() {
        List<RefundRequest> inTransitRequests = refundRequestService.findInTransitRequests();
        if (inTransitRequests.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(inTransitRequests);
    }

    @GetMapping("/delivered")
    public ResponseEntity<List<RefundRequest>> getDeliveredRequests() {
        List<RefundRequest> deliveredRequests = refundRequestService.findDeliveredRequests();
        if (deliveredRequests.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(deliveredRequests);
    }







    //Operatorun onay verdiklerini döner

    @GetMapping("/approved")
    public ResponseEntity<List<RefundRequest>> getApprovedRefundRequests() {
        List<RefundRequest> approvedRequests = refundRequestService.getApprovedRefundRequests();
        if (approvedRequests.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(approvedRequests);
    }

    //İade işlemi yapılmışları döner

    @GetMapping("/refunded")
    public ResponseEntity<List<RefundRequest>> getRefundedRefundRequests() {
        List<RefundRequest> refundedRequests = refundRequestService.getRefundedRefundRequests();
        if (refundedRequests.isEmpty()) {
            return ResponseEntity.noContent().build(); // HTTP 204 - No Content
        }
        return ResponseEntity.ok(refundedRequests); // HTTP 200 - OK
    }


}
