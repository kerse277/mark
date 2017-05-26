package com.mark.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity(label = "Mark")
@Accessors(chain = true)
public class Mark {

    @GraphId
    @Setter
    @Getter
    private Long id;

    @Getter
    @Setter
    private String uniqueID;

    @Getter
    @Setter
    private String longitude;

    @Getter
    @Setter
    private String latitude;

    @Getter
    @Setter
    private String placeName;

    @Getter
    @Setter
    private String placeType;

    @Getter
    @Setter
    private boolean live;

}
