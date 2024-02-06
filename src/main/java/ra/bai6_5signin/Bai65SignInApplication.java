package ra.bai6_5signin;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class Bai65SignInApplication {

    public static void main(String[] args) {
        SpringApplication.run(Bai65SignInApplication.class, args);
    }
@Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
}
@Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
}
}
