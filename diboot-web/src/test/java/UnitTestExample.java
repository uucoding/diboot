import com.diboot.common.service.UserService;
import com.diboot.components.msg.sms.config.MsgConfig;
import com.diboot.framework.model.BaseUser;
import com.diboot.framework.service.SystemConfigService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Map;

/**
 * 单元测试样例
 * @author Mazc@dibo.ltd
 * @version 2017/4/18
 * Copyright @ www.dibo.ltd
 */
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={SpringTestConfig.class})
public class UnitTestExample {

    @Autowired
    UserService userService;

    @Test
    public void testGetUser(){
        BaseUser user = userService.getUserByUsername("admin");
        Assert.assertTrue(user != null);
        System.out.println(user.getRealname());
    }

    @Autowired
    SystemConfigService systemConfigService;

    @Test
    public void testSystemConfig(){
        String key = "sms.eton.mturl";
        System.out.println(MsgConfig.getProperty(key));
        Map map = systemConfigService.getConfigMap("MSG", "SMS_ETON");
        System.out.println(map.get(key));
    }

}
