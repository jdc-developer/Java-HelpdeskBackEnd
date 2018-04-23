package jdc.helpdesk;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import jdc.helpdesk.entity.User;
import jdc.helpdesk.enums.Profile;
import jdc.helpdesk.repository.UserRepository;

@SpringBootApplication
public class JavaHelpdeskBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaHelpdeskBackEndApplication.class, args);
	}
	
	@Bean
	CommandLineRunner init(UserRepository repository, PasswordEncoder encoder) {
		return args -> {
			initUsers(repository, encoder);
		};
	}
	
	private void initUsers(UserRepository repository, PasswordEncoder encoder) {
		User admin = new User();
		admin.setEmail("admin@helpdesk.com");
		admin.setPassword(encoder.encode("123456"));
		admin.setProfile(Profile.ROLE_ADMIN);
		
		User find = repository.findByEmailIgnoreCaseContaining("admin@helpdesk.com");
		if(find == null) {
			repository.save(admin);
		}
	}
}
