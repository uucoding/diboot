import com.diboot.common.service.UserService;
import com.diboot.framework.model.BaseUser;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * 单元测试样例
 * @author Mazc@dibo.ltd
 * @version 2017/4/18
 * Copyright @ www.dibo.ltd
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UnitTestExample.class)
@Import(SpringTestConfig.class)
public class UnitTestExample{

    @Resource
    UserService userService;

    @Test
    public void testGetUser(){
        BaseUser user = userService.getUserByUsername("admin");
        Assert.assertTrue(user != null);
        System.out.println(user.getRealname());
    }

}
