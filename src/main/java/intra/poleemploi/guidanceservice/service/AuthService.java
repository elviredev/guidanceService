package intra.poleemploi.guidanceservice.service;

import intra.poleemploi.guidanceservice.entities.RoleApp;
import intra.poleemploi.guidanceservice.entities.UserApp;

public interface AuthService {
    UserApp saveUserApp(String username, String password, String confirmedPassword);
    UserApp saveUserApp(UserApp userApp);
    UserApp loadUserAppByUsername(String username);
    RoleApp saveRoleApp(RoleApp role);
    void addRoleToUser(String username, String roleName);
    void addAppliToUser(String username, String appliName);
}
