/*
 * Copyright 2002-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.paj.psd2.aggregator.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paj.psd2.aggregator.client.AccountsApiClientWrapper;
import com.paj.psd2.aggregator.payload.JwtAuthenticationResponse;
import com.paj.psd2.aggregator.service.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Joe Grandja
 */
@Controller
public class MainController {

	@Autowired
	private AuthenticationService authenticationService;

	private final Logger logger = LoggerFactory.getLogger(MainController.class);

	@RequestMapping("/")
	public String root() {
		return "redirect:/home";
	}

	@RequestMapping("/code")
	public String authenticationCode(Model model, @RequestParam(name = "username") String username) {
		model.addAttribute("username", username);
		return "authenticationCode";
	}

	@RequestMapping("/index")
	public String index() {
		return "index";
	}



	@RequestMapping("/user/index")
	public String userIndex() {
		return "user/index";
	}

	@RequestMapping("/login")
	public String login() {
		return "login";
	}

	@RequestMapping("/accounts-page")
	public String accounts() {
		return "accounts";
	}

	@RequestMapping("/register")
	public String register() {
		return "register";
	}

	@RequestMapping("/add-bank-account-page")
	public String addBankAccount() {
		return "addNewAccount";
	}

	@RequestMapping("/login-error")
	public String loginError(Model model) {
		model.addAttribute("loginError", true);
		return "login";
	}

}
