package com.ttn.ecommerce.repositories;

import com.ttn.ecommerce.domain.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {

    Optional<Category> findByName(String name);

    @Query( value = "select name from category where name=:name", nativeQuery = true)
    String findByCatName(@Param("name") String name);
}
