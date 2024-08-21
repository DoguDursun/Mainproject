package task.v2.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import task.v2.Entity.Product;
import task.v2.Entity.User;
import task.v2.Repository.ProductRepository;
import task.v2.Repository.UserRepository;

import java.util.Arrays;
import java.util.List;

@Service
public class FakeDataService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    public void loadFakeData() {
        loadFakeUsers();
        loadFakeProducts();
    }

    public void loadFakeUsers() {
        List<User> users = Arrays.asList(
                new User(null, "John Doe", "password123", "john.doe@example.com", 1000.0),
                new User(null, "Jane Smith", "password123", "jane.smith@example.com", 2000.0),
                new User(null, "Mike Johnson", "password123", "mike.johnson@example.com", 500.0)
        );

        userRepository.saveAll(users);
    }

    public void loadFakeProducts() {
        List<Product> products = Arrays.asList(
                new Product(null, 10L, 1200.00, "Laptop"),
                new Product(null, 15L, 800.00, "Smartphone"),
                new Product(null, 20L, 200.00, "Headphones"),
                new Product(null, 8L, 400.00, "Monitor")
        );

        productRepository.saveAll(products);
    }

}
