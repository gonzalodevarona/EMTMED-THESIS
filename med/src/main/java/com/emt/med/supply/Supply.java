package com.emt.med.supply;

import com.emt.med.order.OrderEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
public abstract class Supply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long weight;
    private Integer quantity;

    @JsonBackReference(value="order-supply")
    @ManyToOne
    private OrderEntity order;

}
