package it.city.itcityacademy.payload;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter

public class ReqLogin {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
