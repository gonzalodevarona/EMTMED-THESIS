package com.emt.med.weightUnit;

import com.emt.med.supply.Supply;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeightUnitEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "weightUnit", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonManagedReference("weightUnit-supply")
    private List<Supply> supplyList;
}
