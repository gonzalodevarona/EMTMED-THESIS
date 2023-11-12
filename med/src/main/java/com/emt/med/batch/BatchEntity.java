package com.emt.med.batch;

import com.emt.med.baseBatch.BaseBatch;
import com.emt.med.consumable.ConsumableEntity;
import com.emt.med.countingUnit.CountingUnitEntity;
import com.emt.med.medicationBatch.MedicationBatchEntity;
import com.emt.med.supply.Supply;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatchEntity extends BaseBatch {
    @ManyToOne
    @JsonBackReference("consumable-batch")
    private ConsumableEntity consumable;
}
