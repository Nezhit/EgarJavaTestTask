package com.example.EdgarTask.repositories;

import com.example.EdgarTask.classes.Transport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface transportRepo extends JpaRepository<Transport, Integer> {
    List<Transport> findByBrandAndModel(String brand, String model);
    List<Transport> findByBrandOrModel(String brand, String model);
    List<Transport> findByCategoryAndYear(String category, int year);
    boolean existsByIDOrNumber(Long ID, String number);

    List<Transport> findByBrand(String brand);
    List<Transport> findByModel(String model);
    List<Transport> findByYear(int year );
    List<Transport> findByNumber(String number );
    List<Transport> findByCategory(String category );
    List<Transport> findByIsTrailer(boolean istrailer );
    List<Transport> findByID(Long ID );


}
