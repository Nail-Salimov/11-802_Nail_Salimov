package server;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import server.config.ApplicationContextConfigWithAnnotation;
import server.model.UserDto;
import server.service.UserService;

import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        //with annotation
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationContextConfigWithAnnotation.class);
        UserService userService = context.getBean(UserService.class);
        Optional<UserDto> optionalUserDto = userService.findUserByName("Oleg");
        System.out.println(optionalUserDto.get().getSurname());


        //with context.xml
        ApplicationContext contextXML = new ClassPathXmlApplicationContext("context.xml");
        UserService userService1 = context.getBean(UserService.class);
        Optional<UserDto> optionalUserDto1 = userService1.findUserByName("Oleg");
        System.out.println(optionalUserDto1.get().getSurname());
    }
}
