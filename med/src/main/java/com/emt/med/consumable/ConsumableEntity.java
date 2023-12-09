package com.emt.med.consumable;

import com.emt.med.batch.BatchEntity;
import com.emt.med.medicationBatch.MedicationBatchEntity;
import com.emt.med.supply.Supply;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsumableEntity extends Supply {
    @OneToMany(mappedBy = "consumable", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
    @JsonManagedReference("consumable-batch")
    @ToString.Exclude
    private List<BatchEntity> batches;
}
