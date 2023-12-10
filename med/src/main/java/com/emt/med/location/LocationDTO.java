package com.emt.med.location;

import com.emt.med.batch.BatchEntityDTO;
import com.emt.med.disposalStation.DisposalStationEntityDTO;
import com.emt.med.inventoryOrder.InventoryOrderEntityDTO;
import com.emt.med.medicationBatch.MedicationBatchEntityDTO;
import com.emt.med.pharmacy.PharmacyEntityDTO;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({@JsonSubTypes.Type(value = PharmacyEntityDTO.class, name = "pharmacy"), @JsonSubTypes.Type(value = DisposalStationEntityDTO.class, name = "disposalStation")})
public class LocationDTO {

    @Positive(message = "id should be greater than 0")
    private Long id;
    @NotBlank(message = "name shouldn't be blank")
    private String name;
    private List<MedicationBatchEntityDTO> medicationBatchList;
    private List<BatchEntityDTO> batchList;
    private List<InventoryOrderEntityDTO> destinationList;
    private List<InventoryOrderEntityDTO> sourceList;
}
