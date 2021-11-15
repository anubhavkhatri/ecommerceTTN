package com.ttn.ecommerce.repositories;

import com.ttn.ecommerce.domain.ProductReview;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductReviewRepository extends CrudRepository<ProductReview, Integer> {
}
