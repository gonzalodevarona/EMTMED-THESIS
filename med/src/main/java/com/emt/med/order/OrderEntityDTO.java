package com.emt.med.order;

import com.emt.med.inventoryOrder.InventoryOrderEntityDTO;
import com.emt.med.supplyOrder.SupplyOrderEntityDTO;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({@JsonSubTypes.Type(value = InventoryOrderEntityDTO.class, name = "inventoryOrder"), @JsonSubTypes.Type(value = SupplyOrderEntityDTO.class, name = "supplyOrder")})
public class OrderEntityDTO {

    @Positive(message = "id number should be greater than zero")
    private Long id;
    @Positive(message = "practitioner id should be greater than zero")
    private Long practitionerId;

    @PastOrPresent(message = "authored on date should be today or before")
        private LocalDateTime authoredOn;
    @NotNull
    private OrderStatus status;


    @NotBlank(message = "note should not be blank")
    private String note;
}
