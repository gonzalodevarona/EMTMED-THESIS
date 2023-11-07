package com.emt.med.order;

import com.emt.med.batch.BatchEntity;
import com.emt.med.supply.Supply;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.engine.jdbc.batch.spi.Batch;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Data
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long practitionerId;

    private LocalDateTime authoredOn;

    private OrderStatus status;


    @ManyToMany
    private List<Supply> supplies;

}
