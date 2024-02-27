package com.emt.med.supply;

import com.emt.med.countingUnit.CountingUnitEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;


@Entity
@Data
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Supply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idNumberCreatedBy;
    private String name;
    private Long quantity;
    @ManyToOne
    @JsonBackReference("countingUnit-supply")
    private CountingUnitEntity countingUnit;



}
