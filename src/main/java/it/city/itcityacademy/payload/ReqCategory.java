package it.city.itcityacademy.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReqCategory {

    private Integer parentCategoryId;

    private  String name;

    private Double price;

    private String description;
}
