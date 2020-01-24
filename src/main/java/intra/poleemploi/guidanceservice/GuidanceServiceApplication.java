package intra.poleemploi.guidanceservice;

import intra.poleemploi.guidanceservice.dao.RoleAppRepository;
import intra.poleemploi.guidanceservice.dao.UserAppRepository;
import intra.poleemploi.guidanceservice.entities.RoleApp;
import intra.poleemploi.guidanceservice.entities.UserApp;
import intra.poleemploi.guidanceservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;
import java.util.stream.Stream;

import static org.springframework.boot.SpringApplication.run;

@SpringBootApplication
public class GuidanceServiceApplication{

	public static void main(String[] args) {
		SpringApplication.run(GuidanceServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner start(AuthService authService, UserAppRepository userAppRepository){
		return args -> {
			authService.saveRoleApp(new RoleApp(null, "USER"));
			authService.saveRoleApp(new RoleApp(null, "ADMIN"));

			Stream.of("user1", "user2", "user3", "user4", "admin").forEach(un -> {
				authService.saveUserApp(un, "1234", "1234");
			});
			authService.addRoleToUser("admin", "ADMIN");

			userAppRepository.findAll().forEach(userApp -> {
				System.out.println(userApp.toString());
			});
		};
	}

	/*@Bean
	BCryptPasswordEncoder getBCPE() {
		return new BCryptPasswordEncoder();
	}*/
}
