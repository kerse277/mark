package com.mark.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class CollectedMark {

    @Getter
    @Setter
    private String ownerID;

    @Getter
    @Setter
    private String type;

}
