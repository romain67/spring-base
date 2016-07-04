package com.roms.library.security.rest;

import com.roms.module.user.domain.model.User;
import com.roms.module.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("restAuthenticationSuccessHandler")
public class RestAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication)
            throws ServletException, IOException {

        com.roms.library.security.userdetails.User userDetails;
        userDetails = (com.roms.library.security.userdetails.User) authentication.getPrincipal();
        this.updateUserLastLogin(userDetails);

        response.setContentType("application/json");
        response.setStatus(HttpStatus.OK.value());
        String json = "{\"message\":\"" + HttpStatus.OK.getReasonPhrase() + "\"}";

        response.getOutputStream().println(json);
    }

    private void updateUserLastLogin(com.roms.library.security.userdetails.User userDetails) {
        User user = userDetails.getUserEntity();
        userService.updateUserLastLogin(user.getId());
    }

}