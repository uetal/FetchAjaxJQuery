package crud.controller;

import crud.model.Role;
import crud.model.User;
import crud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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
        modelMap.addAttribute(user);
        List<User> list= userService.listUsers();
        modelMap.addAttribute("list", list);
        return "admin/adminpage";
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
        setRole.add(new Role(1L, "ROLE_USER"));
        user.setRoles(setRole);
        userService.add(user);
        return "redirect:/admin/";
    }

//    @PostMapping(value = "/new")
//    public String addUser(@RequestParam("name")String name,
//                          @RequestParam("password")String password)
//    {
//        Set<Role> set = new HashSet<>();
//        set.add(new Role(1L,"ROLE_USER"));
//        User user = new User();
//        user.setUsername(name);
//        user.setPassword(password);
//        user.setRoles(set);
//        userService.add(user);
//        return "redirect:/admin/users";
//    }
//
//    @GetMapping(value = "/new")
//    public String newUser() {
//        return "admin/new";
//    }

//    @GetMapping(value = "/edit/{id}")
//    public String editUser(@PathVariable("id") Long id, ModelMap modelMap) {
//        User user = userService.getById(id);
//        List<Role>roleList=userService.listRoles();
//        modelMap.addAttribute("user",user);
//        modelMap.addAttribute("rolelist",roleList);
//        return "admin/update";
//    }

//    @PostMapping(value = "/edit")
//    public String updateUser(@RequestParam("id") Long id,
//                             @RequestParam("name")String name,
//                             @RequestParam("roles") String[] roles,
//                             @RequestParam("password")String password)
//    {
//        Set<Role>setRoles=new HashSet<>();
//        for (String role : roles) {
//            if (Integer.parseInt(role)==2) {
//                setRoles.add(new Role(2L, "ROLE_ADMIN"));
//            }
//            if (Integer.parseInt(role)==1) {
//                setRoles.add(new Role(1L, "ROLE_USER"));
//            }
//        }
//        User user = new User();
//        user.setUsername(name);
//        user.setPassword(password);
//        user.setId(id);
//        user.setRoles(setRoles);
//        userService.update(user);
//        return "redirect:/admin/users";
//    }

    @GetMapping(value = "/delete/{id}")
    public String deleteUser(@PathVariable("id")Long id){
        User user = userService.getById(id);
        userService.delete(user);
        return "redirect:/admin/";
    }


}
