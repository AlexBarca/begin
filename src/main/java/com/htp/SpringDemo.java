package com.htp;

import com.htp.dao.UserDao;
import com.htp.domain.Role;
import com.htp.domain.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringDemo {
    public static void main(String[] args) {


    //1. Download pom xml dependencies
    //2. Create application-context.xml or use annotation @Component
    //3. Use @Autowired for DI
    //4. Use @Qualifier for bean name definition
    //5. For test purpose only: use ClassPathXmlApplicationContext or AnnotationConfigApplicationContext
    //   for Spring Context calling and getting bean

//    ApplicationContext context = new ClassPathXmlApplicationContext("classpath:application-context.xml");
    ApplicationContext context = new AnnotationConfigApplicationContext("com.htp");
//        User user = (User) context.getBean("user");
//        Role role = (Role) context.getBean("role");
//
//        System.out.println(user);
//        System.out.println(role);

    //6. Getting bean by name (possible get bean by class name)
    UserDao userDaoImpl = (UserDao) context.getBean("userDaoImpl");
    UserDao userDaoImplByClassName = context.getBean(UserDao.class);
//
//    //7. Call method as usual
    String login = userDaoImpl.findOne(Long.parseLong("4")).getLogin();
    String login2 = userDaoImplByClassName.findOne(Long.parseLong("5")).getLogin();
        System.out.println(login);
        System.out.println(login2);


    }
}
