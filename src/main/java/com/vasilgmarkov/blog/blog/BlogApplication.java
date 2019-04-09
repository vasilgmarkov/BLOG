package com.vasilgmarkov.blog.blog;

import com.vasilgmarkov.blog.blog.config.CustomUserDetails;
import com.vasilgmarkov.blog.blog.config.FileStorageProperties;
import com.vasilgmarkov.blog.blog.entities.Role;
import com.vasilgmarkov.blog.blog.entities.User;
import com.vasilgmarkov.blog.blog.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.Arrays;

@SpringBootApplication
@EnableConfigurationProperties({
		FileStorageProperties.class
})
public class BlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}
//
//	@Autowired
//	public void authenticationManager(AuthenticationManagerBuilder builder, UserRepository userRepository) throws Exception{
//		builder.userDetailsService(new UserDetailsService() {
//			@Override
//			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//				return new CustomUserDetails(userRepository.findByUsername(username));
//			}
//		});
//	}

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	public void authenticationManager(AuthenticationManagerBuilder builder, UserRepository userRepository) throws Exception{
		if(userRepository.count() == 0){
			userRepository.save(new User("user",passwordEncoder.encode("user"), Arrays.asList(new Role("USER"), new Role("ACTUATOR"))));
			userRepository.save(new User("usertwo",passwordEncoder.encode("user"), Arrays.asList(new Role("USER"), new Role("ACTUATOR"))));
		}
		builder.userDetailsService(username -> new CustomUserDetails(userRepository.findByUsername(username)));
	}



}
