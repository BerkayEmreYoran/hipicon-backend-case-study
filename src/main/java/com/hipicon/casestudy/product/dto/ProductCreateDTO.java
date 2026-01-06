package com.hipicon.casestudy.product.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductCreateDTO {

    @NotBlank(message = "Ürün adı boş olamaz")
    @Size(max = 100, message = "Ürün adı 100 karakterden uzun olamaz")
    private String name;

    @NotBlank(message = "Satıcı adı boş olamaz")
    @Size(max = 50, message = "Satıcı adı 50 karakterden uzun olamaz")
    private String sellerName;

    @NotNull(message = "Fiyat alanı boş olamaz")
    @PositiveOrZero(message = "Fiyat negatif olamaz")
    private BigDecimal price;

    @NotBlank(message = "Açıklama boş olamaz")
    @Size(max = 1000, message = "Açıklama 1000 karakterden uzun olamaz")
    private String description;

    @NotNull(message = "Fotoğraf listesi boş olamaz")
    @Size(min = 1, message = "En az bir fotoğraf eklemelisiniz")
    private List<String> photoUrls;

}