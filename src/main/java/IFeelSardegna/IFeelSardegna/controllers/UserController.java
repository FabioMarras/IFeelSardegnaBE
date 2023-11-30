package IFeelSardegna.IFeelSardegna.controllers;

import IFeelSardegna.IFeelSardegna.entites.User;
import IFeelSardegna.IFeelSardegna.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("")
    public Page<User> getUser(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int size,
                              @RequestParam(defaultValue = "id") String orderBy){
        return userService.getUsers(page, size, orderBy);
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable long id){
        return userService.findById(id);
    }

    @PutMapping("/{id}")
    public User findByIdAndUpdate(@PathVariable long id, @RequestBody User body){
        return userService.findByIdAndUpdate(id, body);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public String findByIdAndDelete(@PathVariable long id) {
       return userService.findByIdAndDelete(id);
    }

    @PostMapping("/upload/{id}")
    public String uploadAvatar(@PathVariable long id, @RequestParam("avatar")MultipartFile body) throws IOException {
        return userService.uploadAvatar(id, body);
    }

    @GetMapping("/me")
    public UserDetails getMyProfile(@AuthenticationPrincipal UserDetails currentUser){
        return currentUser;
    }

    @PutMapping("/me")
    public UserDetails putMyProfile(@AuthenticationPrincipal User currentUser, @RequestBody User body){
        return userService.findByIdAndUpdate(currentUser.getId(), body);
    }
    @DeleteMapping("/me")
    public void deleteMyProfile(@AuthenticationPrincipal User currentUser){
        userService.findByIdAndDelete(currentUser.getId());
    }
}
