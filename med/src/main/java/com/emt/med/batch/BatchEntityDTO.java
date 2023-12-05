package com.emt.med.batch;

import com.emt.med.baseBatch.BaseBatchDTO;
import com.emt.med.consumable.ConsumableEntityDTO;
import com.emt.med.location.LocationDTO;
import com.emt.med.supply.Supply;
import com.emt.med.supply.SupplyDTO;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.hibernate.engine.transaction.jta.platform.internal.SunOneJtaPlatform;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatchEntityDTO extends BaseBatchDTO {
    private ConsumableEntityDTO consumable;
    private LocationDTO location;
}
