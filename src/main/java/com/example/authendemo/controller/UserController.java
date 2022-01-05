package com.example.authendemo.controller;

import com.example.authendemo.entity.User;
import com.example.authendemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(method = RequestMethod.POST , path = "add")
    public User save(@RequestBody User user){
        return userService.save(user);
    }

//    @RequestMapping(method = RequestMethod.POST , path = "add")
//    public User save(){
//        return new User();
//    }

    @RequestMapping(method = RequestMethod.PUT , path = "/edit")
    public User update(@RequestBody User user){
        return userService.update(user);
    }

    @RequestMapping(method = RequestMethod.DELETE , path = "/delete")
    public boolean delete(@RequestParam(value = "id") Long id){
       return userService.delete(id);
    }
    @RequestMapping(method = RequestMethod.GET , path = "/detail/{id}")
    public User getDetail(@PathVariable Long id){
        return userService.getDetail(id);
    }

}
