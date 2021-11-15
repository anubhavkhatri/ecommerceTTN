package com.ttn.ecommerce.controller;

import com.ttn.ecommerce.domain.Customer;
import com.ttn.ecommerce.domain.Seller;
import com.ttn.ecommerce.model.CustomerRegisterModel;
import com.ttn.ecommerce.model.SellerRegisterModel;
import com.ttn.ecommerce.repositories.ConfirmationTokenRepository;
import com.ttn.ecommerce.repositories.UserRepository;
import com.ttn.ecommerce.service.UserDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.oauth2.provider.token.TokenStore;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@RestController
public class UserController {

    @Autowired
    private UserDaoService userDaoService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private MessageSource messageSource;

    /**
     * API to register a customer
     */
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(path = "/customer-registration")       //http://localhost:8080/customer-registration
    public String createCustomer(@Valid @RequestBody CustomerRegisterModel customerRegisterModel) {
        return userDaoService.saveNewCustomer(customerRegisterModel);
    }

    /**
     * API to activate the customer
     */
    @GetMapping("/confirm-account")         //http://localhost:8080/confirm-account
    public String confirmUserAccount(@RequestParam("token") String confirmationToken) {
        return userDaoService.confirmUserAccount(confirmationToken);
    }

    /**
     * API to re-send activation link
     */
    @PostMapping(path = "/resendActivationToken", consumes = "application/json")    //http://localhost:8080/resendActivationToken
    public String resendActivationToken(@RequestBody String email) {
        return userDaoService.resendActivationToken(email);
    }

    /**
     * API to register a seller
     */
    @PostMapping(path = "/seller-registration")         //http://localhost:8080/seller-registration
    public String createSeller(@Valid @RequestBody SellerRegisterModel sellerRegisterModel) {
        return userDaoService.saveNewSeller(sellerRegisterModel);
    }

    /**
     * API to login into the system     --http://localhost:8080/oauth/token--
     */

    /**
     * API to logout of the system
     */
    @GetMapping("/doLogout")        //http://localhost:8080/doLogout
    public String logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            String tokenValue = authHeader.replace("Bearer", "").trim();
            OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
            tokenStore.removeAccessToken(accessToken);
        }
        return "Logged out successfully";
    }

    @GetMapping("/")                //http://localhost:8080/
    public String index() {
        return "index";
    }

    @GetMapping("/admin/home")      //http://localhost:8080/admin/home
    public String adminHome() {
        return "Admin home";
    }

    @GetMapping("/customer/home")   //http://localhost:8080/customer/home
    public String userCustomer() {
        Customer customer = userDaoService.getLoggedInCustomer();
        String name = customer.getFirstName();
        return messageSource.getMessage("welcome.message", new Object[]{name}, LocaleContextHolder.getLocale());
    }

    @GetMapping("/seller/home")     //http://localhost:8080/seller/home
    public String sellerHome() {
        Seller seller = userDaoService.getLoggedInSeller();
        String name = seller.getFirstName();
        return messageSource.getMessage("welcome.message", new Object[]{name}, LocaleContextHolder.getLocale());
    }



    @PostMapping(path = "/enableSellerAccount/{sellerId}") //http://localhost:8080/enableSellerAccount/
    public String enableSellerAccount(@PathVariable Integer sellerId) {
        return userDaoService.enableSellerAccount(sellerId);
    }


}