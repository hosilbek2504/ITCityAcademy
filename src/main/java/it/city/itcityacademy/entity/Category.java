package it.city.itcityacademy.entity;


import it.city.itcityacademy.entity.template.AbsNameEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Category extends AbsNameEntity {

    @Column(nullable = false)
    private Double price;


    @Column(columnDefinition = "text")
    private String description;
    private boolean active;

}
