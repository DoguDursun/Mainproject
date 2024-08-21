package task.v2.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import task.v2.Entity.ShippingStatus;
import task.v2.Service.ShippingStatusService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/shipping-statuses")
public class ShippingStatusController {

    @Autowired
    private ShippingStatusService shippingStatusService;


    @PatchMapping("/update/{id}")
    public ResponseEntity<ShippingStatus> updateShippingStatus(@PathVariable Long id, @RequestBody ShippingStatus shippingStatus) {
        ShippingStatus updatedShippingStatus = shippingStatusService.updateShippingStatus(id, shippingStatus);
        return ResponseEntity.ok(updatedShippingStatus);
    }




    @GetMapping("/getAll")
    public ResponseEntity<List<ShippingStatus>> getAllShippingStatuses() {
        List<ShippingStatus> shippingStatuses = shippingStatusService.getAllShippingStatuses();
        return ResponseEntity.ok(shippingStatuses);
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<ShippingStatus> getShippingStatusById(@PathVariable Long id) {
        Optional<ShippingStatus> shippingStatus = shippingStatusService.getShippingStatusById(id);
        return shippingStatus.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @DeleteMapping("/deleteByProcessId/{refundProcessId}")
    public ResponseEntity<Void> deleteShippingStatusesByRefundProcessId(@PathVariable Long refundProcessId) {
        shippingStatusService.deleteShippingStatusesByRefundProcessId(refundProcessId);
        return ResponseEntity.noContent().build();
    }
}
