package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    //login 페이지에 입력한 name, password 맞으면 login 성공
    @GetMapping("/")
    public String goTologinPage(Model model) {

        return "login";
    }


    //아직 password검증은 안함...;;;;;;;;;;
    //login 인증 실패시 어떻게 redirect 할지 생각해보기
    @PostMapping("/login")
    public String loginAuth(@RequestParam(name = "name") String name,
                            @RequestParam(name = "password") String password,
                            Model model) {
        if (!userService.findName(name).isEmpty()) {
            List<User> userList = userService.findName(name);
            if(userList.get(0).getPassword().equals(password)){
                //System.out.println(userList);
                model.addAttribute("name", name);
                return "redirect:/dashBoard";
            }

        } else {
            // 사용자 인증 실패시에 다시 login.html로
            return "login";
        }
        return "login";
    }

}

