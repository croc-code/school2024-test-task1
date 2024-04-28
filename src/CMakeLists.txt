add_subdirectory(lib)

add_executable(test_app test_app.cpp)
target_link_libraries(test_app nlohmann_json::nlohmann_json order order_analytics)