package com.emt.med.batchRequest;

import com.emt.med.batch.BatchEntity;
import com.emt.med.supplyOrder.SupplyOrderEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatchRequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private SupplyOrderEntity supplyOrder;

    @ManyToOne

    private BatchEntity batch;

    private int quantity;
}
