package intra.poleemploi.guidanceservice.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import intra.poleemploi.guidanceservice.dao.UserAppRepository;
import intra.poleemploi.guidanceservice.entities.UserApp;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;
    private UserAppRepository userAppRepository;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, UserAppRepository userAppRepository) {
        this.authenticationManager = authenticationManager;
        this.userAppRepository = userAppRepository;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            UserApp userApp = new ObjectMapper().readValue(request.getInputStream(), UserApp.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userApp.getUsername(), userApp.getPassword()));
        } catch(IOException e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User user = (User)authResult.getPrincipal();
        List<String> roles = new ArrayList<>();
        authResult.getAuthorities().forEach(a -> {
            roles.add(a.getAuthority());
        });

        UserApp userApp = userAppRepository.findUserByUsername(user.getUsername());
        String jwt = JWT.create()
                .withIssuer(request.getRequestURI())
                .withClaim("id", userApp.getId())
                //.withSubject(userApp.getUsername())
                .withClaim("username", userApp.getUsername())
                .withArrayClaim("roles", roles.toArray(new String[roles.size()]))
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityParams.EXPIRATION))
                .sign(Algorithm.HMAC256(SecurityParams.SECRET));
        response.addHeader(SecurityParams.JWT_HEADER_NAME, jwt);
    }
}
