package com.hipicon.casestudy.product.entity;

import com.hipicon.casestudy.product.enums.ProductStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String sellerName;

    @Column(nullable = false)
    private BigDecimal price;

    @ElementCollection
    @CollectionTable(
            name = "product_photos",
            joinColumns = @JoinColumn(name = "product_id")
    )

    @Column(name = "photo_url")
    private List<String> photoUrls;

    @Column(length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    private String createdBy;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}