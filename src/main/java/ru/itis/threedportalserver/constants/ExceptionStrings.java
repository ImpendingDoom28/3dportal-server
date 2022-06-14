package ru.itis.threedportalserver.constants;

import lombok.Getter;

@Getter
public class ExceptionStrings {

    public final static String YOUR_EMAIL_PASSWORD_IS_INCORRECT = "Your email/password is incorrect!";

    public static String USER_WITH_EMAIL_ALREADY_EXIST(String email) {
        return "User with email '" + email + "' exist!";
    }

    public static String USER_WITH_EMAIL_DOES_NOT_EXIST(String email) {
        return "User with email '" + email + "' does not exist!";
    }

    public static String USER_WITH_ID_DOES_NOT_EXIST(String id) {
        return "User with id: '" + id + "' does not exist!";
    }

    public final static String BAD_TOKEN = "Bad token";

    public static String INSTRUMENT_WITH_ID_DOES_NOT_EXIST(String id) {
        return "Instrument with id: '" + id + "' does not exist!";
    }
}
