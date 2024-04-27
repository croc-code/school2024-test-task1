#include <nlohmann/json.hpp>
#include <date/date.h>

#include <fstream>
#include <iostream>
#include <sstream>
#include <string>

using json = nlohmann::ordered_json;

class Order {
private:
    const std::string user_id_{""};
    const std::string ordered_at_{""};
    const std::string status_{""};
    const std::string total_{""};

public:
    Order();

    Order(const std::string& user_id,
          const std::string& time,
          const std::string& status,
          const std::string& total
          );

    ~Order();

    const std::string GetMonth() const;
    const std::string GetDate() const { return this->ordered_at_; }
    const std::string GetStatus() const { return this->status_; }
    const std::string GetTotal() const { return this->total_; }
};

class OrderAnalytics {
private:
    std::vector<Order> orders_;

public:
    OrderAnalytics();
    OrderAnalytics(const std::string& filename);
    OrderAnalytics(const std::vector<Order>& order_list);
    ~OrderAnalytics();

    const std::vector<std::string> GetMaxSpendingMonth() const;
    const void CreateMonthReport() const;
};

const std::vector<Order> ReadOrdersFile(const std::string& filename) {
    std::ifstream orders_file;
    json data;

    try {
        orders_file.open(filename);
        if (!orders_file.is_open()) {
            throw std::runtime_error("Error: Unable to open file '" + filename + "'");
        }
        data = json::parse(orders_file);
    } catch (const std::exception& e) {
        std::cerr << e.what() << std::endl;
        throw std::ifstream::failbit;
    }

    orders_file.close();

    std::vector<Order> orders_list;
    for (auto it = data.begin(); it != data.end(); it += 1) {
        Order current_order = Order((*it)["user_id"].get<std::string>(),
                                    (*it)["ordered_at"].get<std::string>(),
                                    (*it)["status"].get<std::string>(),
                                    (*it)["total"].get<std::string>());
        orders_list.push_back(current_order);
    }

    return orders_list;
}

OrderAnalytics::~OrderAnalytics() = default;

OrderAnalytics::OrderAnalytics(const std::string &filename)
    : orders_(ReadOrdersFile(filename))
{
};

OrderAnalytics::OrderAnalytics(const std::vector<Order>& order_list)
    : orders_{order_list}
{
};

const std::vector<std::string> OrderAnalytics::GetMaxSpendingMonth() const {
    std::map<std::string, double> month_total = {
            {"january", 0}, {"february", 0}, {"march", 0}, {"april", 0},
            {"may", 0}, {"june", 0}, {"july", 0}, {"august", 0},
            {"september", 0}, {"october", 0}, {"november", 0}, {"december", 0}
    };

    double max_amount = 0;

    for (Order order : orders_) {
        std::string current_month = order.GetMonth();

        if (order.GetStatus() == "COMPLETED") {
            month_total[current_month] += std::stod(order.GetTotal());
            if (month_total[current_month] > max_amount) {
                max_amount = month_total[current_month];
            }
        }
    }

    std::vector<std::string> result;
    for (auto it : month_total) {
        const std::string& month = it.first;
        const double& count = it.second;

        if (count == max_amount) {
            result.push_back(month);
        }
    }

    return result;
}

const void OrderAnalytics::CreateMonthReport() const {
    std::vector<std::string> max_months = GetMaxSpendingMonth();

    json j_report = {
        {"months", max_months}
    };

    std::cout << j_report;
}

Order::Order() = default;

Order::Order(const std::string& user_id,
             const std::string& time,
             const std::string& status,
             const std::string& total)
      : user_id_{user_id}
      , ordered_at_{time}
      , status_{status}
      , total_{total}
{
};

Order::~Order() = default;

const std::string Order::GetMonth() const {
    std::string date_string = this->GetDate();

    try {
        std::istringstream iss(date_string);
        date::sys_seconds tp;
        iss >> date::parse("%Y-%m-%dT%H:%M:%S", tp);

        std::string month = date::format("%B", tp);
        month[0] = tolower(month[0]);

        return month;
    } catch (const std::exception& e) {
        std::cerr << "Error: " << e.what() << std::endl;
        return "Invalid date format";
    }
}

int main() {
    OrderAnalytics orders("format.json");
    orders.CreateMonthReport();
    return 0;
}