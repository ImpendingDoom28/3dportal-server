package ru.itis.threedportalserver.contollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.itis.threedportalserver.repositories.BucketFileRepository;
import ru.itis.threedportalserver.repositories.BucketFileRepositoryImpl;
import ru.itis.threedportalserver.services.interfaces.ModelsService;

@Controller
public class IndexController {

    @Autowired
    private ModelsService modelsService;

    @GetMapping
    public String getDocs() {
        return "swagger-ui.html";
    }
}
