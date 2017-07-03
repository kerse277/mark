package com.mark.business;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mark.model.*;
import com.mark.repository.PersonRepository;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

@Service
public class PersonBS {

    @Autowired
    PersonRepository personRepository;

    public String addPerson(Person person) {
        Person person1 = personRepository.findByEmail(person.getEmail());
        if (person1 == null) {
            String uuid = UUID.randomUUID().toString();
            person.setUniqueID(uuid)
                    .setStatus(true)
                    .setProfilePic("/mark/images/" + uuid + "/avatar.jpg");
            personRepository.save(person);

            try {
                File imageFile;
                if (person.getGender().equals("Male")) {
                    imageFile = new File("//var//www//html//mark//images//avatarmale.jpg");
                } else {
                    imageFile = new File("//var//www//html//mark//images//avatarfemale.jpg");
                }
                File mkdir = new File("//var//www//html//mark//images//" + person.getUniqueID());
                mkdir.mkdir();
                File filedir = new File("//var//www//html//mark//images//" + person.getUniqueID() + "//avatar.jpg");

                FileUtils.copyFile(imageFile, filedir);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "success";
        } else
            return "failed";
    }


    public String updatePerson(Person person) {
        Person person1 = personRepository.findByAuhtToken(person.getAuhtToken());
        if (!person.getProfileDesc().equals("") && person.getProfileDesc() != null)
            person1.setProfileDesc(person.getProfileDesc());
        if (!person.getWork().equals("") && person.getWork() != null)
            person1.setWork(person.getWork());
        if (!person.getInstagram().equals("") && person.getInstagram() != null)
            person1.setInstagram(person.getInstagram());

        personRepository.save(person1);
        return "success";

    }

    public Token login(LoginObject loginObject) {

        Person person = personRepository.findByEmailAndPassword(loginObject.getEmail(), loginObject.getPassword());

        if (person != null) {
            String authToken = UUID.randomUUID().toString();

            Token token = new Token()
                    .setAuthToken(authToken)
                    .setUniqueID(person.getUniqueID());

            person.setAuhtToken(authToken);
            personRepository.save(person);
            return token;
        } else {
            return null;
        }
    }

    public List<CollectMarkAndPersons> getMyCollectMarks(String authToken) {
        ObjectMapper mapper = new ObjectMapper();
        Person person = personRepository.findByAuhtToken(authToken);
        List<CollectMarkAndPersons> collectMarkAndPersonses = new ArrayList<>();
        for (String value : person.getCollectedMarks()) {
            try {
                CollectedMark collectedMark = mapper.readValue(value, CollectedMark.class);

                Person person1 = personRepository.findByUniqueID(collectedMark.getOwnerID());
                CustomPerson customPerson = new CustomPerson()
                        .setCollection(person1.getCollection())
                        .setCollectedCollection(person.getCollectedCollection())
                        .setActiveMarkCount(person1.getActiveMarkCount())
                        .setEmail(person1.getEmail())
                        .setFirstName(person1.getFirstName())
                        .setLastName(person1.getLastName())
                        .setGender(person1.getGender())
                        .setPopularPoint(person1.getPopularPoint())
                        .setPassiveMarkCount(person1.getPassiveMarkCount())
                        .setProfileDesc(person1.getProfileDesc())
                        .setProfilePic(person1.getProfilePic())
                        .setUniqueID(person1.getUniqueID())
                        .setWork(person1.getWork());

                CollectMarkAndPersons markAndPersons = new CollectMarkAndPersons()
                        .setPerson(customPerson)
                        .setMarkCount(collectedMark.getMarkCount())
                        .setMsgPermision(collectedMark.isMsgPermission());
                collectMarkAndPersonses.add(markAndPersons);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return collectMarkAndPersonses;
    }

    public CustomPerson myInfo(String auhttoken) {
        Person person = personRepository.findByAuhtToken(auhttoken);

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
                .setInstagram(person.getInstagram())
                .setProfilePic(person.getProfilePic())
                .setUniqueID(person.getUniqueID())
                .setWork(person.getWork())
                .setPopularPoint(person.getPopularPoint());
        return customPerson;
    }

    public CustomPerson showProfile(String uid) {


        Person person = personRepository.findByUniqueID(uid);
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
                .setInstagram(person.getInstagram())
                .setProfilePic(person.getProfilePic())
                .setUniqueID(person.getUniqueID())
                .setWork(person.getWork())
                .setPopularPoint(person.getPopularPoint());

        return customPerson;
    }

}
