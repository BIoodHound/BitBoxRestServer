package com.bitboxserver.demo.models.dto;

import com.bitboxserver.demo.models.entities.PriceReduction;
import lombok.Data;

import java.util.Set;

@Data
public class PriceReductionInsert {
    private Long itemCode;
    private Set<PriceReduction> priceReductions;
}
