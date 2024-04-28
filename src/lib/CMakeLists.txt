find_package(nlohmann_json REQUIRED)
find_package(date REQUIRED)

add_library(order order.cpp include/order.hpp)
target_link_libraries(order PRIVATE date::date)

set_property(TARGET order PROPERTY CXX_STANDARD 11)

target_include_directories(order PUBLIC
    $<BUILD_INTERFACE:${CMAKE_CURRENT_SOURCE_DIR}/include>
    $<INSTALL_INTERFACE:include>
)

add_library(order_analytics order_analytics.cpp include/order_analytics.hpp)
target_link_libraries(order_analytics PRIVATE date::date nlohmann_json::nlohmann_json order)

set_property(TARGET order_analytics PROPERTY CXX_STANDARD 11)

target_include_directories(order_analytics PUBLIC
        $<BUILD_INTERFACE:${CMAKE_CURRENT_SOURCE_DIR}/include>
        $<INSTALL_INTERFACE:include>
)

install(TARGETS order order_analytics)