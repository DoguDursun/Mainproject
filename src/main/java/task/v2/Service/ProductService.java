package task.v2.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import task.v2.Core.results.*;
import task.v2.Entity.Product;
import task.v2.Repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    public ProductRepository productRepository;

    public DataResult<Product> getProduct(Long productId){
        try {
            Product product = productRepository.findByproductId(productId);
            return new SuccessDataResult<>(product,"Product başarılı şekilde döndürüldü");
        }catch (Exception e){
            return new ErrorDataResult<>("Product döndürülemedi");
        }
    }

    public DataResult<List<Product>> getAllProduct(){
        try {
        List<Product> products = productRepository.findAll();
            return new SuccessDataResult<>(products,"Productlar getirildi.");
        }catch (Exception e){
            return new  ErrorDataResult("Productlar döndürülemedi.");
        }

    }

    public DataResult<Product> updateProduct(Long productId, Product productDetails) {
        try {
            Product product = productRepository.findByproductId(productId);
            if (product == null) {
                return new ErrorDataResult<>("Bu ID'ye sahip bir ürün bulunamadı.");
            }

            product.setProductName(productDetails.getProductName());
            product.setPrice(productDetails.getPrice());
            product.setStockQuantity(productDetails.getStockQuantity());
            Product updatedProduct = productRepository.save(product);

            return new SuccessDataResult<>(updatedProduct, "Ürün başarıyla güncellendi.");
        } catch (Exception e) {
            return new ErrorDataResult<>("Ürün güncellenemedi: " + e.getMessage());
        }
    }

    public Result deleteProductById(Long productId) {
        try {
            Product product = productRepository.findByproductId(productId);
            if (product == null) {
                return new ErrorResult("Bu ID'ye sahip bir ürün bulunamadı.");
            }

            productRepository.deleteById(productId);
            return new SuccessResult("Ürün başarıyla silindi.");
        } catch (Exception e) {
            return new ErrorResult("Ürün silinemedi: " + e.getMessage());
        }
    }

    public Product addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        return productRepository.save(product);
    }

}
