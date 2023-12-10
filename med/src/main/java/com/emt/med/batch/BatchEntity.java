package com.emt.med.batch;

import com.emt.med.baseBatch.BaseBatch;
import com.emt.med.consumable.ConsumableEntity;
import com.emt.med.location.Location;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatchEntity extends BaseBatch {
    @ManyToOne
    @JsonBackReference("consumable-batch")
    private ConsumableEntity consumable;

    @ManyToOne
    @JsonBackReference("location-batch")
    private Location location;
}
