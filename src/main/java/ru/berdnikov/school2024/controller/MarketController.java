package ru.berdnikov.school2024.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.berdnikov.school2024.service.MarketControllerService;

//Автоматом генерируется json
@RestController
public class MarketController {
    private final MarketControllerService marketControllerService;

    @Autowired
    public MarketController(MarketControllerService marketControllerService) {
        this.marketControllerService = marketControllerService;
    }

    //Начало
    @GetMapping("/start")
    public ResponseEntity<?> getMaxTotalMonth() {
        return marketControllerService.calculate();
    }
}
