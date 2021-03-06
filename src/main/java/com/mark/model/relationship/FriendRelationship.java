package com.mark.model.relationship;

import com.mark.model.Person;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

@RelationshipEntity(type = "Friend")
@Accessors(chain = true)
public class FriendRelationship {

    @GraphId
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    @StartNode
    private Person startNode;

    @Getter
    @Setter
    @EndNode
    private Person endNode;
}
