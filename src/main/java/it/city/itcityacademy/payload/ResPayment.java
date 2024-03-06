package it.city.itcityacademy.payload;


import it.city.itcityacademy.entity.Group;
import it.city.itcityacademy.entity.Student;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Data
@Getter
@Setter
public class ResPayment {
    private Date payDate;
    private double paySum;
    private Student student;
    private Group group;
}
