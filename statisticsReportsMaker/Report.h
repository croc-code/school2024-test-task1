#ifndef STATISTICSREPORTSMAKER_REPORT_H
#define STATISTICSREPORTSMAKER_REPORT_H
#include <nlohmann/json.hpp>

class Report {

public:
    int getSum(int n);
    std::string getMonthName(int n);
    Report(nlohmann::json j) ;


private:
    class MonthInfo {
    public:
        MonthInfo() {}
        std::string monthName;
        long double ordersSum;
    };
    MonthInfo monthInfos[13];
    void fillInitialValues();
    void addToSumByMonth(double sum, int n);

};


#endif //STATISTICSREPORTSMAKER_REPORT_H
