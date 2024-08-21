package task.v2.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartUpdateRequest {
    private Long userId;
    private Long productId;
    private int quantity;
}
