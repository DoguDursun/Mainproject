package task.v2.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import task.v2.Core.results.DataResult;
import task.v2.Core.results.Result;
import task.v2.Entity.User;
import task.v2.Service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/get/{id}")
    public ResponseEntity<DataResult<User>> getUser(@PathVariable Long id) {
        DataResult<User> result = userService.getUser(id);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<DataResult<List<User>>> getAllUsers() {
        DataResult<List<User>> result = userService.getAllUsers();
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }



    @PostMapping("/add")
    public ResponseEntity<DataResult<User>> createUser(@RequestBody User user) {
        DataResult<User> result = userService.createUser(user);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<DataResult<User>> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        DataResult<User> result = userService.updateUser(id, userDetails);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Result> deleteUser(@PathVariable Long id) {
        Result result = userService.deleteUser(id);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }

}
