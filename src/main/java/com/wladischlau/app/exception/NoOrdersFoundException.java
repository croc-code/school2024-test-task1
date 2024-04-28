package com.wladischlau.app.exception;

/**
 * Исключение, выбрасываемое при отсутствии данных после парсинга JSON-файла.
 */
public class NoOrdersFoundException extends Exception {

    /**
     * Конструктор с указанием сообщения об ошибке.
     *
     * @param message сообщение об ошибке.
     */
    public NoOrdersFoundException(String message) {
        super(message);
    }

    /**
     * Конструктор с указанием сообщения об ошибке и причины исключения.
     *
     * @param message сообщение об ошибке.
     * @param cause   причина исключения.
     */
    public NoOrdersFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
