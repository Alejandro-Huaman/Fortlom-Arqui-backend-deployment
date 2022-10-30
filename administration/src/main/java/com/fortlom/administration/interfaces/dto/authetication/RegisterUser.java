package com.fortlom.administration.interfaces.dto.authetication;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class RegisterUser {
    @NotBlank
    private String username;
    private String password;

    private Set<String> roles=new HashSet<>();
}
