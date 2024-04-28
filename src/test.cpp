#include <order.hpp>
#include <order_analytics.hpp>

int main() {
    OrderAnalytics orders("format.json");
    orders.CreateMonthReport();
    return 0;
}