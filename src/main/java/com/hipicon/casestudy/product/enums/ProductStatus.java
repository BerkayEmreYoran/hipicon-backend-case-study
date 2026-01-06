package com.hipicon.casestudy.product.enums;

public enum ProductStatus {
    PENDING("Onay Bekliyor"),
    ACTIVE("Aktif"),
    DEACTIVE("Deaktif");

    private final String label;

    ProductStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
