package com.wladischlau.app.dto.order;

/**
 * Перечисление, описывающие возможные статусы {@link OrderDTO заказа}.
 * <p>
 * Возможные значения:<br>
 * - {@link OrderStatus#COMPLETED COMPLETED}: Заказ завершен.<br>
 * - {@link OrderStatus#CANCELED CANCELED}: Заказ отменен.<br>
 * - {@link OrderStatus#CREATED CREATED}: Заказ создан, но еще не оплачен.<br>
 * - {@link OrderStatus#DELIVERY DELIVERY}: Заказ создан, оплачен и находится в процессе доставки.
 * </p>
 */
public enum OrderStatus {
    /**
     * Заказ завершён.
     */
    COMPLETED,
    /**
     * Заказ отменён.
     */
    CANCELED,
    /**
     * Заказ создан, но ещё не оплачен.
     */
    CREATED,
    /**
     * Заказ создан, оплачен и находится в процессе доставки
     */
    DELIVERY
}
