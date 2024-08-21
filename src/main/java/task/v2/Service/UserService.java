package task.v2.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import task.v2.Core.results.*;
import task.v2.Entity.User;
import task.v2.Repository.UserRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public DataResult<User> getUser(Long id) {
        try {
            User user = userRepository.findByUserId(id);
            return new SuccessDataResult<>(user,"User dödürüldü");

        } catch (Exception e) {
            return new ErrorDataResult<>("User getirilemedi: " + e.getMessage());
        }
    }

    public DataResult<List<User>> getAllUsers() {
        try {
            List<User> users = userRepository.findAll();
            return new SuccessDataResult<>(users, "Users başarıyla getirildi.");
        } catch (Exception e) {
            return new ErrorDataResult<>("Users getirilemedi: " + e.getMessage());
        }
    }

    public DataResult<User> createUser(User user) {
        try {
            User newUser = userRepository.save(user);
            return new SuccessDataResult<>(newUser, "User başarıyla oluşturuldu.");
        } catch (Exception e) {
            return new ErrorDataResult<>("User oluşturulamadı: " + e.getMessage());
        }
    }

    public DataResult<User> updateUser(Long id, User userDetails) {
        try {
            User user = userRepository.findByUserId(id);
            if (user.getUserId() == null) {
                return new ErrorDataResult<>("Bu ID'ye sahip bir kullanıcı bulunamadı.");
            }

            user.setUserName(userDetails.getUserName());
            user.setPassword(userDetails.getPassword());
            user.setBalance(userDetails.getBalance());
            user.setEmail(userDetails.getEmail());
            User updatedUser = userRepository.save(user);
            return new SuccessDataResult<>(updatedUser, "User başarıyla güncellendi.");
        } catch (Exception e) {
            return new ErrorDataResult<>("User güncellenemedi: " + e.getMessage());
        }
    }

    public Result deleteUser(Long id) {
        try {
            User user = userRepository.findByUserId(id);
            if (user.getUserId() == null) {
                return new ErrorResult("Bu ID'ye sahip bir kullanıcı bulunamadı.");
            }
            userRepository.deleteById(id);
            return new SuccessResult("User başarıyla silindi.");
        } catch (Exception e) {
            return new ErrorResult("User silinemedi: " + e.getMessage());
        }
    }

    public void creditBalance(User user, Double amount) {
        user.setBalance(user.getBalance() + (amount));
        userRepository.save(user);
    }

}
