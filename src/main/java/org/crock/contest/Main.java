package org.crock.contest;

import org.crock.contest.config.Configuration;
import org.crock.contest.converter.MonthsToJson;
import org.crock.contest.order.Order;
import org.crock.contest.order.Orders;


import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Order> orders = Configuration.listOrdersReader().readObjectFromJsonFile(args[0]);
        List<List<String>> months = Orders.getMonthsWithMaxTotal(orders);
        var convert = new MonthsToJson(Configuration.gsonInit());
        System.out.println(convert.object2Json(months));
    }
}