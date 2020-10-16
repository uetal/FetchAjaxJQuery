package crud.controller;

import crud.model.Role;
import crud.model.User;
import crud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UserService userService;

    @GetMapping(value = "/")
    public String getAdminPage(Principal principal, ModelMap modelMap) {
        User user = (User) userService.loadUserByUsername(principal.getName());
        modelMap.addAttribute("user1" ,user);
        List<User> list= userService.listUsers();
        modelMap.addAttribute("list", list);
        return "admin/adminpage";
    }

    @GetMapping(value = "/delete/{id}")
    public String deleteUser(@RequestParam("deleteid")Long id){
        User user = userService.getById(id);
        userService.delete(user);
        return "redirect:/admin/";
    }

    @PostMapping(value = "/new")
    public String addUser(
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("age") int age,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("roles") String[] roles
    ) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setAge(age);
        user.setEmail(email);
        user.setPassword(password);
        Set<Role>setRole=new HashSet<>();
        for (String role : roles) {
            if (Integer.parseInt(role)==2) {
                setRole.add(new Role(2L, "ROLE_ADMIN"));
            }
            if (Integer.parseInt(role)==1) {
                setRole.add(new Role(1L, "ROLE_USER"));
            }
        }
//        setRole.add(new Role(1L, "ROLE_USER"));
        user.setRoles(setRole);
        userService.add(user);
        return "redirect:/admin/";
    }


    @GetMapping(value = "/edit/{id}")
    public String editUser(
            @RequestParam("editid") Long id,
            @RequestParam("editfirstName") String firstname,
            @RequestParam("editlastName") String lastname,
            @RequestParam("editage") int age,
            @RequestParam("editemail") String email,
            @RequestParam("editpassword") String password,
            @RequestParam("roles") String[] roles
    ) {
        User user = userService.getById(id);
        user.setFirstName(firstname);
        user.setLastName(lastname);
        user.setAge(age);
        user.setEmail(email);
        user.setPassword(password);
        Set<Role>setRole=new HashSet<>();
        for (String role : roles) {
            if (Integer.parseInt(role)==2) {
                setRole.add(new Role(2L, "ROLE_ADMIN"));
            }
            if (Integer.parseInt(role)==1) {
                setRole.add(new Role(1L, "ROLE_USER"));
            }
        }
        if (setRole.size()==0){
            setRole.add(new Role(1L, "ROLE_USER"));
        }
        userService.update(user);
        return "redirect:/admin/";
    }
}
