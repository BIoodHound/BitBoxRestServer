package com.bitboxserver.demo.implementation;

import com.bitboxserver.demo.models.entities.Supplier;
import com.bitboxserver.demo.models.enums.EState;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class ItemServiceImpl {
    private Long itemCode;
    private String description;
    private Float price;
    private EState state;
    private Set<Supplier> suppliers;
    private Date creationDate;
    private String creator;
}
