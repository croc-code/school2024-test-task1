cmake_minimum_required(VERSION 3.26)
project(statisticsReportsMaker)

set(CMAKE_CXX_STANDARD 17)

set(JSON_INCLUDE_DIR "C:/Programming/CLion/Libraries/nlohmannJson/include")
include_directories(${JSON_INCLUDE_DIR})

add_executable(statisticsReportsMaker main.cpp
        Report.cpp
        ReportsService.cpp
)
