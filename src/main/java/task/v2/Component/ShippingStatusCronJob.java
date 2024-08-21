package task.v2.Component;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import task.v2.Entity.Product;
import task.v2.Entity.RefundRequest;
import task.v2.Entity.ShippingStatus;
import task.v2.EnumStatus.ShippingStatusEnum;
import task.v2.Repository.ProductRepository;
import task.v2.Repository.RefundRequestRepository;
import task.v2.Repository.ShippingStatusRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import static task.v2.EnumStatus.ShippingStatusEnum.*;

@Service
public class ShippingStatusCronJob {

    @Autowired
    private ShippingStatusRepository shippingStatusRepository;
    @Autowired
    private RefundRequestRepository refundRequestRepository;
    @Autowired
    private ProductRepository productRepository;
    private Random random = new Random();

    @Scheduled(cron = "0 * * * * ?") // Her dakika başında çalışır
    public void updateShippingStatus() {
        List<RefundRequest> requests = refundRequestRepository.findAll();
        for (RefundRequest request : requests) {
            if (request.getShippingStatusEnum() == ShippingStatusEnum.RETURNED ||
                    request.getShippingStatusEnum() == ShippingStatusEnum.CANCELLED) {
                continue;
            }

            LocalDateTime now = LocalDateTime.now();

            switch (request.getShippingStatusEnum()) {
                case PENDING:
                    if (request.getUpdatedAt().plusMinutes(1).isBefore(now)) {
                        addShippingStatus(request, ShippingStatusEnum.PENDING);
                        request.setShippingStatusEnum(ShippingStatusEnum.SHIPPED);
                        request.setUpdatedAt(now);
                        refundRequestRepository.save(request);
                        addShippingStatus(request, ShippingStatusEnum.SHIPPED);
                    }
                    break;
                case SHIPPED:
                    if (request.getUpdatedAt().plusMinutes(1).isBefore(now)) {
                        request.setShippingStatusEnum(ShippingStatusEnum.IN_TRANSIT);
                        request.setUpdatedAt(now);
                        refundRequestRepository.save(request);
                        addShippingStatus(request, ShippingStatusEnum.IN_TRANSIT);
                    }
                    break;
                case IN_TRANSIT:
                    if (request.getUpdatedAt().plusMinutes(1).isBefore(now)) {
                        request.setShippingStatusEnum(ShippingStatusEnum.DELIVERED);
                        request.setUpdatedAt(now);
                        refundRequestRepository.save(request);
                        addShippingStatus(request, ShippingStatusEnum.DELIVERED);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private void addShippingStatus(RefundRequest request, ShippingStatusEnum status) {
        ShippingStatus shippingStatus = new ShippingStatus();
        shippingStatus.setStatus(status);
        shippingStatus.setTrackingCode(request.getReturnCode());


        Product product = productRepository.findById(request.getProduct().getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));


        shippingStatus.setProductName(product.getProductName());
        shippingStatus.setPrice(product.getPrice());
        shippingStatus.setUpdatedAt(LocalDateTime.now());


        shippingStatus.setRefundProcessId(request.getId());


        if (status == ShippingStatusEnum.PENDING) {
            shippingStatus.setRefundRequest(request);
        }

        shippingStatusRepository.save(shippingStatus);
    }

}