package com.neoflex.product.controller.api;

import com.neoflex.product.dto.ProductDto;
import com.neoflex.product.service.AuditService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/audit")
@Tag(name = "Audit Controller",
        description = "Предоставляет версии продукта")
public class AuditController {

    private final AuditService auditService;

    @Operation(summary = "Получение текущей версии продукта",
            description = "Возвращает текущую версию продукта по его идентификатору")
    @GetMapping("/find/actual/{productId}")
    public ProductDto getActualProductVersion(@Positive @PathVariable @Parameter(description = "Идентификатор продукта",
            example = "123e4567-e89b-42d3-a456-556642440000", required = true) UUID productId) {
        log.info("Got the request for retrieving current version of the product with id {}", productId);
        return auditService.getActualVersion(productId);
    }

    @Operation(summary = "Получение старых версий продукта",
            description = "Возвращает предыдущие версии продукта по его идентификатору. Последняя версия не " +
                    "возвращается")
    @GetMapping("/find/previous/{productId}")
    public List<ProductDto> getPreviousProductVersions(
            @Positive @PathVariable @Parameter(description = "Идентификатор продукта",
            example = "123e4567-e89b-42d3-a456-556642440000", required = true) UUID productId) {
        log.info("Got the request for retrieving previous versions of the product with id {}", productId);
        return auditService.getPreviousVersions(productId);
    }

    @Operation(summary = "Получение версий продукта на определенный период",
            description = "Возвращает версии продукта по его идентификатору с учетом указанного периода. Указывать " +
                    "обе даты обязательно.")
    @GetMapping("/find/period/{productId}")
    public List<ProductDto> getProductVersionsByPeriod(
            @Parameter(description = "Дата начала периода", example = "2024-01-12")
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate,
            @Parameter(description = "Дата окончания периода", example = "2029-01-12")
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate toDate,
            @Positive @PathVariable @Parameter(description = "Идентификатор продукта",
                    example = "123e4567-e89b-42d3-a456-556642440000", required = true) UUID productId) {
        log.info("Got the request for retrieving versions of the product with id {} by period with start date {} " +
                "and end date {}", productId, fromDate, toDate);
        return auditService.getVersionsByPeriod(fromDate, toDate, productId);
    }

    @Operation(summary = "Откат версии продукта",
            description = "Откатывает версию продукта на одну из предыдущих. Возвращает продукт с необходимыми " +
                    "значениями. Номер версии при этом возрастает.")
    @PutMapping("/revert/{productId}")
    public ProductDto revertProductVersion(
            @Positive @PathVariable @Parameter(description = "Идентификатор продукта",
            example = "123e4567-e89b-42d3-a456-556642440000", required = true) UUID productId,
            @PositiveOrZero @Parameter(description = "Версия продукта", example = "1") @RequestParam long version) {
        log.info("Got the request for changing existing version of the product with id {} on one of the previous " +
                "versions: {}", productId, version);
        return auditService.revertVersion(productId, version);
    }
}