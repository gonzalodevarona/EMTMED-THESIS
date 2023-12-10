package com.emt.med.supplyOrder;

import com.emt.med.batchRequest.BatchRequestEntity;
import com.emt.med.batchRequest.BatchRequestEntityDTO;
import com.emt.med.medicationBatchRequest.MedicationBatchRequestEntity;
import com.emt.med.medicationBatchRequest.MedicationBatchRequestEntityDTO;
import com.emt.med.order.OrderEntity;
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
public class SupplyOrderEntity extends OrderEntity {

    private Long pacientId;
    private String diagnostic;
    @OneToMany(mappedBy = "supplyOrder", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonManagedReference("supplyOrder-medicationBatchRequests")
    @ToString.Exclude
    private List<MedicationBatchRequestEntity> medicationBatchRequests;
    @OneToMany(mappedBy = "supplyOrder", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonManagedReference("supplyOrder-batchRequests")
    @ToString.Exclude
    private List<BatchRequestEntity> batchRequests;

}
