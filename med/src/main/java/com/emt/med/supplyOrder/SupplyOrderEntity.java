package com.emt.med.supplyOrder;

import com.emt.med.order.OrderEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplyOrderEntity extends OrderEntity {

    private Long pacientId;
    private String diagnostic;

}
