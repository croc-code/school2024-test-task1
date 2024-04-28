package org.example;

import org.example.pojo.Order;

public class Main {
    public static void main(String[] args) {
        Parser<Order> parser = new JsonParser();
        System.out.println(parser.doCounting());
    }
}