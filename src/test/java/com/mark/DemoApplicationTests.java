package com.mark;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mark.model.Mark;
import com.mark.model.CollectedMark;
import com.mark.model.Person;
import com.mark.model.relationship.OwnerRelationship;
import com.mark.repository.MarkRepository;
import com.mark.repository.OwnerRepository;
import com.mark.repository.PersonRepository;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    OwnerRepository ownerRepository;

    @Autowired
    MarkRepository markRepository;

    @Test
    public void add() {
        personRepository.deleteAll();
        ObjectMapper objectMapper = new ObjectMapper();
        List<Person> personList = new ArrayList<>();
        try {
            personList = Arrays.asList(objectMapper.readValue(getPersons(), Person[].class));
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Person person : personList) {
            String uuid = UUID.randomUUID().toString();
            person.setStatus(true)
                    .setUniqueID(uuid)
                    .setProfilePic("/mark/images/" + uuid + "/avatar.jpg");
            personRepository.save(person);
            try {
                File imageFile;
                if(person.getGender().equals("Male"))
                    imageFile = new File("//var//www//html//mark//images//avatarmale.jpg");
                else
                    imageFile = new File("//var//www//html//mark//images//avatarfemale.jpg");

                File mkdir = new File("//var//www/sudo c/html//mark//images//" + person.getUniqueID());
                mkdir.mkdir();
                File filedir = new File("//var//www//html//mark//images//" + person.getUniqueID() + "//avatar.jpg");

                FileUtils.copyFile(imageFile, filedir);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    @Test
    public void myMark() {
        Person person = personRepository.findByUniqueID("0a8e3aa5-f393-4a5e-81ca-5c7cf840d86d");
        List<String> myMarks = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        CollectedMark collectedMark = new CollectedMark()
                .setOwnerID("af06dac9-b700-4787-9856-46b806630f42")
                .setType("Gezgin");
        CollectedMark collectedMark1 = new CollectedMark()
                .setOwnerID("af06dac9-b700-4787-9856-46b806630f42")
                .setType("AAAA");
        try {
            myMarks.add(objectMapper.writeValueAsString(collectedMark));
            myMarks.add(objectMapper.writeValueAsString(collectedMark1));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        person.setCollectedMarks(myMarks);
        personRepository.save(person);
    }


    @Test
    public void addMark() {
      //  markRepository.deleteAll();
      /*  ObjectMapper objectMapper = new ObjectMapper();
        List<Mark> marks = new ArrayList<>();

        try {
            marks = Arrays.asList(objectMapper.readValue(getMarks(), Mark[].class));
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Mark mark : marks) {
            mark.setUniqueID(UUID.randomUUID().toString());
            markRepository.save(mark);
        }*/


      Mark mark = new Mark()
              .setUniqueID(UUID.randomUUID().toString())
              .setLongitude("32.74492")
              .setLatitude("39.86999")
              .setPlaceType("empty");

      markRepository.save(mark);
    }

    @Test
    public void relation() {
        List<Person> personList = (List<Person>) personRepository.findAll();
        List<Mark> markList = (List<Mark>) markRepository.findAll();
        Random random = new Random();
        for (Mark mark : markList) {
            OwnerRelationship ownerRelationship = new OwnerRelationship()
                    .setStartNode(mark)
                    .setEndNode(personList.get(random.nextInt(personList.size()-1)));

        ownerRepository.save(ownerRelationship);
        }
    }


    public String getMarks() {
        return "[{\n" +
                "  \"type\": \"Kaşif\",\n" +
                "  \"longitude\": \"39.845387\",\n" +
                "  \"latitude\": \"32.753123\",\n" +
                "  \"placeName\": \"Hacettepe Cami\",\n" +
                "  \"placeType\": \"Mosque\"\n" +
                "}, {\n" +
                "  \"type\": \"Spotman\",\n" +
                "  \"longitude\": \"39.872545\",\n" +
                "  \"latitude\": \"32.733557\",\n" +
                "  \"placeName\": \"Bilkent Merkez Spor Salonu\",\n" +
                "  \"placeType\": \"Sport Center\"\n" +
                "}, {\n" +
                "  \"type\": \"Gurme\",\n" +
                "  \"longitude\": \"39.869645\",\n" +
                "  \"latitude\": \"32.744678\",\n" +
                "  \"placeName\": \"Starbucks\",\n" +
                "  \"placeType\": \"Cafe\"\n" +
                "}, {\n" +
                "  \"type\": \"Okul Çocuğu\",\n" +
                "  \"longitude\": \"39.825357\",\n" +
                "  \"latitude\": \"32.772842\",\n" +
                "  \"placeName\": \"İşletme Fakultesi\",\n" +
                "  \"placeType\": \"Faculty\"\n" +
                "}, {\n" +
                "  \"type\": \"Gurme\",\n" +
                "  \"longitude\": \"39.864327\",\n" +
                "  \"latitude\": \"32.745877\",\n" +
                "  \"placeName\": \"Bilkent Burger King\",\n" +
                "  \"placeType\": \"Restaurant\"\n" +
                "}, {\n" +
                "  \"type\": \"Party Animal\",\n" +
                "  \"longitude\": \"39.856871\",\n" +
                "  \"latitude\": \"32.735272\",\n" +
                "  \"placeName\": \"IF Club\",\n" +
                "  \"placeType\": \"Night Club\"\n" +
                "}, {\n" +
                "  \"type\": \"Gurme\",\n" +
                "  \"longitude\": \"39.878964\",\n" +
                "  \"latitude\": \"32.797763\",\n" +
                "  \"placeName\": \"Starbucks\",\n" +
                "  \"placeType\": \"Cafe\"\n" +
                "}, {\n" +
                "  \"type\": \"Gurme\",\n" +
                "  \"longitude\": \"39.844372\",\n" +
                "  \"latitude\": \"32.712456\",\n" +
                "  \"placeName\": \"Burger House\",\n" +
                "  \"placeType\": \"Restaurant\"\n" +
                "}, {\n" +
                "  \"type\": \"Yönetmen\",\n" +
                "  \"longitude\": \"39.865824\",\n" +
                "  \"latitude\": \"32.742368\",\n" +
                "  \"placeName\": \"Cinemaximum Bilkent\",\n" +
                "  \"placeType\": \"Mosque\"\n" +
                "}, {\n" +
                "  \"type\": \"Gurme\",\n" +
                "  \"longitude\": \"39.888956\",\n" +
                "  \"latitude\": \"32.799874\",\n" +
                "  \"placeName\": \"Acıktım Usta\",\n" +
                "  \"placeType\": \"Restaurant\"\n" +
                "}, {\n" +
                "  \"type\": \"İşkolik\",\n" +
                "  \"longitude\": \"39.841235\",\n" +
                "  \"latitude\": \"32.732573\",\n" +
                "  \"placeName\": \"Hacettepe Teknokent\",\n" +
                "  \"placeType\": \"Office\"\n" +
                "}, {\n" +
                "  \"type\": \"İşkolik\",\n" +
                "  \"longitude\": \"39.855662\",\n" +
                "  \"latitude\": \"32.747141\",\n" +
                "  \"placeName\": \"Bilkent PTT\",\n" +
                "  \"placeType\": \"Office\"\n" +
                "}, {\n" +
                "  \"type\": \"Party Animal\",\n" +
                "  \"longitude\": \"39.836257\",\n" +
                "  \"latitude\": \"32.712278\",\n" +
                "  \"placeName\": \"chifree\",\n" +
                "  \"placeType\": \"Night Club\"\n" +
                "}, {\n" +
                "  \"type\": \"Gurme\",\n" +
                "  \"longitude\": \"39.822445\",\n" +
                "  \"latitude\": \"32.729658\",\n" +
                "  \"placeName\": \"Zadegan\",\n" +
                "  \"placeType\": \"Restaurant\"\n" +
                "}, {\n" +
                "  \"type\": \"Okul Çocuğu\",\n" +
                "  \"longitude\": \"39.842378\",\n" +
                "  \"latitude\": \"32.778513\",\n" +
                "  \"placeName\": \"Hacettepe İlkokul\",\n" +
                "  \"placeType\": \"School\"\n" +
                "}, {\n" +
                "  \"type\": \"İşkolik\",\n" +
                "  \"longitude\": \"39.866845\",\n" +
                "  \"latitude\": \"32.768578\",\n" +
                "  \"placeName\": \"Bilkent Öğrenci İşleri\",\n" +
                "  \"placeType\": \"Office\"\n" +
                "}, {\n" +
                "  \"type\": \"Sporcu\",\n" +
                "  \"longitude\": \"39.872357\",\n" +
                "  \"latitude\": \"32.769782\",\n" +
                "  \"placeName\": \"Bilkent Sağlık Merkezi\",\n" +
                "  \"placeType\": \"Hospital\"\n" +
                "}, {\n" +
                "  \"type\": \"Okul Çocuğu\",\n" +
                "  \"longitude\": \"39.845138\",\n" +
                "  \"latitude\": \"32.745478\",\n" +
                "  \"placeName\": \"Hacettepe Hukuk Fakultesi\",\n" +
                "  \"placeType\": \"Faculty\"\n" +
                "}, {\n" +
                "  \"type\": \"Okul Çocuğu\",\n" +
                "  \"longitude\": \"39.845387\",\n" +
                "  \"latitude\": \"32.744587\",\n" +
                "  \"placeName\": \"Bilkent Mimarlık Fakultesi\",\n" +
                "  \"placeType\": \"Faculty\"\n" +
                "}, {\n" +
                "  \"type\": \"Gurme\",\n" +
                "  \"longitude\": \"39.822339\",\n" +
                "  \"latitude\": \"32.754539\",\n" +
                "  \"placeName\": \"Tostcu Selim\",\n" +
                "  \"placeType\": \"Restaurant\"\n" +
                "}, {\n" +
                "  \"type\": \"Gurme\",\n" +
                "  \"longitude\": \"39.845387\",\n" +
                "  \"latitude\": \"32.767787\"\n" +
                "}, {\n" +
                "  \"type\": \"Gurme\",\n" +
                "  \"longitude\": \"39.865422\",\n" +
                "  \"latitude\": \"32.754329\"\n" +
                "}, {\n" +
                "  \"type\": \"Gurme\",\n" +
                "  \"longitude\": \"39.894764\",\n" +
                "  \"latitude\": \"32.723984\"\n" +
                "}, {\n" +
                "  \"type\": \"Gurme\",\n" +
                "  \"longitude\": \"39.823094\",\n" +
                "  \"latitude\": \"32.745876\"\n" +
                "}, {\n" +
                "  \"type\": \"Gurme\",\n" +
                "  \"longitude\": \"39.844750\",\n" +
                "  \"latitude\": \"32.7459989\",\n" +
                "  \"placeName\": \"Bilkent Cami\",\n" +
                "  \"placeType\": \"Mosque\"\n" +
                "}, {\n" +
                "  \"type\": \"Gurme\",\n" +
                "  \"longitude\": \"39.845449\",\n" +
                "  \"latitude\": \"32.723887\"\n" +
                "}, {\n" +
                "  \"type\": \"Gurme\",\n" +
                "  \"longitude\": \"39.845975\",\n" +
                "  \"latitude\": \"32.767472\"\n" +
                "}, {\n" +
                "  \"type\": \"Gurme\",\n" +
                "  \"longitude\": \"39.834756\",\n" +
                "  \"latitude\": \"32.711248\"\n" +
                "}, {\n" +
                "  \"type\": \"Sporcu\",\n" +
                "  \"longitude\": \"39.843682\",\n" +
                "  \"latitude\": \"32.754862\",\n" +
                "  \"placeName\": \"İntertnational Sport\",\n" +
                "  \"placeType\": \"Sport Center\"\n" +
                "}, {\n" +
                "  \"type\": \"Gurme\",\n" +
                "  \"longitude\": \"39.843793\",\n" +
                "  \"latitude\": \"32.745804\"\n" +
                "}, {\n" +
                "  \"type\": \"Gurme\",\n" +
                "  \"longitude\": \"39.834853\",\n" +
                "  \"latitude\": \"32.745839\"\n" +
                "}, {\n" +
                "  \"type\": \"Gurme\",\n" +
                "  \"longitude\": \"39.823794\",\n" +
                "  \"latitude\": \"32.756036\"\n" +
                "}, {\n" +
                "  \"type\": \"Gurme\",\n" +
                "  \"longitude\": \"39.856780\",\n" +
                "  \"latitude\": \"32.734542\"\n" +
                "}, {\n" +
                "  \"type\": \"Gurme\",\n" +
                "  \"longitude\": \"39.812218\",\n" +
                "  \"latitude\": \"32.723433\",\n" +
                "  \"placeName\": \"İplik Terzi\",\n" +
                "  \"placeType\": \"Office\"\n" +
                "}, {\n" +
                "  \"type\": \"Gurme\",\n" +
                "  \"longitude\": \"39.887653\",\n" +
                "  \"latitude\": \"32.734564\"\n" +
                "}, {\n" +
                "  \"type\": \"Gurme\",\n" +
                "  \"longitude\": \"39.865765\",\n" +
                "  \"latitude\": \"32.743793\",\n" +
                "  \"placeName\": \"Hacettepe Cami\",\n" +
                "  \"placeType\": \"Mosque\"\n" +
                "}, {\n" +
                "  \"type\": \"Gurme\",\n" +
                "  \"longitude\": \"39.846702\",\n" +
                "  \"latitude\": \"32.745920\"\n" +
                "}, {\n" +
                "  \"type\": \"Gurme\",\n" +
                "  \"longitude\": \"39.839471\",\n" +
                "  \"latitude\": \"32.712539\"\n" +
                "}, {\n" +
                "  \"type\": \"Okul Çocuğu\",\n" +
                "  \"longitude\": \"39.856085\",\n" +
                "  \"latitude\": \"32.756873\",\n" +
                "  \"placeName\": \"Hacettepe Anaokulu\",\n" +
                "  \"placeType\": \"School\"\n" +
                "}, {\n" +
                "  \"type\": \"Gurme\",\n" +
                "  \"longitude\": \"39.887765\",\n" +
                "  \"latitude\": \"32.723367\"\n" +
                "}]";
    }

    public String getPersons() {
        return "[{\n" +
                "  \"firstName\": \"Kenneth\",\n" +
                "  \"lastName\": \"Harvey\",\n" +
                "  \"email\": \"kharvey0@jigsy.com\",\n" +
                "  \"gender\": \"Male\",\n" +
                "  \"password\": 1234\n" +
                "}, {\n" +
                "  \"firstName\": \"Harold\",\n" +
                "  \"lastName\": \"Burns\",\n" +
                "  \"email\": \"hburns1@ehow.com\",\n" +
                "  \"gender\": \"Male\",\n" +
                "  \"password\": 1234\n" +
                "}, {\n" +
                "  \"firstName\": \"Irene\",\n" +
                "  \"lastName\": \"Perkins\",\n" +
                "  \"email\": \"iperkins2@storify.com\",\n" +
                "  \"gender\": \"Female\",\n" +
                "  \"password\": 1234\n" +
                "}, {\n" +
                "  \"firstName\": \"Roy\",\n" +
                "  \"lastName\": \"Vasquez\",\n" +
                "  \"email\": \"rvasquez3@blogtalkradio.com\",\n" +
                "  \"gender\": \"Male\",\n" +
                "  \"password\": 1234\n" +
                "}, {\n" +
                "  \"firstName\": \"Karen\",\n" +
                "  \"lastName\": \"Reynolds\",\n" +
                "  \"email\": \"kreynolds4@typepad.com\",\n" +
                "  \"gender\": \"Female\",\n" +
                "  \"password\": 1234\n" +
                "}, {\n" +
                "  \"firstName\": \"Carol\",\n" +
                "  \"lastName\": \"Burke\",\n" +
                "  \"email\": \"cburke5@liveinternet.ru\",\n" +
                "  \"gender\": \"Female\",\n" +
                "  \"password\": 1234\n" +
                "}, {\n" +
                "  \"firstName\": \"Carl\",\n" +
                "  \"lastName\": \"Stephens\",\n" +
                "  \"email\": \"cstephens6@sakura.ne.jp\",\n" +
                "  \"gender\": \"Male\",\n" +
                "  \"password\": 1234\n" +
                "}, {\n" +
                "  \"firstName\": \"Albert\",\n" +
                "  \"lastName\": \"Cook\",\n" +
                "  \"email\": \"acook7@apple.com\",\n" +
                "  \"gender\": \"Male\",\n" +
                "  \"password\": 1234\n" +
                "}, {\n" +
                "  \"firstName\": \"Frances\",\n" +
                "  \"lastName\": \"Martin\",\n" +
                "  \"email\": \"fmartin8@fda.gov\",\n" +
                "  \"gender\": \"Female\",\n" +
                "  \"password\": 1234\n" +
                "}, {\n" +
                "  \"firstName\": \"Judy\",\n" +
                "  \"lastName\": \"Stewart\",\n" +
                "  \"email\": \"jstewart9@nba.com\",\n" +
                "  \"gender\": \"Female\",\n" +
                "  \"password\": 1234\n" +
                "}, {\n" +
                "  \"firstName\": \"Christina\",\n" +
                "  \"lastName\": \"Armstrong\",\n" +
                "  \"email\": \"carmstronga@domainmarket.com\",\n" +
                "  \"gender\": \"Female\",\n" +
                "  \"password\": 1234\n" +
                "}, {\n" +
                "  \"firstName\": \"Michael\",\n" +
                "  \"lastName\": \"Walker\",\n" +
                "  \"email\": \"mwalkerb@miitbeian.gov.cn\",\n" +
                "  \"gender\": \"Male\",\n" +
                "  \"password\": 1234\n" +
                "}, {\n" +
                "  \"firstName\": \"Eugene\",\n" +
                "  \"lastName\": \"Alvarez\",\n" +
                "  \"email\": \"ealvarezc@google.co.jp\",\n" +
                "  \"gender\": \"Male\",\n" +
                "  \"password\": 1234\n" +
                "}, {\n" +
                "  \"firstName\": \"Judy\",\n" +
                "  \"lastName\": \"Moreno\",\n" +
                "  \"email\": \"jmorenod@barnesandnoble.com\",\n" +
                "  \"gender\": \"Female\",\n" +
                "  \"password\": 1234\n" +
                "}, {\n" +
                "  \"firstName\": \"Mark\",\n" +
                "  \"lastName\": \"Henderson\",\n" +
                "  \"email\": \"mhendersone@nih.gov\",\n" +
                "  \"gender\": \"Male\",\n" +
                "  \"password\": 1234\n" +
                "}, {\n" +
                "  \"firstName\": \"Wayne\",\n" +
                "  \"lastName\": \"Smith\",\n" +
                "  \"email\": \"wsmithf@mayoclinic.com\",\n" +
                "  \"gender\": \"Male\",\n" +
                "  \"password\": 1234\n" +
                "}, {\n" +
                "  \"firstName\": \"Ruth\",\n" +
                "  \"lastName\": \"Hill\",\n" +
                "  \"email\": \"rhillg@sphinn.com\",\n" +
                "  \"gender\": \"Female\",\n" +
                "  \"password\": 1234\n" +
                "}, {\n" +
                "  \"firstName\": \"Joshua\",\n" +
                "  \"lastName\": \"Gray\",\n" +
                "  \"email\": \"jgrayh@tuttocitta.it\",\n" +
                "  \"gender\": \"Male\",\n" +
                "  \"password\": 1234\n" +
                "}, {\n" +
                "  \"firstName\": \"Eric\",\n" +
                "  \"lastName\": \"Edwards\",\n" +
                "  \"email\": \"eedwardsi@cam.ac.uk\",\n" +
                "  \"gender\": \"Male\",\n" +
                "  \"password\": 1234\n" +
                "}, {\n" +
                "  \"firstName\": \"Ralph\",\n" +
                "  \"lastName\": \"Gonzales\",\n" +
                "  \"email\": \"rgonzalesj@rediff.com\",\n" +
                "  \"gender\": \"Male\",\n" +
                "  \"password\": 1234\n" +
                "}, {\n" +
                "  \"firstName\": \"Brian\",\n" +
                "  \"lastName\": \"Reynolds\",\n" +
                "  \"email\": \"breynoldsk@microsoft.com\",\n" +
                "  \"gender\": \"Male\",\n" +
                "  \"password\": 1234\n" +
                "}, {\n" +
                "  \"firstName\": \"Howard\",\n" +
                "  \"lastName\": \"Sims\",\n" +
                "  \"email\": \"hsimsl@illinois.edu\",\n" +
                "  \"gender\": \"Male\",\n" +
                "  \"password\": 1234\n" +
                "}, {\n" +
                "  \"firstName\": \"Stephen\",\n" +
                "  \"lastName\": \"Kelley\",\n" +
                "  \"email\": \"skelleym@wikispaces.com\",\n" +
                "  \"gender\": \"Male\",\n" +
                "  \"password\": 1234\n" +
                "}, {\n" +
                "  \"firstName\": \"Linda\",\n" +
                "  \"lastName\": \"Fuller\",\n" +
                "  \"email\": \"lfullern@cloudflare.com\",\n" +
                "  \"gender\": \"Female\",\n" +
                "  \"password\": 1234\n" +
                "}, {\n" +
                "  \"firstName\": \"Donald\",\n" +
                "  \"lastName\": \"Andrews\",\n" +
                "  \"email\": \"dandrewso@g.co\",\n" +
                "  \"gender\": \"Male\",\n" +
                "  \"password\": 1234\n" +
                "}, {\n" +
                "  \"firstName\": \"Ruby\",\n" +
                "  \"lastName\": \"Carroll\",\n" +
                "  \"email\": \"rcarrollp@deviantart.com\",\n" +
                "  \"gender\": \"Female\",\n" +
                "  \"password\": 1234\n" +
                "}, {\n" +
                "  \"firstName\": \"Diane\",\n" +
                "  \"lastName\": \"Mccoy\",\n" +
                "  \"email\": \"dmccoyq@qq.com\",\n" +
                "  \"gender\": \"Female\",\n" +
                "  \"password\": 1234\n" +
                "}, {\n" +
                "  \"firstName\": \"Lori\",\n" +
                "  \"lastName\": \"Baker\",\n" +
                "  \"email\": \"lbakerr@nationalgeographic.com\",\n" +
                "  \"gender\": \"Female\",\n" +
                "  \"password\": 1234\n" +
                "}, {\n" +
                "  \"firstName\": \"Amy\",\n" +
                "  \"lastName\": \"Garcia\",\n" +
                "  \"email\": \"agarcias@w3.org\",\n" +
                "  \"gender\": \"Female\",\n" +
                "  \"password\": 1234\n" +
                "}, {\n" +
                "  \"firstName\": \"Billy\",\n" +
                "  \"lastName\": \"Schmidt\",\n" +
                "  \"email\": \"bschmidtt@icio.us\",\n" +
                "  \"gender\": \"Male\",\n" +
                "  \"password\": 1234\n" +
                "}]";
    }
}
