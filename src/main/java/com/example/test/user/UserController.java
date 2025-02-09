package com.example.test.user;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService service;
    @GetMapping("/users/search")
    public String searchUsers(Model model,
                              @RequestParam(value = "keyword", defaultValue = "") String keyword) {
        List<User> listUsers = service.listAll(keyword);
        model.addAttribute("users", listUsers);
        model.addAttribute("keyword", keyword);
        return "users";
    }
    @GetMapping("/users")
    public String listUsers(Model model,
                            @RequestParam(value = "keyword", required = false) String keyword) {
        List<User> listUsers = service.listAll(keyword);
        model.addAttribute("users", listUsers);
        model.addAttribute("keyword", keyword);
        return "users";
    }

    @GetMapping("/users/new")
    public String showNewForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("pageTitle", "Add new user");
        return "user_form";
    }


    @PostMapping("/users/save")
    public String saveUser(User user, RedirectAttributes ra) {
        service.save(user);
        ra.addFlashAttribute("message", "The user has been saved successfully . ");
        return "redirect:/users";
    }

    @GetMapping("/users/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        try {
            User user = service.get(id);
            model.addAttribute("user", user);
            model.addAttribute("pageTitle", "Edit user (ID: " + id + ")");
            return "user_form";  // Add return statement to navigate to the form
        } catch (UserNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/users";
        }
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id,  RedirectAttributes ra) {

        return "redirect:/users";
    }

//    @GetMapping("/users/search")
//    public String searchUsers(Model model,
//                              @RequestParam(value = "keyword", defaultValue = "") String keyword) {
//        List<User> listUsers = service.listAll(keyword);
//        model.addAttribute("users", listUsers);
//        model.addAttribute("keyword", keyword);
//        return "users";
//    }

}