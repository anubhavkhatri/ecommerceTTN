package com.ttn.ecommerce.controller;

import com.ttn.ecommerce.domain.Seller;
import com.ttn.ecommerce.model.AddressModel;
import com.ttn.ecommerce.model.SellerUpdateModel;
import com.ttn.ecommerce.service.SellerDaoService;
import com.ttn.ecommerce.service.UserDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
public class SellerController {

    @Autowired
    private SellerDaoService sellerDaoService;

    @Autowired
    private UserDaoService userDaoService;

    /**
     * API to view my profile
     */
    @GetMapping("/seller/profile")
    public MappingJacksonValue getProfileDetails() {
        Seller seller = userDaoService.getLoggedInSeller();
        String username = seller.getUsername();

        return sellerDaoService.getUserProfile(username);
    }

    /**
     * API to update my profile
     */
    @PatchMapping("/updateSellerProfile")
    public String updateSellerDetails(@RequestBody SellerUpdateModel sellerUpdateModel, HttpServletResponse response) {
        Seller seller = userDaoService.getLoggedInSeller();
        Integer id = seller.getUser_id();

        String message = sellerDaoService.updateSeller(sellerUpdateModel, id);
        if (!message.equals("Profile updated successfully")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "Error";
        } else
            return message;
    }

    /**
     *  API to update an address
     */
    @PutMapping("/updateSellerAddress/{address_id}")
    public String updateCustomerAddress(@RequestBody AddressModel addressModel, @PathVariable Integer address_id, HttpServletResponse response) {
        Seller seller = userDaoService.getLoggedInSeller();

        String message = sellerDaoService.updateAddress(addressModel, address_id);
        if (!message.equals("Address updated")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return message;
    }
}