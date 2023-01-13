package com.fundamentosplatzi.springboot.fundamentos;

import com.fundamentosplatzi.springboot.fundamentos.bean.MyBean;
import com.fundamentosplatzi.springboot.fundamentos.bean.MyBeanWithProperties;
import com.fundamentosplatzi.springboot.fundamentos.component.ComponentDependency;
import com.fundamentosplatzi.springboot.fundamentos.entity.User;
import com.fundamentosplatzi.springboot.fundamentos.pojo.UserPojo;
import com.fundamentosplatzi.springboot.fundamentos.repository.UserRepository;
import com.fundamentosplatzi.springboot.fundamentos.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootApplication
@Slf4j
public class FundamentosApplication implements CommandLineRunner {

    private ComponentDependency componentDependency;
    private MyBean myBean;
    private MyBeanWithProperties myBeanWithProperties;
    private UserPojo userPojo;
    private UserRepository userRepository;
    private UserService userService;
    public FundamentosApplication(@Qualifier("componentImplement") ComponentDependency componentDependency, MyBean myBean, MyBeanWithProperties myBeanWithProperties, UserPojo userPojo, UserRepository userRepository, UserService userService) {
        this.componentDependency = componentDependency;
        this.myBean = myBean;
        this.myBeanWithProperties = myBeanWithProperties;
        this.userPojo = userPojo;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    public static void main(String[] args) {
        SpringApplication.run(FundamentosApplication.class, args);
    }

    @Override
    public void run(String... args) {
//        setup();
//        saveUsersInDataBase();
        getInformationJpqlFromUser();
        saveWithErrorTransactional();
    }

    private void getInformationJpqlFromUser() {
        AtomicInteger count = new AtomicInteger();
        List<User> users = userRepository.findAll();
        users.stream().forEach(user -> {
            System.out.println((count.getAndIncrement() + 1) + " " + user);
        });

//        log.info("Usuario con el metodo findByUserEmail: " + userRepository.findByUserEmail("john@domain.com").orElseThrow(() -> new RuntimeException("No se encontro el usuario")));
//
//        userRepository.findAndSort("user", Sort.by("id").descending()).stream().forEach(user -> {
//            log.info("Usuario con el metodo findAndSort: " + user);
//        });
//
//        userRepository.findByName("kevin").stream().forEach(user -> {
//            log.info("Usuario con el metodo findByName: " + user);
//        });
//
//        log.info("Usuario con query method findByEmailAndName: " + userRepository.findByEmailAndName("karen@domain.com", "Karen").orElseThrow(() -> new RuntimeException("No se encontro el usuario")));
//
//        userRepository.findByNameLike("%k%").stream().forEach(user -> {
//            log.info("Usuario con query method findByNameLike: " + user);
//        });
//
//        userRepository.findByNameOrEmail("Enrique", null).stream().forEach(user -> {
//            log.info("Usuario con query method findByNameOrEmail: " + user);
//        });
//
//        userRepository.findByBirthDateBetween(LocalDate.of(2020, 1, 1), LocalDate.of(2021, 6, 18)).stream().forEach(user -> {
//            log.info("Usuario con query method findByBirthDateBetween: " + user);
//        });
//
//        log.info("Usuario con query method findByNameContaining: " + userRepository.getAllByBirthDateAndName(LocalDate.of(2021, 11, 12), "Enrique").orElseThrow(() -> new RuntimeException("No se encontro el usuario")));
    }

    private void saveWithErrorTransactional() {
        User user1 = new User("TestTransactinal1", "TestTransactinal1@example.com", LocalDate.of(2021, 11, 12));
        User user2 = new User("TestTransactinal2", "TestTransactinal2@example.com", LocalDate.of(2021, 2, 11));
        User user3 = new User("TestTransactinal3", "TestTransactinal3@example.com", LocalDate.of(2021, 4, 5));
        User user4 = new User("TestTransactinal4", "TestTransactinal4@example.com", LocalDate.of(2021, 7, 7));

        List<User> users = Arrays.asList(user1, user2, user3, user4);

        try {
            userService.saveTransactional(users);
        } catch (Exception e) {
            log.error("Esta es una excepcion dentro del metodo transaccional " + e);
        }

        userService.getAllUsers().stream().forEach(user -> {
            log.info("Estos son los usuarios dentro del metodo transactional: " + user);
        });
    }

    private void saveUsersInDataBase() {
        User user1 = new User("John", "john@domain.com", LocalDate.of(2021, 3, 13));
        User user2 = new User("Marco", "marco@domain.com", LocalDate.of(2021, 12, 8));
        User user3 = new User("Daniela", "daniela@domain.com", LocalDate.of(2021, 9, 8));
        User user4 = new User("Marisol", "marisol@domain.com", LocalDate.of(2021, 6, 18));
        User user5 = new User("Karen", "karen@domain.com", LocalDate.of(2021, 1, 1));
        User user6 = new User("Carlos", "carlos@domain.com", LocalDate.of(2021, 7, 7));
        User user7 = new User("Enrique", "enrique@domain.com", LocalDate.of(2021, 11, 12));
        User user8 = new User("Luis", "luis@domain.com", LocalDate.of(2021, 2, 27));
        User user9 = new User("Paola", "paola@domain.com", LocalDate.of(2021, 4, 10));
        List<User> list = Arrays.asList(user1, user2, user3, user4, user5, user6, user7, user8, user9);
        list.forEach(userRepository::save);
    }

    private void setup() {
        componentDependency.saludar();
        myBean.print();
        System.out.println(myBeanWithProperties.function());
        System.out.println(userPojo.getEmail() + " - " + userPojo.getPassword());
    }
}
