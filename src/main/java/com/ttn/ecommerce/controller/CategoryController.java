package com.ttn.ecommerce.controller;

import com.ttn.ecommerce.domain.Category;
import com.ttn.ecommerce.domain.CategoryMetadataField;
import com.ttn.ecommerce.model.CategoryModel;
import com.ttn.ecommerce.model.MetadataFieldValueInsertModel;
import com.ttn.ecommerce.service.CategoryDaoService;
import com.ttn.ecommerce.service.CategoryMetadataFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    private CategoryDaoService categoryDaoService;

    @Autowired
    private CategoryMetadataFieldService categoryMetadataFieldService;


    /**
     * API add a category
     */
    @PostMapping("/save-category")
    public String saveCategory(@Valid @RequestBody CategoryModel categoryModel){
        return categoryDaoService.saveNewCategory(categoryModel);
    }

    /**
     *  API add a category
     */
    @PostMapping("/save-category/{parentCategory}")
    public String saveSubCategory(@Valid @PathVariable String parentCategory, @RequestBody CategoryModel subCategories){
        return categoryDaoService.saveNewSubCategory(parentCategory, subCategories);
    }

    /**
     * API to view all categories
     */
    @GetMapping("/find-all-categories")
    public List<Category> findAllCategories(){
        return categoryDaoService.findAllCategory();
    }

    /**
     * API to view a category
     */
    @GetMapping("/find-category/{category_id}")
    public Category findCategory(@PathVariable Long category_id){
        return categoryDaoService.findCategory(category_id);
    }

    /**
     * API to update a category
     */
    @PutMapping ("/updateCategory/{category}")
    public String updateCategory(@RequestBody CategoryModel categoryModel, @PathVariable String category){
        return categoryDaoService.updateCategory(categoryModel, category);
    }

    /**
     * API add a Metadata field
     */
    @PostMapping("/metadata-fields/add")
    public String addMetaDataField(@RequestParam String fieldName) {
        return categoryMetadataFieldService.addNewMetadataField(fieldName);
    }

    /**
     * API to view all Metadata fields
     */
    @GetMapping("/find-all-metadata-fields")
    public List<CategoryMetadataField> findAllMetadataFields(){
        return categoryMetadataFieldService.findAllMetadataFields();
    }

    /**
     *  API to add new category metadata field for a category
     */
    @PostMapping("/metadata-fields/addValues/{categoryId}/{metaFieldId}")
    public String addMetaDataFieldValues(@RequestBody MetadataFieldValueInsertModel fieldValueDtos, @PathVariable Long categoryId, @PathVariable Long metaFieldId) {
        return categoryMetadataFieldService.addNewMetadataFieldValues(fieldValueDtos, categoryId, metaFieldId);
    }
    /**
     * API to update values for an existing metadata field in a category
     */

}
