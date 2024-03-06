package it.city.itcityacademy.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResCategory {

    private Integer id;

    private String name;

    private Integer parentCategoryId;

    private Double price;

    private String description;
}
