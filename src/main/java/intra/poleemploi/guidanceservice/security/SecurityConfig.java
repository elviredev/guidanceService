package intra.poleemploi.guidanceservice.security;

import intra.poleemploi.guidanceservice.dao.UserAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserAppRepository userAppRepository;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //http.formLogin();
        http.requiresChannel()
                .requestMatchers(r -> r.getHeader("X-Forwarded-Proto") != null)
                .requiresSecure();
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers("/login/**", "/adminUsers/**", "/applis/**", "/contents/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/userApps/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/roleApps/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/applis/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/contents/**").permitAll();
        http.authorizeRequests().antMatchers("/applis/**").hasAuthority("ADMIN");
        http.authorizeRequests().antMatchers("/contents/**").hasAuthority("USER");
        http.authorizeRequests().antMatchers("/userApps/**", "/roleApps/**", "/updateUserRoles/**", "/updateUserApplis/**").hasAuthority("ADMIN");
        http.authorizeRequests().anyRequest().authenticated();

        http.addFilter(new JWTAuthenticationFilter(authenticationManager(), userAppRepository));
        http.addFilterBefore(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
