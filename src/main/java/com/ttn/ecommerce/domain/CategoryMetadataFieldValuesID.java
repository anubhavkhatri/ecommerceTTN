package com.ttn.ecommerce.domain;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class CategoryMetadataFieldValuesID implements Serializable {
    private Long categoryId;
    private Long categoryMetadataFieldId;

}
