#include <iostream>

#include <nlohmann/json.hpp>

#include <order.hpp>
#include <order_analytics.hpp>

int main(int argc, char* argv[]) {
    OrderAnalytics orders("input.json");
    std::cout << orders.CreateMonthReport() << std::endl;
    system("pause");
}