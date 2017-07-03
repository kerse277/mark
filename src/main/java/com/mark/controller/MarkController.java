package com.mark.controller;

import com.mark.business.MarkBS;
import com.mark.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("mark")
public class MarkController {

    @Autowired
    MarkBS markBS;

    @PostMapping("addmark")
    @ResponseBody
    public String addMark(@RequestBody MarkAddObject markAddObject){
        return markBS.addMark(markAddObject);
    }


    @PostMapping("collectMark")
    @ResponseBody
    public String collectMark(@RequestBody MarkCollectObject markCollectObject){
        return markBS.collectMark(markCollectObject);
    }

    @GetMapping("marksmyloc")
    @ResponseBody
    public List<Mark> myLocationMarks(@RequestParam("long") String longitude,@RequestParam("lat") String latitude){
        return markBS.myLocationMarks(longitude,latitude);
    }

    @GetMapping("byplace")
    @ResponseBody
    public List<Mark> marksByPlace(@RequestParam("placeName") String placeName){
        return markBS.marksByPlace(placeName);
    }

    @GetMapping("nearbyplace")
    @ResponseBody
    public List<Place> nearByPlace(@RequestParam("long") String longitude,@RequestParam("lat") String latitude){
        return markBS.nearByPlace(longitude,latitude);
    }

    @GetMapping("mymarks")
    @ResponseBody
    public List<Mark> myMarks(@RequestParam("authtoken") String authToken){
        return markBS.myMarks(authToken);
    }

    @GetMapping("mylivemarks")
    @ResponseBody
    public List<Mark> myLiveMarks(@RequestParam("authtoken") String authToken){
        return markBS.myLiveMarks(authToken);
    }

    @GetMapping("markowner")
    @ResponseBody
    public CustomPerson markOwner(@RequestParam("markid") String markid){
        return markBS.markOwner(markid);

    }


}
