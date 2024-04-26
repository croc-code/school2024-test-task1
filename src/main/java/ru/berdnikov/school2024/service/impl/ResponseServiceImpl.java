package ru.berdnikov.school2024.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.berdnikov.school2024.service.ResponseService;

@Service
public class ResponseServiceImpl implements ResponseService<String> {
    @Override
    public ResponseEntity<?> successResponse(String orders) {
        return ResponseEntity.ok(orders);
    }

    @Override
    public ResponseEntity<?> errorResponse(String error) {
        return ResponseEntity.badRequest().body(error);
    }
}
