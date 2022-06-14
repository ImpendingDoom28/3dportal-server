package ru.itis.threedportalserver.services;

import org.springframework.stereotype.Service;
import ru.itis.threedportalserver.services.interfaces.ApiKeyService;

import javax.xml.bind.DatatypeConverter;
import java.security.SecureRandom;

@Service
public class ApiKeyServiceImpl implements ApiKeyService {

    @Override
    public String generateApiKey() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[32];
        random.nextBytes(bytes);
        return DatatypeConverter.printHexBinary(bytes).toLowerCase();
    }
}
