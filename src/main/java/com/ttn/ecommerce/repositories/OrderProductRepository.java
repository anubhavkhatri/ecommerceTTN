package com.ttn.ecommerce.repositories;

import com.ttn.ecommerce.domain.OrderProduct;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderProductRepository extends CrudRepository<OrderProduct, Integer> {
}
