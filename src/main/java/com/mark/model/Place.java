package com.mark.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Accessors(chain = true)
public class Place {

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private List<String> placeType = new ArrayList<>();

    @Getter
    @Setter
    private String placeAddress;
}
