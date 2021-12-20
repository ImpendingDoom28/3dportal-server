package ru.itis.threedportalserver.constants;

import lombok.Getter;

@Getter
public class ExceptionStrings {

    public final static String USER_NOT_FOUND = "Your email/password is incorrect!";

    public static String USER_WITH_EMAIL_ALREADY_EXIST(String email) {
        return "User with email '" + email + "' exist!";
    }
}
