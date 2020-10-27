package crud.controller;

import crud.model.Role;
import crud.model.User;
import crud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.ResultSet;
import java.util.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UserService userService;

    @GetMapping(value = "/")
    public String getAdminPage(Principal principal, ModelMap modelMap) {
        User user = (User) userService.loadUserByUsername(principal.getName());
        modelMap.addAttribute("user1" ,user);
        List<String>listRole=new ArrayList<>();
        for (Role role : user.getRoles()){
            if (role.getName().equals("ROLE_USER")){
                listRole.add("2");
            }
        }
        modelMap.addAttribute("listRole", listRole);
        List<User> list= userService.listUsers();
        modelMap.addAttribute("list", list);
        return "admin/restadmin";
    }
}
