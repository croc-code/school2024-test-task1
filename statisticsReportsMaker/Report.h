#ifndef STATISTICSREPORTSMAKER_REPORT_H
#define STATISTICSREPORTSMAKER_REPORT_H
#include <nlohmann/json.hpp>

//This class combines all the input data into a single report object, excluding information
//that is not required to generate the output report
class Report {

public:
    //This method returns the sum, that was spent in the n-th month
    int getSum(int n);
    //This method returns the name of the n-th month in english starting with a small letter
    std::string getMonthName(int n);

    //This method construct input report object from the json
    Report(nlohmann::json j) ;


private:
    class MonthInfo {
    public:
        MonthInfo() {}
        std::string monthName;
        long double ordersSum;
    };
    MonthInfo monthInfos[13];

    //This method writes the names of the months and start sum = 0 to the monthInfos. Is used only in the constructor.
    void fillInitialValues();
    //This method adds the sum to the month while creating report object from json. Is used only in the constructor.
    void addToSumByMonth(double sum, int n);

};


#endif //STATISTICSREPORTSMAKER_REPORT_H
