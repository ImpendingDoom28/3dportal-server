package ru.itis.threedportalserver.contollers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping
    public String getDocs() {
        return "swagger-ui.html";
    }
}
