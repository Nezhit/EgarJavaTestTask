package com.example.EdgarTask.controllers;
import com.example.EdgarTask.classes.MyRequestData;
import com.example.EdgarTask.classes.Transport;
import com.example.EdgarTask.repositories.transportRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
public class IndexController {

    private final transportRepo tsRepo;

    @Autowired
    public IndexController(transportRepo tsRepo) {
        this.tsRepo = tsRepo;
    }
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("message", "Привет, мир!");
        return "greeting"; // Предполагается, что у вас есть файл "index.html" в папке "WEB-INF/views/"
    }
    @GetMapping("/intro")
    public String greeting(Model model) {
        model.addAttribute("message", "Привет, мир!");
        return "intro";
    }
    @GetMapping("save")
    public String gotosave(Model model) {

        return "save";
    }
    @GetMapping("error")
    public String gotoerror(Model model) {

        return "intro";
    }
    @GetMapping("search")
    public String gotosearch(Model model) {

        return "search";
    }
    @PostMapping("/save")
    public String saveData(@RequestParam("field1") Long field1,
                           @RequestParam("field2") String field2,
                           @RequestParam("field3") String field3,
                           @RequestParam("field4") String field4,
                           @RequestParam("field5") String field5,
                           @RequestParam("field6") int field6,
                           @RequestParam("field7") boolean field7) {
        if(field1==0||field2.isEmpty()||field3.isEmpty()||field4.isEmpty()||field5.isEmpty()||field6==0){
            return "intro";
        }
        // Проверка наличия записи с тем же номером и идентификатором в базе данных
        if (!tsRepo.existsByIDOrNumber(field1, field5)) {
            // Если запись не существует, добавляем в базу данных
            Transport ts = new Transport(field1, field2, field3, field4, field5, field6, field7);
            tsRepo.save(ts);
        } else {
            return "intro";
            // Если запись уже существует, выполните нужные действия
            // например, показать сообщение об ошибке или перенаправить пользователя на другую страницу
            // ...
        }

        // Верните страницу или перенаправьте пользователя на другую страницу после сохранения
        return "redirect:/"; // Перенаправление на главную страницу после сохранения
    }




    @GetMapping("/table")
    public String showTable(Model modell) {

        List<Transport> transportList=tsRepo.findAll();
        modell.addAttribute("transportList", transportList);
        return "table";
    }
    @PostMapping("/search")
    public String searchData(Model modell, @RequestParam("field4") String field4,
                             @RequestParam("field6") int field6) {


        List<Transport> transportList = tsRepo.findByCategoryAndYear(field4,field6);
        modell.addAttribute("transportList", transportList);


        return "search";
    }
    @PostMapping("/table")
    public  String handlePostRequest(Model modell,@RequestBody MyRequestData requestData) {


        String inputField = requestData.getInputField();
        String buttonId = requestData.getButtonId();
        List<Transport> transportList = null;
        switch (buttonId) {
            case "brand":
                transportList= tsRepo.findByBrand(inputField);
                break;
            case "model":
                transportList=tsRepo.findByModel(inputField);
                break;
            case "category":
                transportList= tsRepo.findByCategory(inputField);
                break;
            case "number":
                transportList= tsRepo.findByNumber(inputField);
                break;
            case "year":
                transportList=tsRepo.findByYear(Integer.parseInt(inputField));
                break;
            case "istrailer":
                transportList=tsRepo.findByIsTrailer(Boolean.parseBoolean(inputField));
                break;
            default:
                System.out.println("Неизвестный фрукт");
                break;
        }
        modell.addAttribute("transportList", transportList);

        return "table";
    }




}
