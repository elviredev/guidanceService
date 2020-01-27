package intra.poleemploi.guidanceservice.web;

import intra.poleemploi.guidanceservice.dao.UserAppRepository;
import intra.poleemploi.guidanceservice.entities.UserApp;
import intra.poleemploi.guidanceservice.service.AuthService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
public class UserController {
    @Autowired
    private AuthService authService;

    @PostMapping(value = "/adminUsers")
    public UserApp register(@RequestBody UserForm userForm){
        return authService.saveUserApp(userForm.getUsername(), userForm.getPassword(), userForm.getConfirmedPassword());
    }

}
@Data
class UserForm {
    private String username;
    private String password;
    private String confirmedPassword;
}
