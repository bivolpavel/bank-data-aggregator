package com.paj.psd2.aggregator.web;

import com.paj.psd2.aggregator.service.TransactionsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TransactionsController {

    private final Logger logger = LoggerFactory.getLogger(TransactionsController.class);

    private final TransactionsService transactionsService;

    public TransactionsController(TransactionsService transactionsService) {
        this.transactionsService = transactionsService;
    }

    @RequestMapping("/transactions")
    public String transactions(Model model) {
        transactionsService.getTransactionsInfo()
                .ifPresent(allAccountsTransactions ->
                        model.addAttribute("allAccountsTransactions", allAccountsTransactions));

        return "transactions";
    }
}
