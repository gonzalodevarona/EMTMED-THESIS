package com.emt.med.medicine;

import com.emt.med.batch.BatchEntity;
import com.emt.med.medicationBatch.MedicationBatchEntity;
import com.emt.med.supply.Supply;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicineEntity extends Supply {

    private String activePharmaceuticalIngredient;
    private String concentration;
    @OneToMany(mappedBy = "medicine", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonManagedReference("medicine-medicationBatch")
    @ToString.Exclude
    private List<MedicationBatchEntity> batches;

}
