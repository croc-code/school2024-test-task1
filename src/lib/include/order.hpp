#ifndef _ORDER_HPP_
#define _ORDER_HPP_

#include <string>

class Order {
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

private:
    const std::string user_id_{""};
    const std::string ordered_at_{""};
    const std::string status_{""};
    const std::string total_{""};
};

#endif //_ORDER_HPP_