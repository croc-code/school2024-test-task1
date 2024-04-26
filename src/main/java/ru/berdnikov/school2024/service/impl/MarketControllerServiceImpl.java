package ru.berdnikov.school2024.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.berdnikov.school2024.service.MarketControllerService;
import ru.berdnikov.school2024.service.OrderService;
import ru.berdnikov.school2024.service.ResponseService;
import ru.berdnikov.school2024.utils.Errors;

@Service
public class MarketControllerServiceImpl implements MarketControllerService {
    private final ResponseService<String> responseService;
    private final OrderService orderService;

    @Autowired
    public MarketControllerServiceImpl(ResponseService<String> responseService, OrderService orderService) {
        this.responseService = responseService;
        this.orderService = orderService;
    }

    @Override
    public ResponseEntity<?> calculate() {
        String response = orderService.getMaxTotalMonthReport();
        if (response.isEmpty()) {
            return responseService.errorResponse(Errors.NOT_RESPONSE);
        }
        return responseService.successResponse(orderService.getMaxTotalMonthReport());
    }
}
