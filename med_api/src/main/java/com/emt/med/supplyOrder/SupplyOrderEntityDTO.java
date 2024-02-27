package com.emt.med.supplyOrder;


import com.emt.med.batchRequest.BatchRequestEntityDTO;
import com.emt.med.medicationBatchRequest.MedicationBatchRequestEntityDTO;
import com.emt.med.order.OrderEntityDTO;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeName("supplyOrder")
public class SupplyOrderEntityDTO extends OrderEntityDTO {
    private Long pacientId;
    private String diagnostic;

    private List<MedicationBatchRequestEntityDTO> medicationBatchRequests;

    private List<BatchRequestEntityDTO> batchRequests;
}
