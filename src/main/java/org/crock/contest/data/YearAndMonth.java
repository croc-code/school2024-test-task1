package org.crock.contest.data;

import java.util.Objects;

/**
 * Класс для хранения года и месяца заказа
 *
 * @param year  - порядковый номер года
 * @param month - порядковый номер месяуа
 */
public record YearAndMonth(int year, int month) {

    public YearAndMonth(int year, int month) {
        // валидация
        if (year <= 1900 || month <= 0 || month > 12) {
            throw new IllegalArgumentException("Неверный формат месяца и года");
        }
        this.year = year;
        this.month = month;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        YearAndMonth that = (YearAndMonth) o;
        return year == that.year && month == that.month;
    }

    @Override
    public int hashCode() {
        return Objects.hash(year, month);
    }
}
