package com.example.compareapi.model.enums;

/*
* Enum for product types
* Add any type you need
* */
public enum ProductType {
    ELECTRONIC("ELECTRONIC", 1),
    CLOTHING("CLOTHING", 2),
    BOOK("BOOK", 3),
    FURNITURE("FURNITURE", 4),
    OTHER("OTHER", 5);

    public final String type;
    public final Integer id;

    ProductType(String type, Integer id) {
        this.type = type;
        this.id = id;
    }

    public static ProductType getByType(String type) {
        for (ProductType e : values()) {
            if (e.type.equals(type)) {
                return e;
            }
        }
        return null;
    }

    public static ProductType getById(Integer id) {
        for (ProductType e : values()) {
            if (e.id.equals(id)) {
                return e;
            }
        }
        return null;
    }
}
