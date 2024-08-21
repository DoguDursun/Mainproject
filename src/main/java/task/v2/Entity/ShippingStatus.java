package task.v2.Entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import task.v2.EnumStatus.ShippingStatusEnum;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "shipping_status")
@AllArgsConstructor
@NoArgsConstructor
public class ShippingStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "refund_process_id")
    private Long refundProcessId;

    @Column(name = "tracking_code", unique = true)
    private String trackingCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ShippingStatusEnum status;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;


    @OneToOne
    @JoinColumn(name = "refund_request_id", nullable = true)
    private RefundRequest refundRequest;
}
