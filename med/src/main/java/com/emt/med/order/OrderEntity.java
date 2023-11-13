package com.emt.med.order;

import com.emt.med.supply.Supply;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Data
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long practitionerId;
    private Long quantity;

    private LocalDateTime authoredOn;

    private OrderStatus status;


    @ManyToMany
    private List<Supply> supplies;

}
