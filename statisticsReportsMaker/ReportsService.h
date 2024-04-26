#ifndef STATISTICSREPORTSMAKER_REPORTSSERVICE_H
#define STATISTICSREPORTSMAKER_REPORTSSERVICE_H
#include <nlohmann/json.hpp>
#include <string>
#include <fstream>
#include "Report.h"


class ReportsService {
public:
    void createReport(std::string fullFileName, std::string fullOutputFileName);
};


#endif //STATISTICSREPORTSMAKER_REPORTSSERVICE_H
