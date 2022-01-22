package com.paj.psd2.aggregator.web;

import com.paj.psd2.aggregator.service.BankAuthorizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AddBankAccountController {

    private final Logger logger = LoggerFactory.getLogger(AddBankAccountController.class);

    private final BankAuthorizationService bankAuthorizationService;

    public AddBankAccountController(BankAuthorizationService bankAuthorizationService) {
        this.bankAuthorizationService = bankAuthorizationService;
    }

    @RequestMapping("/oauth/initiate-authorization")
    public String initiateAuthorization() {
        logger.debug("Initiate authorization flow!");
        return "redirect:" + bankAuthorizationService.getBankAuthorizationUrl().orElse("/error");
    }

    @RequestMapping("/outh/redirect/url")
    public String redirectBack(@RequestParam String code, @RequestParam(required = false) String state){
        logger.debug("Receive redirect from bank with code: {}", code);
        bankAuthorizationService.getCustomerAccessToken(code, state);
        return "success";
    }
}
