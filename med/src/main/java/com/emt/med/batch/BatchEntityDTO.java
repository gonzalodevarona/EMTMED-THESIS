package com.emt.med.batch;

import com.emt.med.baseBatch.BaseBatchDTO;
import com.emt.med.consumable.ConsumableEntityDTO;
import com.emt.med.location.LocationDTO;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatchEntityDTO extends BaseBatchDTO {
    private ConsumableEntityDTO consumable;
    private LocationDTO location;
}
