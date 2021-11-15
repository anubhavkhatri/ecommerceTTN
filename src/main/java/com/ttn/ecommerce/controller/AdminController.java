package com.ttn.ecommerce.controller;

import com.ttn.ecommerce.repositories.UserRepository;
import com.ttn.ecommerce.service.AdminDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminDaoService adminDaoService;

    /**
     * API to list all the registered customers
     */
    @GetMapping("list-customers")       //http://localhost:8080/admin/list-customers
    public MappingJacksonValue getCustomers(@RequestParam(defaultValue = "0") String page, @RequestParam(defaultValue = "10")String size){
        return adminDaoService.listAllCustomer(page, size);
    }

    /**
     * API to list all the registered sellers
     * @param page
     * @param size
     * @return
     */
    @GetMapping("list-sellers")         //http://localhost:8080/admin/list-sellers
    public MappingJacksonValue getSellers(@RequestParam(defaultValue = "0")String page, @RequestParam(defaultValue = "10")String size) {
        return adminDaoService.listAllSeller(page, size);
    }

    /**
     * API to activate customer and seller.
     * @param uid
     * @return
     */
    @PatchMapping("activate-user/{uid}")    //http://localhost:8080/admin/activate-user/
    public String userActivation(@PathVariable Integer uid) {
        return adminDaoService.activateUser(uid);
    }

    /**
     * API to deactivate customer and seller
     * @param uid
     * @return
     */
    @PatchMapping("deactivate-user/{uid}")  //http://localhost:8080/admin/deactivate-user/
    public String userDeactivation(@PathVariable Integer uid) {
        return adminDaoService.deactivateUser(uid);
    }

}
