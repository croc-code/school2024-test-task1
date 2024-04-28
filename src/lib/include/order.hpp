#ifndef _ORDER_HPP_
#define _ORDER_HPP_

#include <string>

/*!
 * @brief Class for orders
 */
class Order {
public:
    Order();

    Order(const std::string& user_id,
          const std::string& time,
          const std::string& status,
          const std::string& total
    );

    ~Order();

    /*!
     * Returns the month name of a current order
     *
     * @return The month name string
     */
    const std::string GetMonthName() const;

    const std::string& GetDate() const { return this->ordered_at_; }
    const std::string& GetStatus() const { return this->status_; }
    const std::string& GetTotal() const { return this->total_; }

private:
    const std::string user_id_{""};
    const std::string ordered_at_{""};
    const std::string status_{""};
    const std::string total_{""};
};

#endif //_ORDER_HPP_