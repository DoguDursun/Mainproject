package task.v2.Service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import task.v2.Entity.ShippingStatus;
import task.v2.Repository.ShippingStatusRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ShippingStatusService {

    @Autowired
    private ShippingStatusRepository shippingStatusRepository;


    public ShippingStatus updateShippingStatus(Long id, ShippingStatus updatedShippingStatus) {
        ShippingStatus existingShippingStatus = shippingStatusRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ShippingStatus not found with id " + id));


        existingShippingStatus.setRefundProcessId(updatedShippingStatus.getRefundProcessId());
        existingShippingStatus.setTrackingCode(updatedShippingStatus.getTrackingCode());
        existingShippingStatus.setStatus(updatedShippingStatus.getStatus());
        existingShippingStatus.setProductName(updatedShippingStatus.getProductName());
        existingShippingStatus.setPrice(updatedShippingStatus.getPrice());
        existingShippingStatus.setUpdatedAt(updatedShippingStatus.getUpdatedAt());

        return shippingStatusRepository.save(existingShippingStatus);
    }





    public List<ShippingStatus> getAllShippingStatuses() {
        return shippingStatusRepository.findAll();
    }


    public Optional<ShippingStatus> getShippingStatusById(Long id) {
        return shippingStatusRepository.findById(id);
    }


    public void deleteShippingStatusesByRefundProcessId(Long refundProcessId) {
        List<ShippingStatus> statusesToDelete = shippingStatusRepository.findByRefundProcessId(refundProcessId);
        if (statusesToDelete.isEmpty()) {
            throw new EntityNotFoundException("No ShippingStatus found with refundProcessId " + refundProcessId);
        }
        shippingStatusRepository.deleteAll(statusesToDelete);
    }



}
