package com.ttn.ecommerce.repositories;

import com.ttn.ecommerce.domain.Seller;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SellerRepository extends CrudRepository<Seller,Integer> {

    Page<Seller> findAll(Pageable page);

    Optional<Seller> findByUsername(String username);
}
