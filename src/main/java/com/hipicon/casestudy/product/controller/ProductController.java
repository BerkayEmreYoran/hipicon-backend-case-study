package com.hipicon.casestudy.product.controller;

import com.hipicon.casestudy.product.dto.ProductFilterDTO;
import com.hipicon.casestudy.product.enums.ProductStatus;
import com.hipicon.casestudy.product.dto.ProductCreateDTO;
import com.hipicon.casestudy.product.service.ProductService;
import com.hipicon.casestudy.product.dto.ProductDTO;
import com.hipicon.casestudy.product.dto.ProductUpdateDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import com.hipicon.casestudy.base.BaseControllerV1;
import com.hipicon.casestudy.base.Response;


@RestController
@RequestMapping("/api/products")
public class ProductController extends BaseControllerV1 {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public Response<ProductDTO> create(@Valid @RequestBody ProductCreateDTO dto) {
        ProductDTO responseDTO = productService.create(dto);
        return ok(responseDTO);
    }

    @PutMapping
    public Response<ProductDTO> update(@Valid @RequestBody ProductUpdateDTO dto) {
        ProductDTO responseDTO = productService.update(dto);
        return ok(responseDTO);
    }

    @GetMapping
    public Response<Page<ProductDTO>> list(@ModelAttribute ProductFilterDTO dto) {
        Page<ProductDTO> responseDTO = productService.list(dto);
        return ok(responseDTO);
    }

    @PutMapping("/{id}/approve")
    public Response<ProductDTO> approve(@PathVariable Long id) {
        ProductDTO responseDTO = productService.updateStatus(id, ProductStatus.ACTIVE);
        return ok(responseDTO);
    }

    @PutMapping("/{id}/deactivate")
    public Response<ProductDTO> deactivate(@PathVariable Long id) {
        ProductDTO responseDTO = productService.updateStatus(id, ProductStatus.DEACTIVE);
        return ok(responseDTO);
    }

    @PutMapping("/{id}/status")
    public Response<ProductDTO> changeStatus(
            @PathVariable Long id,
            @RequestParam ProductStatus status
    ) {
        ProductDTO responseDTO = productService.updateStatus(id, status);
        return ok(responseDTO);
    }
}
