#include <iostream>
#include <string>
#include "ReportsService.h"

int main() {
    ReportsService reportsService = ReportsService();
    std::cout << "Please enter the full path to the input file:";
    std::string input;
    std::cin >> input;
    std::cout << "Please enter the full path to the output file:";
    std::string output;
    std::cin >> output;
    reportsService.createReport(input, output);
    return 0;
}
