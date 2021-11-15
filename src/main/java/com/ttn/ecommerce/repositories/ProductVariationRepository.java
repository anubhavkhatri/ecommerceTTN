package com.ttn.ecommerce.repositories;

import com.ttn.ecommerce.domain.ProductVariation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductVariationRepository extends CrudRepository<ProductVariation, Integer> {

}
