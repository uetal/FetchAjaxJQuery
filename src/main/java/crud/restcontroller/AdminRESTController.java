package crud.restcontroller;

import crud.model.User;
import crud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/restadmin")
public class AdminRESTController {

    UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    List<User>getAllUsers() {
        return userService.listUsers();
    }

    @GetMapping("/{id}")
    User getUserById(@PathVariable Long id) {
        User user = userService.getById(id);
        return user;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/add")
    void newUser(@RequestBody User newUser) {
        userService.add(newUser);
    }

    @DeleteMapping("/delete/{id}")
    void deleteUser(@PathVariable Long id) {
        userService.delete(userService.getById(id));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/edit/")
    User updateUser(@RequestBody User newUser) {
        User user = userService.getById(newUser.getId());
        user.setFirstName(newUser.getFirstName());
        user.setLastName(newUser.getLastName());
        user.setEmail(newUser.getEmail());
        user.setPassword(newUser.getPassword());
        user.setAge(newUser.getAge());
        user.setRoles(newUser.getRoles());
        userService.update(user);
        return user;
    }
}
