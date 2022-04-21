package ru.itis.threedportalserver;

import org.apache.commons.lang3.SystemUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.itis.threedportalserver.repositories.BucketFileRepositoryImpl;

@SpringBootApplication
public class ThreedportalserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThreedportalserverApplication.class, args);
    }

}
