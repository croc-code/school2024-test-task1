cmake_minimum_required(VERSION 3.24)
project(school2024_test_task1)

set(CMAKE_CXX_STANDARD 17)

set(CMAKE_INSTALL_PREFIX ${CMAKE_CURRENT_SOURCE_DIR}/solution)

find_package(nlohmann_json REQUIRED)
find_package(date REQUIRED)

add_executable(${PROJECT_NAME} source.cpp)
target_link_libraries(${PROJECT_NAME} nlohmann_json::nlohmann_json date::date)

install(TARGETS ${PROJECT_NAME} DESTINATION ${CMAKE_INSTALL_PREFIX})

install(DIRECTORY ${CMAKE_CURRENT_SOURCE_DIR}/input/
        DESTINATION ${CMAKE_INSTALL_PREFIX}
        FILES_MATCHING
        PATTERN "*.json")


add_custom_target(install_${PROJECT_NAME}
        ${CMAKE_COMMAND} -DCMAKE_INSTALL_CONFIG_NAME=${CMAKE_BUILD_TYPE}
        -P cmake_install.cmake
)