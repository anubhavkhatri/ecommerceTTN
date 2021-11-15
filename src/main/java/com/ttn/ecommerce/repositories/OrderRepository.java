package com.ttn.ecommerce.repositories;

import com.ttn.ecommerce.domain.Orders;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Orders, Integer> {
}
