package com.ttn.ecommerce.controller;

import com.ttn.ecommerce.domain.Seller;
import com.ttn.ecommerce.model.ProductModel;
import com.ttn.ecommerce.model.ProductUpdateModel;
import com.ttn.ecommerce.model.ProductVariationModel;
import com.ttn.ecommerce.service.ProductDaoService;
import com.ttn.ecommerce.service.UserDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
public class ProductController {

    @Autowired
    private ProductDaoService productDaoService;

    @Autowired
    private UserDaoService userDaoService;

    /**
     * API add a product
     */
    @PostMapping("/save-product/{category_name}")
    public String saveProduct(@Valid @RequestBody Set<ProductModel> productModels, @PathVariable String category_name){

        Seller seller = userDaoService.getLoggedInSeller();

        return productDaoService.saveNewProduct(productModels, category_name, seller);
    }

    /**
     * API add a product variation
     */
    @PostMapping("/save-productVariation/{product_id}")
    public String saveProductVariation(@Valid @RequestBody ProductVariationModel productVariationModel, @PathVariable Long product_id){

        Seller seller = userDaoService.getLoggedInSeller();

        return productDaoService.saveNewProductVariation(productVariationModel, product_id, seller);
    }

    @PatchMapping("/admin/activateProduct/{pid}")
    public String productActivation(@PathVariable Long pid) {
        return productDaoService.activateProduct(pid);
    }

    @PatchMapping("/admin/deactivateProduct/{pid}")
    public String productDeactivation(@PathVariable Long pid) {
        return productDaoService.deactivateProduct(pid);
    }


    @PatchMapping("/seller/activateProductVariation/{productVariationId}")
    public String productVariationActivation(@PathVariable Integer productVariationId) {
        return productDaoService.activateProductVariation(productVariationId);
    }

    @PatchMapping("/seller/deactivateProductVariation/{productVariationId}")
    public String productVariationDeactivation(@PathVariable Integer productVariationId) {
        return productDaoService.deactivateProductVariation(productVariationId);
    }

    /**
     * API to view all products
     */
    @GetMapping("/find-all-products/{category_name}")
    public MappingJacksonValue retrieveProductList(@PathVariable String category_name) {
        return productDaoService.retrieveProductList(category_name);
    }

    /**
     * API to view a product
     */
    @GetMapping("/product/{product_id}")
    public MappingJacksonValue retrieveProduct(@PathVariable Long product_id) {
        return productDaoService.retrieveProduct(product_id);
    }

    @PutMapping("/seller/updateProduct/{pid}")
    public ResponseEntity<Object> updateProductDetails(@RequestBody ProductUpdateModel productUpdateModel, @PathVariable Long pid){
        Seller seller = userDaoService.getLoggedInSeller();
        Integer sellerid = seller.getUser_id();
        String message = productDaoService.updateProduct(productUpdateModel, pid, sellerid);

        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    /**
     *  API to delete a product
     */
    @DeleteMapping("/seller/deleteProduct/{pid}")
    public ResponseEntity<Object> deleteProductVariation(@PathVariable Integer pid) {

        Seller seller = userDaoService.getLoggedInSeller();
        Integer sellerid = seller.getUser_id();

        String message = productDaoService.deleteProductVariation(pid, sellerid);

        return new ResponseEntity<>(message, HttpStatus.ACCEPTED);
    }

    /**
     * API to view a product
     */
    @GetMapping("/seller/products")
    public MappingJacksonValue retrieveSellerProducts() {
        Seller seller = userDaoService.getLoggedInSeller();
        return productDaoService.retrieveSellerProducts(seller);
    }
    /**
     * API to view a product variation
     */

}
