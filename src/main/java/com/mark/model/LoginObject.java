package com.mark.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class LoginObject {


    @Setter
    @Getter
    private String email;

    @Getter
    @Setter
    private String password;


}
