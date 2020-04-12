package com.bitboxserver.demo.models.dto;

import com.bitboxserver.demo.models.entities.Supplier;
import lombok.Data;

import java.util.Set;

@Data
public class SupplierInsert {
    private Long itemCode;
    private Set<Supplier> suppliers;
}
