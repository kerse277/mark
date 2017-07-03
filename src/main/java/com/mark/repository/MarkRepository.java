package com.mark.repository;

import com.mark.model.Mark;
import com.mark.model.Person;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by kerse on 29.03.2017.
 */
public interface MarkRepository extends GraphRepository<Mark> {

    Mark findByUniqueID(String uniqueID);

    List<Mark> findByPlaceName(String placeName);

    @Query("Match (n:Person{authToken: {authToken} )<-[:Owner]-(m:Mark) where m.live = true return m")
    List<Mark> findMyLiveMark(@Param("authToken") String authToken);

    @Query("Match (n:Person{authToken: {authToken} )<-[:Owner]-(m:Mark)  return m")
    List<Mark> findMyMark(@Param("authToken") String authToken);

    @Query("Match (n:Mark{uniqueID: {uniqueID} })-[:Owner]->(m:Person) return m")
    Person findMarkOwner(@Param("uniqueID") String uniqueID);

    @Query("Match (n:Mark) where toFloat(n.latitude) <= {lat1} and toFloat(n.latitude) >= {lat2} and toFloat(n.longitude) <= {long1} and toFloat(n.longitude) >= {long2} and n.live = true return n")
    List<Mark> findByNearByMark(@Param("long2") double long1,@Param("long1") double long2,@Param("lat2") double lat1,@Param("lat1") double lat2);



}
