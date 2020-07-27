package com.anbara.jwtsecurity;

import com.anbara.jwtsecurity.dao.TaskRepository;
import com.anbara.jwtsecurity.dao.UserRepository;
import com.anbara.jwtsecurity.entities.AppRole;
import com.anbara.jwtsecurity.entities.AppUser;
import com.anbara.jwtsecurity.entities.Task;
import com.anbara.jwtsecurity.service.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.stream.Stream;

@SpringBootApplication
public class JwtSecurityApplication implements CommandLineRunner {
    private final TaskRepository taskRepository;
    private final AccountService accountService;

    public JwtSecurityApplication(TaskRepository taskRepository, AccountService accountService) {
        this.taskRepository = taskRepository;
        this.accountService = accountService;

    }

    public static void main(String[] args) {
        SpringApplication.run(JwtSecurityApplication.class, args);
    }

    @Override

    public void run(String... args) throws Exception {

        Stream.of("T1", "T2", "T3").forEach(
                name -> {
                    taskRepository.save(new Task(null, name));
                }
        );
        taskRepository.findAll().forEach(System.out::println);

        accountService.saveUser(new AppUser("admin","1234",null));
        accountService.saveUser(new AppUser( "user","1234",null));

        accountService.saveRole(new AppRole(null,"ADMIN"));
        accountService.saveRole(new AppRole(null,"USER"));

        accountService.addRoleToUser("admin","ADMIN");
        accountService.addRoleToUser("admin","USER");

        accountService.addRoleToUser("user","USER");

    }
}
