package com.example.project;

import com.example.project.image.FilesStorageService;
import com.example.project.models.entities.Rol;
import com.example.project.models.entities.Usuario;
import com.example.project.repositories.RolRepository;
import com.example.project.repositories.UserRepository;
import freemarker.core.CommandLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@SpringBootApplication
public class ProjectApplication extends SpringBootServletInitializer implements CommandLineRunner {

	@Resource
	FilesStorageService storageService;

	public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ProjectApplication.class);
	}

	@Override
	public void run(String... arg) throws Exception {
		storageService.init();
	}

	@Component
	public static class DataLoader implements ApplicationRunner {

		@Autowired
		private RolRepository rolRepository;

		@Autowired
		private UserRepository userRepository;

		@Autowired
		PasswordEncoder encoder;

		@Override
		public void run(ApplicationArguments args) throws Exception {
			//Añadiendo Roles:
			rolRepository.save(new Rol(1L,"usuario"));
			rolRepository.save(new Rol(2L,"admin"));

		}
	}
}
