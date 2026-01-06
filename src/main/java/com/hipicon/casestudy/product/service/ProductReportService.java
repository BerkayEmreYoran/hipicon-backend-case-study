package com.hipicon.casestudy.product.service;

import com.hipicon.casestudy.product.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProductReportService {

    private final ProductRepository productRepository;

    public ProductReportService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void logPendingProductsByDesigner() {
        productRepository
                .countPendingProductsGroupedByDesigner()
                .forEach(item ->
                        log.info(
                                "Designer: {} -> Pending Product Count: {}",
                                item.getDesigner(),
                                item.getCount()
                        )
                );
    }
}
