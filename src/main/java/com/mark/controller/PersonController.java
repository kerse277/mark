package com.mark.controller;

import com.mark.business.PersonBS;
import com.mark.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("person")
public class PersonController {

    @Autowired
    PersonBS personBS;

    @PostMapping("addperson")
    @ResponseBody
    public String addPerson(@RequestBody Person person) {
     return personBS.addPerson(person);
    }

    @PostMapping("updateperson")
    @ResponseBody
    public String updatePerson(@RequestBody Person person) {
        return personBS.updatePerson(person);
    }

    @PostMapping("login")
    @ResponseBody
    public Token login(@RequestBody LoginObject loginObject){
        return personBS.login(loginObject);
    }

    @GetMapping("myinfo")
    @ResponseBody
    public CustomPerson myInfo(@RequestParam("authtoken") String authToken) {
        return personBS.myInfo(authToken);
    }

    @GetMapping("getmycollectedmarks")
    @ResponseBody
    public List<CollectMarkAndPersons> getMyCollectMarks(@RequestParam("authtoken") String authToken){
        return personBS.getMyCollectMarks(authToken);
    }
}
