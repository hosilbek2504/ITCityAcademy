package it.city.itcityacademy.payload;

import it.city.itcityacademy.entity.Category;
import it.city.itcityacademy.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResGroup {
    private Integer id;
    private String name;
    private Category category;
    private Timestamp startHour;
    private Timestamp finishHour;
    private Date lessonStartedDate;
    private Date lessonFinishDate;
    private User teacher;
    private String weekday;
}
