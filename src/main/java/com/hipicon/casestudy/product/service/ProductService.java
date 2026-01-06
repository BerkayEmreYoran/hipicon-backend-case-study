package com.hipicon.casestudy.product.service;

import com.beyt.jdq.core.model.Criteria;
import com.beyt.jdq.core.model.CriteriaList;
import com.beyt.jdq.core.model.DynamicQuery;
import com.beyt.jdq.core.model.enums.CriteriaOperator;
import com.hipicon.casestudy.exception.AppException;
import com.hipicon.casestudy.product.dto.ProductFilterDTO;
import com.hipicon.casestudy.product.repository.ProductRepository;
import com.hipicon.casestudy.product.enums.ProductStatus;
import com.hipicon.casestudy.product.dto.ProductCreateDTO;
import com.hipicon.casestudy.product.dto.ProductDTO;
import com.hipicon.casestudy.product.dto.ProductUpdateDTO;
import com.hipicon.casestudy.product.entity.Product;
import com.hipicon.casestudy.security.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @CacheEvict(value = "products", allEntries = true)
    public ProductDTO create(ProductCreateDTO dto) {

        Product product = new Product();
        product.setName(dto.getName());
        product.setSellerName(dto.getSellerName());
        product.setPrice(dto.getPrice());
        product.setDescription(dto.getDescription());
        product.setStatus(ProductStatus.PENDING);
        product.setPhotoUrls(dto.getPhotoUrls());
        product.setCreatedBy(SecurityUtil.getCurrentUserName());
        product.setCreatedAt(LocalDateTime.now());

        productRepository.save(product);

        log.info(
                "Product created successfully. id={}, name={}, seller={}, status={}",
                product.getId(),
                product.getName(),
                product.getSellerName(),
                product.getStatus()
        );

        return productToDTO(product);
    }

    @CacheEvict(value = "products", allEntries = true)
    public ProductDTO update(ProductUpdateDTO dto) {

        Product product = productRepository.findById(dto.getId())
                .orElseThrow(() -> new AppException("Ürün bulunamadı: " + dto.getId()));


        product.setName(dto.getName());
        product.setSellerName(dto.getSellerName());
        product.setPrice(dto.getPrice());
        product.setDescription(dto.getDescription());
        product.setStatus(ProductStatus.PENDING);
        product.setPhotoUrls(dto.getPhotoUrls());
        product.setCreatedBy(SecurityUtil.getCurrentUserName());
        product.setCreatedAt(LocalDateTime.now());

        productRepository.save(product);

        log.info(
                "Product updated. id={}, name={}, seller={}, status={}",
                product.getId(),
                product.getName(),
                product.getSellerName(),
                product.getStatus()
        );

        return productToDTO(product);
    }

    @Cacheable(value = "products", key = "#dto.status + '-' + #dto.sellerName + '-' + #dto.minPrice + '-' + #dto.maxPrice + '-' + #dto.pageNumber + '-' + #dto.pageSize")
    public Page<ProductDTO> list(ProductFilterDTO dto) {
        DynamicQuery dynamicQuery = new DynamicQuery();

        CriteriaList criteriaList = CriteriaList.of();

        if (dto.getStatus() != null) {
            criteriaList.add(Criteria.of("status", CriteriaOperator.EQUAL, dto.getStatus()));
        }
        if (dto.getSellerName() != null) {
            criteriaList.add(Criteria.of("sellerName", CriteriaOperator.CONTAIN, dto.getSellerName()));
        }
        if (dto.getMinPrice() != null) {
            criteriaList.add(Criteria.of("price", CriteriaOperator.GREATER_THAN_OR_EQUAL, dto.getMinPrice()));
        }
        if (dto.getMaxPrice() != null) {
            criteriaList.add(Criteria.of("price", CriteriaOperator.LESS_THAN_OR_EQUAL, dto.getMaxPrice()));
        }

        dynamicQuery.setWhere(criteriaList);
        dynamicQuery.setPageNumber(dto.getPageNumber());
        dynamicQuery.setPageSize(dto.getPageSize());

        Page<Product> page =
                productRepository.findAllAsPage(dynamicQuery, Product.class);
        return page.map(this::productToDTO);
    }


    @CacheEvict(value = "products", allEntries = true)
    public ProductDTO updateStatus(Long id, ProductStatus status) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new AppException("Ürün bulunamadı: " + id));

        product.setStatus(status);
        product.setUpdatedAt(LocalDateTime.now());

        productRepository.save(product);

        log.info(
                "Product status changed. id={}, name={}, status={}",
                product.getId(),
                product.getName(),
                status
        );

        return productToDTO(product);
    }

    private ProductDTO productToDTO(Product product) {
        ProductDTO res = new ProductDTO();
        res.setId(product.getId());
        res.setName(product.getName());
        res.setSellerName(product.getSellerName());
        res.setPrice(product.getPrice());
        res.setDescription(product.getDescription());
        res.setStatus(product.getStatus());
        res.setPhotoUrls(product.getPhotoUrls());
        return res;
    }
}
