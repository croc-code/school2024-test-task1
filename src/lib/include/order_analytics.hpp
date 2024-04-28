#ifndef _ORDER_ANALYTICS_HPP_
#define _ORDER_ANALYTICS_HPP_

#include <string>
#include <vector>

#include <order.hpp>

class OrderAnalytics {
public:
    OrderAnalytics();
    OrderAnalytics(const std::string& filename);
    ~OrderAnalytics();

    const std::vector<std::string> GetMaxSpendingMonth() const;
    const void CreateMonthReport() const;

private:
    const std::vector<Order> ReadOrdersFile(const std::string& filename);

private:
    std::vector<Order> orders_;

};

#endif // _ORDER_ANALYTICS_HPP_