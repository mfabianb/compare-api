package com.example.compareapi.validators;

import com.example.compareapi.exception.BadFormatException;
import com.example.compareapi.model.Product;
import com.example.compareapi.model.enums.ProductType;

import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

import static com.example.compareapi.constants.Constants.*;

public class Validations {
    public static boolean validateRegexFormat(String regex, String input){
        return Pattern.compile(regex).matcher(input).matches();
    }

    public static void validateInputProduct(Product product){
        validateProductImage(product);//Mandatory
        validateProductId(product);//Mandatory
        validateProductName(product);//Mandatory
        validateProductDescription(product);//Mandatory
        validateProductPrice(product);//Mandatory
        validateProductRating(product);//Mandatory
        validateProductSpecifications(product);//Optional
        validateProductType(product);//Mandatory
    }

    public static void validateProductImage(Product product){
        if(Objects.isNull(product.getImageUrl()) || !Validations.validateRegexFormat(URL_REGEX, product.getImageUrl())){
            throw new BadFormatException(URL_BAD_FORMAT);
        }
    }

    public static void validateProductId(Product product) {
        if (Objects.isNull(product.getId()) || !Validations.validateRegexFormat(UUID_REGEX, product.getId())) {
            throw new BadFormatException(UUID_BAD_FORMAT);
        }
    }

    public static void validateProductName(Product product) {
        if (Objects.isNull(product.getName()) || !Validations.validateRegexFormat(AT_LEAST_THREE_LETTERS, product.getName())) {
            throw new BadFormatException(NAME_BAD_FORMAT);
        }
    }

    public static void validateProductDescription(Product product) {
        if (Objects.isNull(product.getDescription()) || !Validations.validateRegexFormat(AT_LEAST_THREE_LETTERS, product.getDescription())) {
            throw new BadFormatException(DESCRIPTION_BAD_FORMAT);
        }
    }

    public static void validateProductPrice(Product product) {
        if (product.getPrice() <= 0 || !Validations.validateRegexFormat(AT_LEAST_ONE_DIGIT, String.valueOf(product.getPrice()))) {
            throw new BadFormatException(PRICE_BAD_FORMAT);
        }
    }

    public static void validateProductRating(Product product) {
        if ((product.getRating() < 0 || product.getRating() > 5) ||
                !Validations.validateRegexFormat(AT_LEAST_ONE_DIGIT, String.valueOf(product.getRating()))) {
            throw new BadFormatException(RATING_BAD_FORMAT);
        }
    }

    public static void validateProductSpecifications(Product product) {
        Map<String, Object> specs = product.getSpecifications();
        if (Objects.isNull(specs) && specs.isEmpty()) {
            throw new BadFormatException(SPECIFICATIONS_BAD_FORMAT);
        }
    }

    public static void validateProductType(Product product) {
        if (Objects.isNull(product.getType())) {
            throw new BadFormatException(TYPE_BAD_FORMAT);
        }
        try {
            // Ensures it's a valid enum constant
            ProductType.valueOf(product.getType().name());
        } catch (IllegalArgumentException ex) {
            throw new BadFormatException(TYPE_BAD_FORMAT);
        }
    }

}
