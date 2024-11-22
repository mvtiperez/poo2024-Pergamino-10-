package ar.edu.unnoba.poo2024.allmusic;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import ar.edu.unnoba.poo2024.allmusic.services.AuthenticathionService;
import ar.edu.unnoba.poo2024.allmusic.entities.MusicArtiesUser;
import ar.edu.unnoba.poo2024.allmusic.entities.User;
import com.password4j.Password;
import ar.edu.unnoba.poo2024.allmusic.services.AuthorizationService;
import ar.edu.unnoba.poo2024.allmusic.services.UserService;
import ar.edu.unnoba.poo2024.allmusic.util.JwtTokenUtil;
import ar.edu.unnoba.poo2024.allmusic.util.PasswordEncoder;

@SpringBootApplication
public class AllmusicApplication {

    @Bean
    public PasswordEncoder returnPasswordEncoder() {
        return new PasswordEncoder();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    public static void main(String[] args) throws Exception {

        ApplicationContext context = SpringApplication.run(AllmusicApplication.class, args);

        UserService userService = context.getBean(UserService.class);
        PasswordEncoder passwordEncoder = context.getBean(PasswordEncoder.class);

        User user = new MusicArtiesUser();
        user.setUsername("mati");
        user.setPassword("123");
        String rawPassword = user.getPassword();
        Password.hash(rawPassword);

    }
}
