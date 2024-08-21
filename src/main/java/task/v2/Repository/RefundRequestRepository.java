package task.v2.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import task.v2.Entity.RefundRequest;
import task.v2.EnumStatus.RefundStatusEnum;
import task.v2.EnumStatus.ShippingStatusEnum;

import java.util.List;

@Repository
public interface RefundRequestRepository extends JpaRepository<RefundRequest,Long> {

    RefundRequest findByReturnCode(String returnCode);
    List<RefundRequest> findByTransaction_User_UserId(Long userId);
    List<RefundRequest> findByRefundStatus(RefundStatusEnum refundStatus);
    List<RefundRequest> findByOperatorStatus(String status);
    List<RefundRequest> findByShippingStatusEnum(ShippingStatusEnum shippingStatusEnum);
    List<RefundRequest> findByRefundedStatus(String refundedStatus);
}
