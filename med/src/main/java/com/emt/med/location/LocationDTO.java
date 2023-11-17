package com.emt.med.location;

import com.emt.med.disposalStation.DisposalStationEntity;
import com.emt.med.disposalStation.DisposalStationEntityDTO;
import com.emt.med.inventoryOrder.InventoryOrderEntity;
import com.emt.med.inventoryOrder.InventoryOrderEntityDTO;
import com.emt.med.pharmacy.PharmacyEntity;
import com.emt.med.pharmacy.PharmacyEntityDTO;
import com.emt.med.supply.SupplyDTO;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
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
    private List<SupplyDTO> supplyList;
    private List<InventoryOrderEntityDTO> destinationList;
    private List<InventoryOrderEntityDTO> sourceList;
}
