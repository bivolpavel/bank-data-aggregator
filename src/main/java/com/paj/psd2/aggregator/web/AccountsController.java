package com.paj.psd2.aggregator.web;

import com.paj.psd2.aggregator.payload.AllAccountsResponse;
import com.paj.psd2.aggregator.service.AccountsService;
import com.paj.psd2.aggregator.service.ProductSummaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class AccountsController {

    private final Logger logger = LoggerFactory.getLogger(AccountsController.class);

    private final String ACCOUNTS_PAGE = "accounts";
    private final String ERROR_PAGE = "error";

    private final AccountsService accountsService;

    private final ProductSummaryService productSummaryService;

    public AccountsController(AccountsService accountsService, ProductSummaryService productSummaryService) {
        this.accountsService = accountsService;
        this.productSummaryService = productSummaryService;
    }

    @RequestMapping("/accounts")
    public String addBankAccount(Model model) {

        Optional<AllAccountsResponse> accountsResponseOptional = accountsService.getAllAccountBalances();

        if (accountsResponseOptional.isPresent()) {

            AllAccountsResponse allAccountsResponse = accountsResponseOptional.get();
            model.addAttribute("accountBalanceResponseList", allAccountsResponse.getAccountBalanceResponses());
            model.addAttribute("amountByCurrencies", allAccountsResponse.getAmountByCurrencies());

            logger.debug("Put on model accountBalanceResponseList with size: {}, and amountByCurrencies with keySet size : {}",
                    allAccountsResponse.getAccountBalanceResponses().size(), allAccountsResponse.getAmountByCurrencies().size());




            return ACCOUNTS_PAGE;
        } else {
            return ERROR_PAGE;
        }
    }
}