package com.emt.med.consumable;

import com.emt.med.batch.BatchEntity;
import com.emt.med.batch.BatchEntityDTO;
import com.emt.med.supply.SupplyDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsumableEntityDTO extends SupplyDTO {
    private List<BatchEntityDTO> batches;
}
