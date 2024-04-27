#ifndef TASK1_HPP
#define TASK1_HPP

#include <algorithm>
#include <cctype>
#include <ctime>
#include <fstream>
#include <iomanip>
#include <iostream>
#include <map>
#include <string>
#include <string_view>
#include <vector>

struct Order {
    std::string user_id;
    std::string ordered_at;
    std::string status;
    double total;
};
std::string getContent(const std::string &);
std::vector<Order> parseJson(const std::string &);
std::vector<int> findMonths(const std::vector<Order> &);
void printResult(const std::vector<int> &);
void createJson(const std::string&, const std::vector<int>&);

#endif // TASK1_HPP
