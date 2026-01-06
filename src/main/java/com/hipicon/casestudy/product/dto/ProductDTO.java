package com.hipicon.casestudy.product.dto;

import com.hipicon.casestudy.product.enums.ProductStatus;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductDTO {

    private Long id;
    private String name;
    private String sellerName;
    private BigDecimal price;
    private String description;
    private ProductStatus status;
    private List<String> photoUrls;

}
