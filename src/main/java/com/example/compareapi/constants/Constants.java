package com.example.compareapi.constants;

public class Constants {

    /*
    * REGEX STRINGS
    * */

    // Matches UUIDs like: 123e4567-e89b-12d3-a456-426614174000
    public static final String UUID_REGEX =
            "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$";

    // Matches URLs like: http://example.com, https://example.com/path?x=1
    public static final String URL_REGEX =
            "^(https?://)([\\w.-]+)(:[0-9]{1,5})?(/[\\w./?%&=-]*)?$";

    // ✅ Requires the string to contain at least 3 digits (0–9)
    public static final String AT_LEAST_THREE_DIGITS = "^(?:.*\\d.*){3,}.*$";

    // ✅ Requires the string to contain at least 1 digits (0–9)
    public static final String AT_LEAST_ONE_DIGIT = "^(?:.*\\d.*){1,}.*$";

    // ✅ Requires at least 3 alphabetic characters
    public static final String AT_LEAST_THREE_LETTERS = "^(?:.*[A-Za-z].*){3,}.*$";

    /*
     * MESSAGES STRINGS
     * */

    public static final String URL_BAD_FORMAT = "URL BAD FORMAT";
    public static final String UUID_BAD_FORMAT = "UUID BAD FORMAT";
    public static final String RESOURCE_NOT_FOUND = "RESOURCE NOT FOUND";
    public static final String NAME_BAD_FORMAT = "Invalid name (must contain at least 3 letters).";
    public static final String DESCRIPTION_BAD_FORMAT = "Invalid description (must have at least 3 characters).";
    public static final String PRICE_BAD_FORMAT = "Invalid price format (must be a positive number with up to 1 digit).";
    public static final String RATING_BAD_FORMAT = "Invalid rating (must be between 0 and 5).";
    public static final String SPECIFICATIONS_BAD_FORMAT = "Specifications cannot be empty.";
    public static final String TYPE_BAD_FORMAT = "Invalid product type.";
    public static final String INVALID_COMPARISON = "Invalid comparison, must be same product type to be compared.";

}
