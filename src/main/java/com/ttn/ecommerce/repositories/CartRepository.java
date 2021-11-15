package com.ttn.ecommerce.repositories;


import com.ttn.ecommerce.domain.Cart;
import org.springframework.data.repository.CrudRepository;

public interface CartRepository extends CrudRepository<Cart, Integer> {
}
