package cc.cicadabear.api.web.controller.test;

import cc.cicadabear.profile.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * Created by Jack on 2/23/17.
 */
@RestController("cc.cicadabear.api.web.controller.test.TestController")
@RequestMapping("/test")
@ResponseBody
public class TestController {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    UserService userService;

    @PostConstruct
    public void init() {
        logger.info("TestController", "PostConstruct");
        System.out.println("========================" + str);
    }

    @Value("${restPath}")
    String str;

    @Secured("ROLE_CLIENT")
    @RequestMapping(value = "/test")
    public Object test() {
        System.out.println("=========test");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        return "test";
    }

    @RequestMapping("/test_cache")
    public Object testfindByMobile() {
        String mobile = "18682323715";

        userService.isExistedUsername(mobile);

        userService.isExistedMobile(mobile);

        return "test_cache";
    }
}
