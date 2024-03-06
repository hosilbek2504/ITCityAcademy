package it.city.itcityacademy.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class ReqPayment {
    private Date payDate;
    private double paySum;
    private UUID studentId;
    private Integer groupId;
}
