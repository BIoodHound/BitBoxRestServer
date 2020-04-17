package com.bitboxserver.demo.models.entities;

import com.bitboxserver.demo.models.enums.EState;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "items")
public class Item implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long itemCode;

    @NotBlank
    @Size(max = 512)
    private String description;

    private Float price;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EState state;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "item_suppliers",
            joinColumns = @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "supplier_id")
    )
    private Set<Supplier> suppliers = new HashSet<>();


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "item_price_reductions",
            joinColumns = @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "price_reduction_id")
    )
    private Set<PriceReduction> priceReductions = new HashSet<>();

    private Date creationDate = new Date();

    private String creator;

    public Item() {
    }

    public Item(Long itemCode, @NotBlank @Size(max = 512) String description, @Size(max = 13) Float price, EState state, Set<Supplier> suppliers, Set<PriceReduction> priceReductions, Date creationDate, String creator) {
        this.itemCode = itemCode;
        this.description = description;
        this.price = price;
        this.state = state;
        this.suppliers = suppliers;
        this.priceReductions = priceReductions;
        this.creationDate = creationDate;
        this.creator = creator;
    }

}
