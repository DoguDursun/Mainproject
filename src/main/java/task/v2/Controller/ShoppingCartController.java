package task.v2.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import task.v2.DTO.CartRemoveRequest;
import task.v2.DTO.CartRequest;
import task.v2.DTO.CartUpdateRequest;
import task.v2.Entity.ShoppingCart;
import task.v2.Service.ShoppingCartService;

@RestController
@RequestMapping("/api/cart")
public class ShoppingCartController {

    @Autowired
    public ShoppingCartService shoppingCartService;


    @GetMapping("/getCart/{userId}")
    public ResponseEntity<ShoppingCart> getShoppingCartByUserId(@PathVariable Long userId){
        ShoppingCart cart = shoppingCartService.getShoppingCartByUserId(userId);
        if (cart != null){
            return ResponseEntity.ok(cart);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/addItem")
    public ResponseEntity<ShoppingCart> addItemToCart(@RequestBody CartRequest request) {
        ShoppingCart updatedCart = shoppingCartService.addItemToCart(request.getUserId(), request.getProductId(), request.getQuantity());
        return ResponseEntity.ok(updatedCart);
    }

    @DeleteMapping("/removeItem")
    public ResponseEntity<ShoppingCart> removeItemFromCart(@RequestBody CartRemoveRequest cartRemoveRequest){
        ShoppingCart updatedCart = shoppingCartService.removeItemFromCart(
                cartRemoveRequest.getUserId(),
                cartRemoveRequest.getProductId()
        );
        return ResponseEntity.ok(updatedCart);
    }

    @PatchMapping("/updateItem")
    public ResponseEntity<ShoppingCart> updateItemInCart(@RequestBody CartUpdateRequest cartUpdateRequest) {
        ShoppingCart updatedCart = shoppingCartService.updateItemInCart(
                cartUpdateRequest.getUserId(),
                cartUpdateRequest.getProductId(),
                cartUpdateRequest.getQuantity()
        );
        return ResponseEntity.ok(updatedCart);
    }

    @DeleteMapping("/removeCart")
    public ResponseEntity<String> removeCartByUserId(@RequestParam Long userId) {
        shoppingCartService.removeCartByUserId(userId);
        return ResponseEntity.ok("Sepet silindi.");
    }

    @PostMapping("/purchase")
    public ResponseEntity<String> purchaseCart(@RequestParam Long userId) {
        try {
            shoppingCartService.purchaseCart(userId);
            return ResponseEntity.ok("Satın alma işlemi başarılı");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


}
