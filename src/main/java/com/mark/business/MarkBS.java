package com.mark.business;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mark.Config.Config;
import com.mark.model.*;
import com.mark.model.relationship.OwnerRelationship;
import com.mark.repository.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class MarkBS {

    @Autowired
    MarkRepository markRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    OwnerRepository ownerRepository;


    public String addMark(MarkAddObject markAddObject) {

        Mark mark = markAddObject.getMark();
        mark.setUniqueID(UUID.randomUUID().toString());
        mark.setLive(true);

        markRepository.save(mark);
        Mark tempMark = markRepository.findByUniqueID(mark.getUniqueID());

        Person person = personRepository.findByAuhtToken(markAddObject.getPersonToken());
        person.getCollection().add(mark.getPlaceType());
        person.setActiveMarkCount(person.getPassiveMarkCount() + 1);
        personRepository.save(person);
        OwnerRelationship ownerRelationship = new OwnerRelationship()
                .setEndNode(person)
                .setStartNode(tempMark);
        ownerRepository.save(ownerRelationship);


        return "success";
    }

    public List<Mark> marksByPlace(String placeName) {
        return markRepository.findByPlaceName(placeName);
    }

    public List<Mark> myMarks(String authToken) {
        return markRepository.findMyMark(authToken);
    }

    public List<Mark> myLiveMarks(String authToken){
        return markRepository.findMyLiveMark(authToken);
    }

    public String collectMark(MarkCollectObject markCollectObject) {

        Mark mark = markRepository.findByUniqueID(markCollectObject.getMarkID());

        double latitude = Double.parseDouble(markCollectObject.getLatitude());
        double longitude = Double.parseDouble(markCollectObject.getLongitude());
        double markerLat1 = Double.parseDouble(mark.getLatitude()) - 0.00250;
        double markerLat2 = Double.parseDouble(mark.getLatitude()) + 0.00250;
        double markerLong1 = Double.parseDouble(mark.getLongitude()) - 0.00250;
        double markerLong2 = Double.parseDouble(mark.getLongitude()) + 0.00250;

        if (latitude >= markerLat1 && latitude <= markerLat2 && longitude >= markerLong1 && longitude <= markerLong2) {
            ObjectMapper objectMapper = new ObjectMapper();
            Person person = personRepository.findByAuhtToken(markCollectObject.getPersonToken());

            Person markOwner = markRepository.findMarkOwner(markCollectObject.getMarkID());
            markOwner.setPopularPoint(markOwner.getPopularPoint() + 2);
            markOwner.setPassiveMarkCount(markOwner.getPassiveMarkCount() + 1);
            markOwner.setActiveMarkCount(markOwner.getActiveMarkCount() - 1);
            boolean isVar = false;
            try {
                for (int i = 0; i < person.getCollectedMarks().size(); i++) {
                    CollectedMark mark1 = objectMapper.readValue(person.getCollectedMarks().get(i), CollectedMark.class);
                    if (markOwner.getUniqueID().equals(mark1.getOwnerID())) {
                        person.getCollectedMarks().remove(i);
                        int value = mark1.getMarkCount() + 1;
                        mark1.setMarkCount(value);


                        isVar = true;
                        if (markOwner.getPopularPoint() < 10 && value >= 3)
                            mark1.setMsgPermission(true);
                        else if (markOwner.getPopularPoint() < 20 & value >= 4)
                            mark1.setMsgPermission(true);
                        else if (markOwner.getPopularPoint() < 30 && value >= 5)
                            mark1.setMsgPermission(true);
                        else if (markOwner.getPopularPoint() < 40 && value >= 6)
                            mark1.setMsgPermission(true);
                        else if (markOwner.getPopularPoint() < 50 && value >= 7)
                            mark1.setMsgPermission(true);
                        else if (markOwner.getPopularPoint() < 60 && value >= 8)
                            mark1.setMsgPermission(true);
                        else if (markOwner.getPopularPoint() < 70 && value >= 9)
                            mark1.setMsgPermission(true);
                        else if (markOwner.getPopularPoint() < 80 && value >= 10)
                            mark1.setMsgPermission(true);
                        else if (markOwner.getPopularPoint() < 90 && value >= 11)
                            mark1.setMsgPermission(true);
                        else if (markOwner.getPopularPoint() < 100 && value >= 12)
                            mark1.setMsgPermission(true);
                        person.getCollectedMarks().add(objectMapper.writeValueAsString(mark1));
                        break;
                    }
                }
                if (!isVar) {
                    person.getCollectedMarks().add(objectMapper.writeValueAsString(new CollectedMark().setOwnerID(markOwner.getUniqueID()).setType(mark.getPlaceType()).setMarkCount(1).setMsgPermission(false)));
                }


            } catch (JsonProcessingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            person.getCollectedCollection().add(mark.getPlaceType());
            personRepository.save(markOwner);
            mark.setLive(false);
            markRepository.save(mark);
            personRepository.save(person);

       /* CustomPerson customPerson = new CustomPerson()
                .setActiveMarkCount(person.getActiveMarkCount())
                .setCollectedMarks(person.getCollectedMarks())
                .setEmail(person.getEmail())
                .setFirstName(person.getFirstName())
                .setLastName(person.getLastName())
                .setGender(person.getGender())
                .setPassiveMarkCount(person.getPassiveMarkCount())
                .setProfileDesc(person.getProfileDesc())
                .setProfilePic(person.getProfilePic())
                .setUniqueID(person.getUniqueID())
                .setWork(person.getWork());*/

            return "success";
        } else
            return "failed";
    }

    public List<Mark> myLocationMarks(String longitude, String latitude) {
        double longitude1 = Double.parseDouble(longitude.substring(0, 8));
        double latitude1 = Double.parseDouble(latitude.substring(0, 8));

        double long1 = longitude1 - 0.00250;
        double long2 = longitude1 + 0.00250;
        double lat1 = latitude1 - 0.00250;
        double lat2 = latitude1 + 0.00250;


        List<Mark> marks = markRepository.findByNearByMark(long1, long2, lat1, lat2);
        if (marks.size() > 0)
            return marks;
        else
            return null;
    }

    public List<Place> nearByPlace(String longitude, String latitude) {
        List<Place> resultList = new ArrayList<>();

        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {
            StringBuilder sb = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json");
            sb.append("?key=" + Config.GOOGLE_API_KEY);
            sb.append("&location=" + longitude + "," + latitude);
            sb.append("&radius=" + "250");
            sb.append("&type=&keyword=");

            URL url = new URL(sb.toString());
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
            return resultList;
        } catch (IOException e) {
            return resultList;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        try {
            // Create a JSON object hierarchy from the results
            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            JSONArray predsJsonArray = jsonObj.getJSONArray("results");

            for (int i = 0; i < predsJsonArray.length(); i++) {
                Place place = new Place()
                        .setPlaceAddress(predsJsonArray.getJSONObject(i).getString("vicinity"))
                        .setName(predsJsonArray.getJSONObject(i).getString("name"));
                for (int j = 0; j < predsJsonArray.getJSONObject(i).getJSONArray("types").length(); j++) {
                    place.getPlaceType().add(predsJsonArray.getJSONObject(i).getJSONArray("types").getString(j));
                }

                resultList.add(place);
            }
        } catch (JSONException e) {
            String a = e.toString();
        }

        return resultList;

    }

    public CustomPerson markOwner(String markid) {
        Person person = markRepository.findMarkOwner(markid);

        CustomPerson customPerson = new CustomPerson()
                .setCollection(person.getCollection())
                .setCollectedCollection(person.getCollectedCollection())
                .setActiveMarkCount(person.getActiveMarkCount())
                .setCollectedMarks(person.getCollectedMarks())
                .setEmail(person.getEmail())
                .setFirstName(person.getFirstName())
                .setLastName(person.getLastName())
                .setGender(person.getGender())
                .setPassiveMarkCount(person.getPassiveMarkCount())
                .setProfileDesc(person.getProfileDesc())
                .setProfilePic(person.getProfilePic())
                .setUniqueID(person.getUniqueID())
                .setWork(person.getWork());
        return customPerson;
    }

    public int calculateMsgLimit(int popPoint) {
        if (popPoint < 10)
            return 3;
        else if (popPoint < 20)
            return 4;
        else if (popPoint < 30)
            return 5;
        else if (popPoint < 40)
            return 6;
        else if (popPoint < 50)
            return 7;
        else if (popPoint < 60)
            return 8;
        else if (popPoint < 70)
            return 9;
        else if (popPoint < 80)
            return 10;
        else if (popPoint < 90)
            return 11;
        else if (popPoint < 100)
            return 12;
        return 0; //gönderecek bişi bulamadım
    }
}
