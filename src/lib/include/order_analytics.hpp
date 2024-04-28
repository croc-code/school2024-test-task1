#ifndef _ORDER_ANALYTICS_HPP_
#define _ORDER_ANALYTICS_HPP_

#include <string>
#include <vector>

#include <nlohmann/json.hpp>

#include <order.hpp>

using json = nlohmann::ordered_json;

/*!
 * @brief Class for orders statistics
 */
class OrderAnalytics {
public:
    OrderAnalytics();
    OrderAnalytics(const std::string& filename);
    ~OrderAnalytics();

    /*!
     * Analyzes only completed orders
     * and returns months with the maximum users' spending
     *
     * @return List of months with the greatest expenses
     */
    const std::vector<std::string> GetMaxSpendingMonth() const;

    /*!
     * Creates a month report for the current orders file
     */
    const json CreateMonthReport() const;

private:
    /*!
     * Reads and return all orders from a given JSON file
     *
     * @param filename
     * @return List of all orders
     */
    const std::vector<Order> ReadOrdersFile(const std::string& filename);

private:
    std::vector<Order> orders_;

};

#endif // _ORDER_ANALYTICS_HPP_