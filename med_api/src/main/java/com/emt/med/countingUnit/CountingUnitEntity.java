package com.emt.med.countingUnit;

import com.emt.med.supply.Supply;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountingUnitEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "countingUnit", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonManagedReference("countingUnit-supply")
    private List<Supply> supplyList;
}
