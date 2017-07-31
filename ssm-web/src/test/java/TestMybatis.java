import com.leeyom.pojo.User;
import com.leeyom.service.user.UserService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestMybatis {

    private ApplicationContext ac = null;
    private UserService userService = null;

    @Before
    public void before() {
        ac = new ClassPathXmlApplicationContext("classpath:spring/spring-mybatis.xml");
        userService = (UserService) ac.getBean("userService");
    }

    @Test
    public void testMybtis() {
        User user = userService.getUserById(1);
        System.out.println(user.getuName() + "------>" + user.getuPassword());
    }

}
