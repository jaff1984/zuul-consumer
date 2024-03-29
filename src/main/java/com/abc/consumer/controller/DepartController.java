package com.abc.consumer.controller;

import com.abc.consumer.bean.Depart;
import com.abc.consumer.service.DepartService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/consumer/depart")
public class DepartController {
    @Autowired
    private DepartService service;

    @PostMapping("/save")
    public boolean saveHandle(@RequestBody Depart depart) {
       return service.saveDepart(depart);
    }

    @DeleteMapping("/del/{id}")
    public boolean deleteHandle(@PathVariable("id") int id) {
        return service.removeDepartById(id);
    }

    @PutMapping("/update")
    public boolean updateHandle(@RequestBody Depart depart) {
        return service.modifyDepart(depart);
    }

    @HystrixCommand(fallbackMethod = "getHystrixHandle")
    @GetMapping("/get/{id}")
    public Depart getHandle(@PathVariable("id") int id) {
        return service.getDepartById(id);
    }

    @HystrixCommand(fallbackMethod = "getTTS")
    @GetMapping("/tts/{id3}")
    public Depart getHandle2(@PathVariable("id3") int id3) {
        return service.getDepartById(id3);
    }

    public Depart getHystrixHandle(@PathVariable("id") int id) {
        Depart depart = new Depart();
        depart.setId(id);
        depart.setName("no this depart-80901");
        return depart;
    }

    public Depart getTTS(@PathVariable("id3") int id) {
        Depart depart = new Depart();
        depart.setId(id);
        depart.setName("this is tts");
        return depart;
    }

    @HystrixCommand(fallbackMethod = "listHandleHystrix")
    @GetMapping("/list")
    public List<Depart> listHandle() {
        return service.listAllDeparts();
    }

    public List<Depart> listHandleHystrix() {
        ArrayList<Depart> departs = new ArrayList<>();
        departs.add(new Depart("no this depart-80902"));
        return departs;
    }

}
