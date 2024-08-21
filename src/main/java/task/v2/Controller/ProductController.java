package task.v2.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import task.v2.Core.results.DataResult;
import task.v2.Core.results.Result;
import task.v2.Entity.Product;
import task.v2.Service.ProductService;

import java.util.List;

@RequestMapping("/api/product")
@RestController
public class ProductController {

    @Autowired
    public ProductService productService;

    @GetMapping("/get/{id}")
    public ResponseEntity<DataResult<Product>> getProductById(@PathVariable Long id) {
        DataResult<Product> result = productService.getProduct(id);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(404).body(result);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<DataResult<List<Product>>> getAllProducts() {
        DataResult<List<Product>> result = productService.getAllProduct();
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }


    @PatchMapping("/update/{id}")
    public ResponseEntity<DataResult<Product>> updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
        DataResult<Product> result = productService.updateProduct(id, productDetails);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(400).body(result);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Result> deleteProductById(@PathVariable Long id) {
        Result result = productService.deleteProductById(id);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(404).body(result);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        Product savedProduct = productService.addProduct(product);
        return ResponseEntity.ok(savedProduct);
    }


}
