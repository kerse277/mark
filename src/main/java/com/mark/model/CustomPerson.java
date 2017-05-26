package com.mark.model;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Accessors(chain = true)
public class CustomPerson {

    @Getter
    @Setter
    private String uniqueID;

    @Getter
    @Setter
    private String firstName;

    @Getter
    @Setter
    private String lastName;

    @Getter
    @Setter
    private String email;


    @Getter
    @Setter
    private String work;

    @Getter
    @Setter
    private String gender;

    @Getter
    @Setter
    private String profileDesc;

    @Getter
    @Setter
    private String instagram;

    @Getter
    @Setter
    private int activeMarkCount;

    @Getter
    @Setter
    private int passiveMarkCount;

    @Getter
    @Setter
    private String profilePic;

    @Setter
    @Getter
    private List<String> collectedMarks = new ArrayList<>();

    @Getter
    @Setter
    private List<String> collection = new ArrayList<>();

    @Getter
    @Setter
    private int popularPoint;

}
