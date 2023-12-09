package com.emt.med.medicationBatchRequest;

import com.emt.med.medicationBatch.MedicationBatchEntity;
import com.emt.med.supplyOrder.SupplyOrderEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicationBatchRequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private SupplyOrderEntity supplyOrder;

    @ManyToOne

    private MedicationBatchEntity medicationBatch;

    private int quantity;
}
