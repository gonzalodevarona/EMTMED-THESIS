package com.emt.med.value;

import com.emt.med.field.FieldEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;



@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValueEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String value;
    @ManyToOne
    @Cascade(CascadeType.PERSIST)
    @JsonBackReference(value="field-value")
    private FieldEntity field;
}
