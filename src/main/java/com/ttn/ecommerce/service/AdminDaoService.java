package com.ttn.ecommerce.service;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.ttn.ecommerce.domain.Customer;
import com.ttn.ecommerce.domain.Seller;
import com.ttn.ecommerce.domain.User;
import com.ttn.ecommerce.exception.BadRequestException;
import com.ttn.ecommerce.exception.UserNotFoundException;
import com.ttn.ecommerce.exception.notfound.NotFoundRequestException;
import com.ttn.ecommerce.repositories.CustomerRepository;
import com.ttn.ecommerce.repositories.SellerRepository;
import com.ttn.ecommerce.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AdminDaoService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SellerRepository sellerRepository;

    public MappingJacksonValue listAllCustomer(String page, String size) {
        List<Customer> customers = customerRepository.findAll(PageRequest.of(Integer.parseInt(page), Integer.parseInt(size))).getContent();

        SimpleBeanPropertyFilter filter1 = SimpleBeanPropertyFilter.filterOutAllExcept("user_id", "firstName", "middleName", "lastName", "email", "is_active");

        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("userFilter", filter1);

        MappingJacksonValue mapping = new MappingJacksonValue(customers);
        mapping.setFilters(filterProvider);
        return mapping;
    }

    public MappingJacksonValue listAllSeller(String page, String size) {
        List<Seller> sellers = sellerRepository.findAll(PageRequest.of(Integer.parseInt(page), Integer.parseInt(size))).getContent();
        SimpleBeanPropertyFilter filter2 = SimpleBeanPropertyFilter.filterOutAllExcept("user_id", "firstName", "middleName", "lastName", "email", "is_active", "company_name","addresses", "company_contact");

        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("userFilter", filter2);

        MappingJacksonValue mapping = new MappingJacksonValue(sellers);
        mapping.setFilters(filterProvider);

        return mapping;
    }

    @Transactional
    public String activateUser(Integer uid) {

        Optional<User> user1 = userRepository.findById(uid);
        if (user1.isPresent()) {
            User user = user1.get();

            if (!user.getIs_active()) {
                user.setIs_active(true);
                userRepository.save(user);
                SimpleMailMessage mailMessage = new SimpleMailMessage();
                mailMessage.setTo(user.getEmail());
                mailMessage.setSubject("Account Activated!!");
                mailMessage.setText("Your account is successfully activated.");
                emailSenderService.sendEmail(mailMessage);

                return "User Activated";
            } else {
                return "User is already activated";
            }

        } else {
            throw new NotFoundRequestException( "Incorrect User ID", HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    public String deactivateUser(Integer uid) {
        Optional<User> user1 = userRepository.findById(uid);
        if (user1.isPresent()) {

            User user = user1.get();
            if (user.getIs_active()) {
                user.setIs_active(false);
                userRepository.save(user);
                SimpleMailMessage mailMessage = new SimpleMailMessage();
                mailMessage.setTo(user.getEmail());
                mailMessage.setSubject("Account Deactivated!!");
                mailMessage.setText("Your account has been deactivated.");
                emailSenderService.sendEmail(mailMessage);
                return "User Deactivated";
            } else {
                return "User is already deactivated";
            }

        } else {
            throw new NotFoundRequestException("Incorrect User ID", HttpStatus.NOT_FOUND);
        }
    }
}
