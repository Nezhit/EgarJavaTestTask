package com.example.EdgarTask;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
public class IndexController {

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
    public String saveData(@RequestParam("field1") int field1,
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
        if (!isTransportExists(field1, field5)) {
            // Если запись не существует, добавляем в базу данных
            Transport ts = new Transport(field1, field2, field3, field4, field5, field6, field7);
            saveTransportToDatabase(ts);
        } else {
            return "intro";
            // Если запись уже существует, выполните нужные действия
            // например, показать сообщение об ошибке или перенаправить пользователя на другую страницу
            // ...
        }

        // Верните страницу или перенаправьте пользователя на другую страницу после сохранения
        return "redirect:/"; // Перенаправление на главную страницу после сохранения
    }

    private boolean isTransportExists(int field1, String field5) {
        // Проверка наличия записи с тем же номером и идентификатором в базе данных

        String url = "jdbc:mysql://localhost:3306/edgarts";
        String username = "root";
        String password = "root";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String sql = "SELECT * FROM transport";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                if(Objects.equals(resultSet.getString("number"), field5) ||resultSet.getInt("id")==field1){
                    return true;
                }
            }




        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
        // Верните результат проверки (true - если запись существует, false - если запись не существует)

    }

    private void saveTransportToDatabase(Transport transport) {
        // Логика сохранения данных в базу данных
        // Здесь вы должны выполнить необходимые действия для сохранения объекта Transport в базу данных

        // JDBC URL, имя пользователя и пароль для подключения к базе данных
        String url = "jdbc:mysql://localhost:3306/edgarts";
        String username = "root";
        String password = "root";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            // Подготовленный запрос для вставки данных
            String sql = "INSERT INTO `edgarts`.`transport` (`id`, `brand`, `model`, `category`, `number`, `year`, `istrailer`) VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement statement = connection.prepareStatement(sql);

            // Установка значений параметров запроса
            statement.setInt(1, transport.getID());
            statement.setString(2, transport.getBrand());
            statement.setString(3, transport.getModel());
            statement.setString(4, transport.getCategory());
            statement.setString(5, transport.getNumber());
            statement.setInt(6, transport.getYear());
            statement.setBoolean(7, transport.isTrailer());

            // Выполнение запроса
            statement.executeUpdate();

        } catch (SQLException e) {
            // Обработка исключения
            e.printStackTrace();
        }
    }
    @GetMapping("/table")
    public String showTable(Model modell) {
        String url = "jdbc:mysql://localhost:3306/edgarts";
        String username = "root";
        String password = "root";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String sql = "SELECT * FROM transport";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            List<Transport> transportList = new ArrayList<>();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String brand = resultSet.getString("brand");
                String model = resultSet.getString("model");
                String category = resultSet.getString("category");
                String number = resultSet.getString("number");
                int year = resultSet.getInt("year");
                boolean istrailer = resultSet.getBoolean("istrailer");

                Transport transport = new Transport(id, brand, model, category, number, year, istrailer);
                transportList.add(transport);
            }

            modell.addAttribute("transportList", transportList);

            return "table";
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "error";
    }
    @PostMapping("/search")
    public String searchData(Model modell, @RequestParam("field4") String field4,
                             @RequestParam("field6") int field6) {
        String url = "jdbc:mysql://localhost:3306/edgarts";
        String username = "root";
        String password = "root";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String sql = "SELECT * FROM transport WHERE category = ? AND year = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, field4);
            statement.setInt(2, field6);
            ResultSet resultSet = statement.executeQuery();

            List<Transport> transportList = new ArrayList<>();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String brand = resultSet.getString("brand");
                String model = resultSet.getString("model");
                String category = resultSet.getString("category");
                String number = resultSet.getString("number");
                int year = resultSet.getInt("year");
                boolean istrailer = resultSet.getBoolean("istrailer");

                Transport transport = new Transport(id, brand, model, category, number, year, istrailer);
                transportList.add(transport);
            }

            modell.addAttribute("transportList", transportList);

            return "search";
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "error";
    }
    @PostMapping("/table")
    public  String handlePostRequest(Model modell,@RequestBody MyRequestData requestData) {
        String inputField = requestData.getInputField();
        String buttonId = requestData.getButtonId();
        String url = "jdbc:mysql://localhost:3306/edgarts";
        String username = "root";
        String password = "root";

        // Здесь вы можете обработать запрос в зависимости от значения buttonId
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String sql = "SELECT * FROM transport WHERE " + buttonId + " = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, inputField);

            ResultSet resultSet = statement.executeQuery();

            List<Transport> transportList = new ArrayList<>();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String brand = resultSet.getString("brand");
                String model = resultSet.getString("model");
                String category = resultSet.getString("category");
                String number = resultSet.getString("number");
                int year = resultSet.getInt("year");
                boolean istrailer = resultSet.getBoolean("istrailer");

                Transport transport = new Transport(id, brand, model, category, number, year, istrailer);
                transportList.add(transport);

            }

            modell.addAttribute("transportList", transportList);

            return "table";
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "error";


    }




}
