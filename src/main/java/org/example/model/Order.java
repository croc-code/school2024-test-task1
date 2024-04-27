package org.example.model;

import java.util.Date;

/**
 * Represents an entity for order
 * @param user_id  id of user who make order
 * @param ordered_at date the order was placed
 * @param status status of order
 * @param total order amount
 */
public record Order(String user_id, Date ordered_at, Status status, double total) {
}
