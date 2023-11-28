package com.neoflex.product.controller.api;

import com.neoflex.product.dto.CreateProductDto;
import com.neoflex.product.dto.ProductDto;
import com.neoflex.product.dto.UpdateProductDto;
import com.neoflex.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.UUID;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/product")
@Tag(name = "Product Controller",
        description = "Создает, изменяет и удаляет продукт")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "Создание продукта",
            description = "Создает продукт без возможности создать тариф сразу")
    @PostMapping("/create")
    public ProductDto createProduct(@Valid @RequestBody CreateProductDto productDto) {
        log.info("Got the request to create product {}", productDto);
        return productService.create(productDto);
    }

    @Operation(summary = "Обновление продукта",
            description = "Обновляет продукт без возможности обновления тарифа")
    @PutMapping("/update/{productId}")
    public ProductDto updateProduct(
            @Positive @PathVariable @Parameter(description = "Идентификатор продукта",
            example = "123e4567-e89b-42d3-a456-556642440000", required = true) UUID productId,
            @Valid @RequestBody UpdateProductDto updateProductDto) {
        log.info("Got the request to update product {}", updateProductDto);
        return productService.update(productId, updateProductDto);
    }

    @Operation(summary = "Удаление продукта",
            description = "Удаляет продукт по идентификатору")
    @DeleteMapping("/remove/{productId}")
    public void removeProduct(@Positive @PathVariable @Parameter(description = "Идентификатор продукта",
            example = "123e4567-e89b-42d3-a456-556642440000", required = true) UUID productId) {
        log.info("Got the request to delete product with id {}", productId);
        productService.remove(productId);
    }
}
