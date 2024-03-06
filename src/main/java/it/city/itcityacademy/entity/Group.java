package it.city.itcityacademy.entity;

import it.city.itcityacademy.entity.enums.Weekday;
import it.city.itcityacademy.entity.template.AbsNameEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "groups")
public class Group extends AbsNameEntity {
    @ManyToOne
    private Category category;

    @Column(nullable = false)
    private Timestamp startHour;
    @Column(nullable = false)
    private Timestamp finishHour;

    private Date lessonStartedDate;

    private Date lessonFinishDate;

    @ManyToOne(optional = false)
    private  User teacher;

    @ElementCollection
    private List<String> weekdays;

    @Enumerated(EnumType.STRING)
    private Weekday weekday;

    private boolean active;
}