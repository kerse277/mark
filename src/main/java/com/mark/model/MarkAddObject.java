package com.mark.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class MarkAddObject {

    @Getter
    @Setter
    private Mark mark;

    @Getter
    @Setter
    private String personToken;

}
