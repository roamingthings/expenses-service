package de.roamingthings.expenses.user.controller;

import de.roamingthings.expenses.user.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Alexander Sparkowsky [info@roamingthings.de]
 * @version 2017/05/03
 */
@RestController
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping({ "/user", "/me" })
    public Map<String, String> user(Principal principal) {
        if (principal instanceof OAuth2Authentication) {
            OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) principal;
            final Authentication userAuthentication = oAuth2Authentication.getUserAuthentication();
            final Object details = userAuthentication.getDetails();
        }

        Map<String, String> map = new LinkedHashMap<>();
        map.put("name", principal.getName());
        return map;
    }
}
