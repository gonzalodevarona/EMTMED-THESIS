package com.emt.med.consumable;

import com.emt.med.batch.BatchEntityDTO;
import com.emt.med.supply.SupplyDTO;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeName("consumable")
public class ConsumableEntityDTO extends SupplyDTO {
    private List<BatchEntityDTO> batches;
}
