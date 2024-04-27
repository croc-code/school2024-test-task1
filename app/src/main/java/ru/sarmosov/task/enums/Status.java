package ru.sarmosov.task.enums;

public enum Status {


    COMPLETED, CANCELED, DELIVERY, CREATED;

    public static Status fromString(String status) {
        return switch (status) {
            case "COMPLETED" -> COMPLETED;
            case "CANCELED" -> CANCELED;
            case "DELIVERY" -> DELIVERY;
            case "CREATED" -> CREATED;
            default -> throw new IllegalArgumentException("Unknown status: " + status);
        };
    }
}
