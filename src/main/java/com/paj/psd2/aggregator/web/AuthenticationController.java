package com.paj.psd2.aggregator.web;

import com.paj.psd2.aggregator.payload.JwtAuthenticationResponse;
import com.paj.psd2.aggregator.payload.SignupResponse;
import com.paj.psd2.aggregator.service.AuthenticationService;
import com.paj.psd2.aggregator.utils.CookieUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

@Controller
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    private final CookieUtils cookieUtils;

    public AuthenticationController(AuthenticationService authenticationService, CookieUtils cookieUtils) {
        this.authenticationService = authenticationService;
        this.cookieUtils = cookieUtils;
    }

    @PostMapping("/login")
    public String loginPost(Model model, HttpServletResponse response, @RequestParam(name = "username") String username,
                            @RequestParam(name = "password") String password){
        try {
            //Sa tratez corect exceptiile aici
            JwtAuthenticationResponse jwtAuthenticationResponse = authenticationService.authenticateUser(username, password);

            if (jwtAuthenticationResponse.isMfa()) {
                model.addAttribute("username", username);
                return "authenticationCode";
            } else {
                cookieUtils.addAuthenticationCookie(response, jwtAuthenticationResponse.getAccessToken());
                return "index";
            }

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "login";
        }
    }

    @PostMapping("/verify")
    public String verifyPost(Model model, HttpServletResponse response, @RequestParam(name = "code") String code,
                             @RequestParam(name = "username") String username){

        try {
            //Sa tratez corect exceptiile aici
            JwtAuthenticationResponse jwtAuthenticationResponse = authenticationService.verifyCode(username, code);
            cookieUtils.addAuthenticationCookie(response, jwtAuthenticationResponse.getAccessToken());

            response.sendRedirect("/home");
            return null;

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "login";
        }
    }

    @PostMapping("/users")
    public String createUserPost(Model model, HttpServletResponse response, @RequestParam(name = "fullName") String fullName,
                            @RequestParam(name = "username") String username,
                            @RequestParam(name = "email") String email,
                            @RequestParam(name = "password") String password,
                            @RequestParam(name = "mfa", required = false) boolean mfa){

        try {
            //Sa tratez corect exceptiile aici
            SignupResponse signupResponse = authenticationService.createUser(fullName, username, email, password, mfa);

            if (signupResponse.isMfa()) {
                model.addAttribute("username", username);
                model.addAttribute("qrcode", signupResponse.getSecretImageUri());
                return "QRCode";
            } else {
                JwtAuthenticationResponse jwtAuthenticationResponse = authenticationService.authenticateUser(username, password);
                cookieUtils.addAuthenticationCookie(response, jwtAuthenticationResponse.getAccessToken());
                response.sendRedirect("/home");
                return null;
            }
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    @GetMapping("/logout")
    public String logout(Model model, HttpServletResponse response){

        cookieUtils.deleteAuthenticationCookie(response);
        return "login";
    }
}
