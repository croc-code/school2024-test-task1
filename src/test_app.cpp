#include <iostream>

#include <nlohmann/json.hpp>

#include <order.hpp>
#include <order_analytics.hpp>

int main() {
    OrderAnalytics orders("format.json");
    std::cout << orders.CreateMonthReport() << std::endl;
    system("pause");
}