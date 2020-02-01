package intra.poleemploi.guidanceservice.security;

import intra.poleemploi.guidanceservice.entities.UserApp;
import intra.poleemploi.guidanceservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private AuthService authService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserApp userApp = authService.loadUserAppByUsername(username);
        if (userApp == null) throw new UsernameNotFoundException("Invalid user");
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        userApp.getRoles().forEach(roleApp -> {
            authorities.add(new SimpleGrantedAuthority(roleApp.getRoleName()));
        });
        return new User(userApp.getUsername(), userApp.getPassword(), authorities);
    }
}
