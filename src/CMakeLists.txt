add_subdirectory(lib)

add_executable(test_app test_app.cpp)
target_link_libraries(test_app order order_analytics)