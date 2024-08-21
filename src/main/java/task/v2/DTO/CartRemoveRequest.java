package task.v2.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartRemoveRequest {
    private Long userId;
    private Long productId;
}
