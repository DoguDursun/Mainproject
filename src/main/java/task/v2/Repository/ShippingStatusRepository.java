package task.v2.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import task.v2.Entity.ShippingStatus;

import java.util.List;

@Repository
public interface ShippingStatusRepository extends JpaRepository<ShippingStatus, Long> {
    ShippingStatus findByTrackingCode(String trackingCode);

    List<ShippingStatus> findByRefundProcessId(Long refundProcessId);
}
