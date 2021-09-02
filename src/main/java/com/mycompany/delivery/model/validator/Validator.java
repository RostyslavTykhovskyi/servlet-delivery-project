package com.mycompany.delivery.model.validator;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Validator {
    public static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z]\\w{4,19}$");
    public static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w]+(?:\\.[\\w]+)*@(?:[a-zA-Z0-9]+\\.)+[a-zA-Z]{2,6}$");
    public static final Pattern PASSWORD_PATTERN = Pattern.compile("^[a-zA-Z0-9_~!@#$%^&*(){};:,.<>?]{5,20}$");

    public static final double MIN_DIMENSION = 0.01;
    public static final double MAX_DIMENSION = 3.00;
    public static final double MIN_WEIGHT = 0.01;
    public static final double MAX_WEIGHT = 1000.00;
    public static final int MIN_DAYS_AFTER_ORDER = 3;
    public static final int MAX_DAYS_AFTER_ORDER = 30;

    public static Map<String, String> validateUserData(String username, String email, String password) {
        Map<String, String> errors = new HashMap<>();

        if (!USERNAME_PATTERN.matcher(username).matches()) {
            errors.put("username", "error");
        }

        if (!EMAIL_PATTERN.matcher(email).matches()) {
            errors.put("email", "error");
        }

        if (!PASSWORD_PATTERN.matcher(password).matches()) {
            errors.put("password", "error");
        }

        return errors;
    }

    public static Map<String, String> validateOrderData(String lengthString, String widthString, String heightString,
                                                        String weightString, String address, String deliveryDate) {
        Map<String, String> errors = new HashMap<>();

        try {
            double length = Double.parseDouble(lengthString);
            if (length < MIN_DIMENSION || length > MAX_DIMENSION) {
                errors.put("length", "error");
            }
        } catch (NumberFormatException ex) {
            errors.put("length", "error");
        }

        try {
            double width = Double.parseDouble(widthString);
            if (width < MIN_DIMENSION || width > MAX_DIMENSION) {
                errors.put("width", "error");
            }
        } catch (NumberFormatException ex) {
            errors.put("width", "error");
        }

        try {
            double height = Double.parseDouble(heightString);
            if (height < MIN_DIMENSION || height > MAX_DIMENSION) {
                errors.put("height", "error");
            }
        } catch (NumberFormatException ex) {
            errors.put("height", "error");
        }

        try {
            double weight = Double.parseDouble(weightString);
            if (weight < MIN_WEIGHT || weight > MAX_WEIGHT) {
                errors.put("weight", "error");
            }
        } catch (NumberFormatException ex) {
            errors.put("weight", "error");
        }

        if (address == null || address.trim().isEmpty()) {
            errors.put("address", "error");
        }

        try {
            LocalDate date = LocalDate.parse(deliveryDate);
            if (date.isBefore(LocalDate.now().plusDays(MIN_DAYS_AFTER_ORDER)) ||
                    date.isAfter(LocalDate.now().plusDays(MAX_DAYS_AFTER_ORDER))) {
                errors.put("deliveryDate", "error");
            }
        } catch (DateTimeParseException ex) {
            errors.put("deliveryDate", "error");
        }

        return errors;
    }

    public static Map<String, String> validateOrderData(String lengthString, String widthString, String heightString,
                                                        String weightString) {
        Map<String, String> errors = new HashMap<>();

        try {
            double length = Double.parseDouble(lengthString);
            if (length < MIN_DIMENSION || length > MAX_DIMENSION) {
                errors.put("length", "error");
            }
        } catch (NumberFormatException ex) {
            errors.put("length", "error");
        }

        try {
            double width = Double.parseDouble(widthString);
            if (width < MIN_DIMENSION || width > MAX_DIMENSION) {
                errors.put("width", "error");
            }
        } catch (NumberFormatException ex) {
            errors.put("width", "error");
        }

        try {
            double height = Double.parseDouble(heightString);
            if (height < MIN_DIMENSION || height > MAX_DIMENSION) {
                errors.put("height", "error");
            }
        } catch (NumberFormatException ex) {
            errors.put("height", "error");
        }

        try {
            double weight = Double.parseDouble(weightString);
            if (weight < MIN_WEIGHT || weight > MAX_WEIGHT) {
                errors.put("weight", "error");
            }
        } catch (NumberFormatException ex) {
            errors.put("weight", "error");
        }

        return errors;
    }
}
