package ru.berdnikov.school2024.service;

import org.springframework.http.ResponseEntity;

public interface ResponseService<T> {
    ResponseEntity<?> successResponse(T orders);

    ResponseEntity<?> errorResponse(T error);
}
