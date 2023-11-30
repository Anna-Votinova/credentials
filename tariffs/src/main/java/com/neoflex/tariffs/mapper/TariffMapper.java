package com.neoflex.tariffs.mapper;

import com.neoflex.tariffs.dto.TariffCreateDto;
import com.neoflex.tariffs.dto.TariffDto;
import com.neoflex.tariffs.dto.TariffKafkaMessage;
import com.neoflex.tariffs.entity.Tariff;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TariffMapper {

    public static Tariff toTariff(TariffCreateDto tariffCreateDto) {
        return Tariff.builder()
                .name(tariffCreateDto.getName())
                .startDate(tariffCreateDto.getStartDate())
                .endDate(tariffCreateDto.getEndDate())
                .description(tariffCreateDto.getDescription())
                .rate(tariffCreateDto.getRate())
                .author(tariffCreateDto.getAuthor())
                .build();
    }

    public static TariffDto toTariffDto(Tariff tariff) {
        return TariffDto.builder()
                .id(tariff.getId())
                .name(tariff.getName())
                .startDate(tariff.getStartDate())
                .endDate(tariff.getEndDate())
                .description(tariff.getDescription())
                .rate(tariff.getRate())
                .author(tariff.getAuthor())
                .version(tariff.getVersion())
                .build();
    }
    public static TariffKafkaMessage toTariffKafkaMessage(UUID productId, Tariff tariff) {
        return TariffKafkaMessage.builder()
                .id(tariff.getId())
                .name(tariff.getName())
                .startDate(tariff.getStartDate())
                .endDate(tariff.getEndDate())
                .description(tariff.getDescription())
                .productId(productId)
                .rate(tariff.getRate())
                .author(tariff.getAuthor())
                .version(tariff.getVersion())
                .build();
    }
}