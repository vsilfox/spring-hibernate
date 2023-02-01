package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;

public class MainApp {
    final static String CAR_MODEL_TO_GET_USER = "Mazda";
    final static int CAR_SERIES_TO_GET_USER = 3;

    public static void main(String[] args) throws SQLException {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        UserService userService = context.getBean(UserService.class);

        userService.add(new User("User1", "Lastname1", "user1@mail.ru", new Car("Volga", 1)));
        userService.add(new User("User2", "Lastname2", "user2@mail.ru", new Car("Mazda", 3)));
        userService.add(new User("User3", "Lastname3", "user3@mail.ru", new Car("Mazda", 3)));

        List<User> users = userService.listUsers();
        users.forEach(System.out::println);

        List<User> usersByCar = userService.getUserByCarModelAndSerial(CAR_MODEL_TO_GET_USER, CAR_SERIES_TO_GET_USER);
        System.out.printf("Users with car %s, series %s:\n", CAR_MODEL_TO_GET_USER, CAR_SERIES_TO_GET_USER);
        usersByCar.forEach(System.out::println);

        context.close();
    }
}
