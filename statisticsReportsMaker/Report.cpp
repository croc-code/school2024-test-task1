#include "Report.h"

int Report::getSum(int n) {
    return monthInfos[n].ordersSum;
}

std::string Report::getMonthName(int n) {
    return monthInfos[n].monthName;
}


Report::Report(nlohmann::json j) {
    fillInitialValues();
    for (const auto &item: j) {
        if (item["status"] == "COMPLETED") {
            char *endptr;
            std::string sumStr = (item["total"]);
            double value = strtod(sumStr.c_str(), &endptr);
            if (*endptr) throw std::invalid_argument("order sum cannot be read");

            std::string dateStr = item["ordered_at"];
            std::string monthStr = dateStr.substr(5, 2);
            int month = std::stoi(monthStr);

            this->addToSumByMonth(value, month);
        }
    }
}


void Report::fillInitialValues() {
    monthInfos[1].monthName = "january";
    monthInfos[2].monthName = "february";
    monthInfos[3].monthName = "march";
    monthInfos[4].monthName = "april";
    monthInfos[5].monthName = "may";
    monthInfos[6].monthName = "june";
    monthInfos[7].monthName = "july";
    monthInfos[8].monthName = "august";
    monthInfos[9].monthName = "september";
    monthInfos[10].monthName = "october";
    monthInfos[11].monthName = "november";
    monthInfos[12].monthName = "december";

    for (int i = 1; i <= 12; i++) {
        monthInfos[i].ordersSum = 0;
    }
}


void Report::addToSumByMonth(double sum, int n) {
    monthInfos[n].ordersSum += sum;
}