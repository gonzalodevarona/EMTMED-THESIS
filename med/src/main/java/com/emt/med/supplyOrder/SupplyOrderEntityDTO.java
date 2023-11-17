package com.emt.med.supplyOrder;


import com.emt.med.order.OrderEntityDTO;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeName("supplyOrder")
public class SupplyOrderEntityDTO extends OrderEntityDTO {
    private Long pacientId;
    private String diagnostic;
}
