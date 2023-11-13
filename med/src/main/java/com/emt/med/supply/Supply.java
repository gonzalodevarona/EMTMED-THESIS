package com.emt.med.supply;

import com.emt.med.batch.BatchEntity;
import com.emt.med.countingUnit.CountingUnitEntity;
import com.emt.med.location.Location;
import com.emt.med.medicationBatch.MedicationBatchEntity;
import com.emt.med.order.OrderEntity;
import com.emt.med.weightUnit.WeightUnitEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonBackReference("weightUnit-supply")
    private WeightUnitEntity weightUnit;
    @ManyToOne
    @JsonBackReference("countingUnit-supply")
    private CountingUnitEntity countingUnit;
    @ManyToMany
    private List<OrderEntity> orders;
    @ManyToOne
    @JsonBackReference("location-supply")
    private Location location;


}
