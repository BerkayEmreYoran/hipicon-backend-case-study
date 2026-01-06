package com.hipicon.casestudy.product.dto;
import com.hipicon.casestudy.product.enums.ProductStatus;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductFilterDTO {

    private ProductStatus status;
    private String sellerName;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private Integer pageNumber = 0;
    private Integer pageSize = 10;

}
