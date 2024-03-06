package it.city.itcityacademy.entity;

import it.city.itcityacademy.entity.enums.CameFromEnum;
import it.city.itcityacademy.entity.enums.UserStatus;
import it.city.itcityacademy.entity.template.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Student extends AbsEntity {

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private Integer age;

    @Column(nullable = false)
    private Date registrationDay;
    private double discount;
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    @Enumerated(EnumType.STRING)
    private CameFromEnum cameFromEnum;

    @ManyToOne(optional = false)
    private Group group;
}
