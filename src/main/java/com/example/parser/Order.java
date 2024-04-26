package com.example.parser;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private String user_id;
    private Date ordered_at;
    private String status;
    private double total;
}
