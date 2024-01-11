package com.emt.med.supply;

import com.emt.med.consumable.ConsumableEntityDTO;
import com.emt.med.countingUnit.CountingUnitEntityDTO;
import com.emt.med.medicine.MedicineEntityDTO;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.Min;
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
@JsonSubTypes({@JsonSubTypes.Type(value = MedicineEntityDTO.class, name = "medicine"), @JsonSubTypes.Type(value = ConsumableEntityDTO.class, name = "consumable")})
public class SupplyDTO {

    @Positive(message = "id number should be greater than zero")
    private Long id;
    @Positive(message = "id number created by number should be greater than zero")
    private Long idNumberCreatedBy;
    @NotBlank(message = "name shouldn't be blank")
    private String name;
    @Min(0)
    private Long quantity;

    private CountingUnitEntityDTO countingUnit;


}
