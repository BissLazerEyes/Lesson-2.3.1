package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import web.dao.UserDao;
import web.model.User;

import javax.validation.Valid;

@Controller
public class UserController {
    private UserDao dao;

    @Autowired
    public UserController(UserDao dao) {
        this.dao = dao;
    }

    @ModelAttribute("newUser")
    public User getPerson(){
        return new User();
    }
    @GetMapping("/people")
	public String index(Model model){
    	model.addAttribute("people",dao.getAllUsers());
    	return "view/index";
	}

    @PostMapping("/people")
    public String creat(@ModelAttribute("newUser")@Valid User user,
                        BindingResult bindingResult,Model model) {
        if (bindingResult.hasErrors()){
            model.addAttribute("people",dao.getAllUsers());
            return "view/index";
        }
    	dao.saveUser(user);
    	return "redirect:/people";
    }

    @DeleteMapping("/people/{id}")
    public String deletePerson(@PathVariable("id") int id){
        dao.removeUserById(id);
        return "redirect:/people";
    }
    @GetMapping("/people/{id}/edit")
    public String edit (@ModelAttribute("id") int id,Model model){
        model.addAttribute("user",dao.getUserById(id));
        return "view/edit";
    }

    @PatchMapping("/people/{id}")
    public String updatePerson(@ModelAttribute("user")@Valid User updateUser, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "view/edit";
        }
        dao.updateUser(updateUser);
        return "redirect:/people";
    }
}