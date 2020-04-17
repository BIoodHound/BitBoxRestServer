package com.bitboxserver.demo.models.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "price_reductions")
@NoArgsConstructor
@Data
public class PriceReduction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Float reduction;

    @NotNull
    private Date startDate;

    @NotNull
    private Date endDate;
}
