package com.emt.med.supplyOrder;


import com.emt.med.order.OrderEntityDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplyOrderEntityDTO extends OrderEntityDTO {

    private Long pacientId;
    private String diagnostic;
}
