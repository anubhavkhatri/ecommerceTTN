package com.ttn.ecommerce.service;

import com.ttn.ecommerce.domain.Category;
import com.ttn.ecommerce.domain.CategoryMetadataField;
import com.ttn.ecommerce.domain.CategoryMetadataFieldValues;
import com.ttn.ecommerce.exception.BadRequestException;
import com.ttn.ecommerce.exception.ResourceNotFoundException;
import com.ttn.ecommerce.model.CategoryMetadataFieldModel;
import com.ttn.ecommerce.model.MetadataFieldValueInsertModel;
import com.ttn.ecommerce.repositories.CategoryMetadataFieldRepository;
import com.ttn.ecommerce.repositories.CategoryMetadataFieldValuesRepository;
import com.ttn.ecommerce.repositories.CategoryRepository;
import com.ttn.ecommerce.utils.StringToSetParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryMetadataFieldService {

    @Autowired
    CategoryMetadataFieldRepository categoryMetadataFieldRepository;

    @Autowired
    CategoryMetadataFieldValuesRepository categoryMetadataFieldValuesRepository;

    @Autowired
    CategoryRepository categoryRepository;


    public String addNewMetadataField(String fieldName){
        CategoryMetadataField categoryMetadataField = categoryMetadataFieldRepository.findByName(fieldName);
        if (categoryMetadataField!=null){
            throw new BadRequestException("Metadata field already exists", HttpStatus.BAD_REQUEST);
        }
        else{
            CategoryMetadataField categoryMetadataField1= new CategoryMetadataField();
            categoryMetadataField1.setName(fieldName);
            categoryMetadataFieldRepository.save(categoryMetadataField1);
            return "Category metadata field created";
        }
    }

    public List<CategoryMetadataField> findAllMetadataFields(){
        return categoryMetadataFieldRepository.findAll();
    }

    public String addNewMetadataFieldValues(MetadataFieldValueInsertModel fieldValueDtos, Long categoryId, Long metaFieldId){

        Optional<Category> category= categoryRepository.findById(categoryId);
        Optional<CategoryMetadataField> categoryMetadataField= categoryMetadataFieldRepository.findById(metaFieldId);
        if (!category.isPresent())
            throw new ResourceNotFoundException("Category does not exists");
        else if (!categoryMetadataField.isPresent())
            throw new ResourceNotFoundException("Metadata field does not exists");
        else{
            Category category1= new Category();
            category1= category.get();

            CategoryMetadataField categoryMetadataField1= new CategoryMetadataField();
            categoryMetadataField1= categoryMetadataField.get();

            CategoryMetadataFieldValues categoryFieldValues = new CategoryMetadataFieldValues();

            for(CategoryMetadataFieldModel fieldValuePair : fieldValueDtos.getFieldValues()){

                String values = StringToSetParser.toCommaSeparatedString(fieldValuePair.getValues());

                categoryFieldValues.setValue(values);
                categoryFieldValues.setCategory(category1);
                categoryFieldValues.setCategoryMetadataField(categoryMetadataField1);

                categoryMetadataFieldValuesRepository.save(categoryFieldValues);
            }
            return "Metadata field values added successfully";
        }

    }

}
