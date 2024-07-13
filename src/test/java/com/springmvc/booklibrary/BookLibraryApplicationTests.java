package com.springmvc.booklibrary;

import com.springmvc.booklibrary.annotations.Mapping;
import com.springmvc.booklibrary.models.Auteur;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BookLibraryApplicationTests {

    @Test
    void contextLoads() {
        Auteur auteur = new Auteur();
        System.out.println(auteur.getClass().getAnnotation(Mapping.class).table_name());
    }

}
