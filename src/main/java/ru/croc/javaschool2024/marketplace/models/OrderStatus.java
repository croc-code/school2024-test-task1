package ru.croc.javaschool2024.marketplace.models;

/**
 * COMPLETED - Завершенный заказ
 * CANCELED - Отмененный заказ
 * CREATED - Созданный заказ, еще не оплаченный
 * DELIVERY - Созданный и оплаченный заказ, который доставляется
 */
public enum OrderStatus {
    COMPLETED,
    CANCELED,
    CREATED,
    DELIVERY
}
