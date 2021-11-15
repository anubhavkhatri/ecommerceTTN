package com.ttn.ecommerce.controller;

import com.ttn.ecommerce.domain.Cart;
import com.ttn.ecommerce.domain.Customer;
import com.ttn.ecommerce.service.CartDaoService;
import com.ttn.ecommerce.service.UserDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartController {

    @Autowired
    private CartDaoService cartDaoService;

    @Autowired
    private UserDaoService userDaoService;

    @PostMapping("add-to-cart/{productVariation_id}")
    public String addToCart(@RequestBody Cart cart, @PathVariable Integer productVariation_id){

        Customer customer = userDaoService.getLoggedInCustomer();
        Integer customer_user_id = customer.getUser_id();

        Cart cart1= cartDaoService.addToCart(customer_user_id, cart, productVariation_id);
        return "Added Product";
    }

}
