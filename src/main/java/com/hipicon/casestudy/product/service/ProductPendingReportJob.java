package com.hipicon.casestudy.product.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ProductPendingReportJob {

    private final ProductReportService productReportService;

    public ProductPendingReportJob(ProductReportService productReportService) {
        this.productReportService = productReportService;
    }

    @Scheduled(fixedRate = 5 * 60 * 1000)
    public void run() {
        productReportService.logPendingProductsByDesigner();
    }
}
