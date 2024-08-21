package task.v2.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import task.v2.EnumStatus.RefundStatusEnum;
import task.v2.EnumStatus.ShippingStatusEnum;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "refund_request")
@AllArgsConstructor
@NoArgsConstructor
public class RefundRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "refund_status")
    private RefundStatusEnum refundStatus;

    @OneToOne
    @JoinColumn(name = "transaction_id", nullable = false)
    private Transaction transaction;

    @Column(name = "operator_status")
    private String operatorStatus;

    @Column(name = "refunded_status")
    private String refundedStatus;

    @Enumerated(EnumType.STRING)  // Enum değerini direk kaydetmek için
    @Column(name = "shipping_status_enum", nullable = false)
    private ShippingStatusEnum shippingStatusEnum; // Enum türünde bir alan

    @JsonIgnore
    @OneToOne(mappedBy = "refundRequest", cascade = CascadeType.ALL)
    private ShippingStatus shippingStatus;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private String returnCode;

    private Double amount;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;



}
