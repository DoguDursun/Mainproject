package task.v2.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import task.v2.Entity.*;
import task.v2.Repository.ProductRepository;
import task.v2.Repository.ShoppingCartRepository;
import task.v2.Repository.TransactionRepository;
import task.v2.Repository.UserRepository;

import java.util.ArrayList;

@Service
public class ShoppingCartService {

    @Autowired
    public ShoppingCartRepository shoppingCartRepository;
    @Autowired
    public ProductRepository productRepository;
    @Autowired
    public UserRepository userRepository;
    @Autowired
    public TransactionRepository transactionRepository;

    public ShoppingCart getShoppingCartByUserId(Long userId) {
        return shoppingCartRepository.findByUser_UserId(userId);
    }

    public ShoppingCart addItemToCart(Long userId, Long productId, int quantity) {
        ShoppingCart cart = shoppingCartRepository.findByUser_UserId(userId);

        if (cart == null) {
            cart = new ShoppingCart();
            cart.setItems(new ArrayList<>()); // Initialize the items list

            User user = userRepository.findByUserId(userId);
            if (user == null) {
                throw new RuntimeException("Kullanıcı bulunamadı");
            }

            cart.setUser(user);
            cart = shoppingCartRepository.save(cart);
        }

        Product product = productRepository.findByproductId(productId);
        if (product == null) {
            throw new RuntimeException("Ürün bulunamadı");
        }

        ShoppingCartItem existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getProductId().equals(productId))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
        } else {
            ShoppingCartItem newItem = new ShoppingCartItem();
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            newItem.setCart(cart);
            cart.getItems().add(newItem);
        }

        return shoppingCartRepository.save(cart);
    }

    public ShoppingCart removeItemFromCart(Long userId, Long productId) {
        ShoppingCart cart = shoppingCartRepository.findByUser_UserId(userId);
        if (cart != null) {
            cart.getItems().removeIf(item -> item.getProduct().getProductId().equals(productId));
            return shoppingCartRepository.save(cart);
        }
        throw new RuntimeException("Sepet bulunamadı");
    }

    public ShoppingCart updateItemInCart(Long userId, Long productId, int quantity) {
        ShoppingCart cart = shoppingCartRepository.findByUser_UserId(userId);
        if (cart != null) {
            for (ShoppingCartItem item : cart.getItems()) {
                if (item.getProduct().getProductId().equals(productId)) {
                    item.setQuantity(quantity);
                    break;
                }
            }
            return shoppingCartRepository.save(cart);
        }
        throw new RuntimeException("Sepet bulunamadı");
    }

    public void removeCartByUserId(Long userId) {
        ShoppingCart cart = shoppingCartRepository.findByUser_UserId(userId);
        if (cart != null) {
            shoppingCartRepository.delete(cart);
        } else {
            throw new RuntimeException("Shopping cart not found for the given user.");
        }
    }

    public void purchaseCart(Long userId) {
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            throw new RuntimeException("Kullanıcı bulunamadı");
        }

        ShoppingCart cart = shoppingCartRepository.findByUser_UserId(userId);
        if (cart == null) {
            throw new RuntimeException("Sepet bulunamadı");

        }

        double totalAmount = cart.getItems().stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();

        if (user.getBalance() < totalAmount) {
            throw new RuntimeException("Yetersiz bakiye");
        }

        user.setBalance(user.getBalance() - totalAmount);
        userRepository.save(user);

        for (ShoppingCartItem item : cart.getItems()) {
            Transaction transaction = new Transaction();
            transaction.setUser(user);
            transaction.setProduct(item.getProduct());
            transaction.setQuantity(item.getQuantity());
            transaction.setTotalPrice(item.getProduct().getPrice() * item.getQuantity());
            transactionRepository.save(transaction);

        }

        cart.getItems().clear();
        shoppingCartRepository.save(cart);
    }
}