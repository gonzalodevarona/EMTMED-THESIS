package com.emt.med.supply;

import com.emt.med.order.OrderEntity;
import com.emt.med.weightUnit.WeightUnitEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Entity
@Data
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Supply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long weight;
    private Long quantity;
    @ManyToOne
    @JsonBackReference
    private WeightUnitEntity weightUnit;
    @ManyToMany
    private List<OrderEntity> orders;

}
