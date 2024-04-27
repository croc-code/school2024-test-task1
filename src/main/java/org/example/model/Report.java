package org.example.model;

import java.util.List;

/**
 * Represents the report about orders
 * @param months list of months when the order value was maxed out
 */
public record Report(List<String> months) {
}
