package com.example.weather_service.weather_service.service;

import com.example.weather_service.weather_service.entity.Role;
import com.example.weather_service.weather_service.entity.Users;
import com.example.weather_service.weather_service.repository.UserDetailsRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

//this will be called on application startup by using CommandLineRunner and it is just for learning not mandatory for authentication, custom/default user/things to store password inside our application(inside h2 in memory database)
@Configuration
public class AdminUserInitializer {
    @Bean
    public CommandLineRunner createAdminUser(UserDetailsRepository userDetailsRepository, PasswordEncoder passwordEncoder){
        return args -> {
            if(userDetailsRepository.findByUsername("admin").isEmpty()){
                Users admin = new Users();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin1234"));//securely store password
                admin.setRole(Role.ADMIN);

                userDetailsRepository.save(admin);
                System.out.println("Default admin user created!");
            }
            if(userDetailsRepository.findByUsername("user").isEmpty()){
                Users user = new Users();
                user.setUsername("user");
                user.setPassword(passwordEncoder.encode("user1234"));//securely store password
                user.setRole(Role.USER);

                userDetailsRepository.save(user);
                System.out.println("Default normal user created!");
            }
        };
    }
}
