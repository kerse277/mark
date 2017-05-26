package com.mark.repository;

import com.mark.model.relationship.FriendRelationship;
import org.springframework.data.neo4j.repository.GraphRepository;

/**
 * Created by kerse on 29.03.2017.
 */
public interface FriendRepository extends GraphRepository<FriendRelationship> {
}
