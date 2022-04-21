package ru.itis.threedportalserver.services;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

public class UtilsService {

    public static String hashSHA256(String stringToHash) {
        return Hashing.sha256().hashString(stringToHash, StandardCharsets.UTF_8).toString();
    }
}
