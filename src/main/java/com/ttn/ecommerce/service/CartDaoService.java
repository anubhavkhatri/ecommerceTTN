package com.ttn.ecommerce.service;

import com.ttn.ecommerce.domain.Cart;
import com.ttn.ecommerce.domain.Customer;
import com.ttn.ecommerce.domain.ProductVariation;
import com.ttn.ecommerce.domain.User;
import com.ttn.ecommerce.exception.ResourceNotFoundException;
import com.ttn.ecommerce.exception.UserNotFoundException;
import com.ttn.ecommerce.repositories.CartRepository;
import com.ttn.ecommerce.repositories.ProductVariationRepository;
import com.ttn.ecommerce.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartDaoService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductVariationRepository productVariationRepository;

    public Cart addToCart(Integer customer_user_id, Cart cart, Integer productVariation_id){

        Optional<User> customer = userRepository.findById(customer_user_id);
        if (customer.isPresent()) {
            User user = new User();
            user = customer.get();

            Customer customer1 = new Customer();
            customer1 = (Customer) user;

            cart.setCustomer(customer1);

            Optional<ProductVariation> productVariation = productVariationRepository.findById(productVariation_id);
            if (productVariation.isPresent()) {
                ProductVariation productVariation1 = new ProductVariation();
                productVariation1 = productVariation.get();

                Integer qty = cart.getQuantity();
                if(qty<productVariation1.getQuantityAvailable())
                {
                    cart.setProductVariation(productVariation1);
                    cartRepository.save(cart);
                    return cart;
                }
                else
                {
                    throw new ResourceNotFoundException("Ordered Quantity is greater than available stock.");
                }
            }
            else {
                throw new ResourceNotFoundException("Invalid Product Variation ID");
            }

        }
        else {
            throw new UserNotFoundException("Invalid customer ID", HttpStatus.NOT_FOUND);
        }
    }

}
