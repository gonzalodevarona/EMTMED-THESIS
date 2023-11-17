package com.emt.med.order;

import com.emt.med.disposalStation.DisposalStationEntityDTO;
import com.emt.med.pharmacy.PharmacyEntityDTO;
import com.emt.med.supply.Supply;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;


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
    private Set<Supply> supplies;

}
