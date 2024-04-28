#include "order.hpp"

#include <iostream>
#include <sstream>

#include <date/date.h>

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