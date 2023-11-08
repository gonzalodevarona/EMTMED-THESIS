package com.emt.med.field;

import com.emt.med.value.ValueEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.CascadeType;

import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FieldEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private FieldType type;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval=true)
    private Set<ValueEntity> values;
}
