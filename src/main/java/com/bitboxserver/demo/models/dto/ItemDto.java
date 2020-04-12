package com.bitboxserver.demo.models.dto;

import com.bitboxserver.demo.models.entities.PriceReduction;
import com.bitboxserver.demo.models.entities.Supplier;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
public class ItemDto {
    @NotNull
    private String description;
    private Float price;
    private Set<Supplier> suppliers;
    private Set<PriceReduction> priceReductions;
    private String creator;

}
