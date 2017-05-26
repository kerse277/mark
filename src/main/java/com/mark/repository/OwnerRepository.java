package com.mark.repository;

import com.mark.model.relationship.OwnerRelationship;
import org.springframework.data.neo4j.repository.GraphRepository;

/**
 * Created by kerse on 29.03.2017.
 */
public interface OwnerRepository extends GraphRepository<OwnerRelationship> {
}
