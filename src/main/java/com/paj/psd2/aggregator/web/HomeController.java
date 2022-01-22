package com.paj.psd2.aggregator.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paj.psd2.aggregator.payload.ProductSummaryResponse;
import com.paj.psd2.aggregator.service.ProductSummaryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
public class HomeController {

    private final ProductSummaryService productSummaryService;

    public HomeController(ProductSummaryService productSummaryService) {
        this.productSummaryService = productSummaryService;
    }

    @RequestMapping("/home")
    public String home(Model model) throws JsonProcessingException {

        ProductSummaryResponse productSummaryResponse = productSummaryService.getProductSummary(null);

        System.out.println(new ObjectMapper().writeValueAsString(productSummaryResponse));

        Set<String> months = productSummaryResponse.getMonthsSpending().keySet();
        Collection<Double> amounts = productSummaryResponse.getMonthsSpending().values();

        model.addAttribute("productSummary", productSummaryResponse);
        model.addAttribute("months", new ObjectMapper().writeValueAsString(months));
        model.addAttribute("amounts", new ObjectMapper().writeValueAsString(amounts));

        return "home";
    }
}
