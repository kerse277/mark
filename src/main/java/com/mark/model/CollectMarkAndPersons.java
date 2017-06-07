package com.mark.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class CollectMarkAndPersons {

    @Getter
    @Setter
    private CustomPerson person;

    @Getter
    @Setter
    private int markCount;

    @Setter
    @Getter
    private boolean msgPermision;

}
