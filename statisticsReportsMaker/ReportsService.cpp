#include "ReportsService.h"
void ReportsService::createReport(std::string fullFileName, std::string fullOutputFileName){
    std::ifstream input(fullFileName);
    nlohmann::json j = nlohmann::json::parse(input);


    Report report = Report(j);

    long double max = 0;
    for (int i = 1; i <= 12; i++){
        if (report.getSum(i) >= max){
            max = report.getSum(i);
        }
    }

    nlohmann::json res;

    for (int i = 1; i <= 12; i++){
        if (report.getSum(i) == max){
            res["month"].push_back(report.getMonthName(i));
        }
    }

    std::ofstream output(fullOutputFileName);
    output << res.dump();

    output.close();
    input.close();
}