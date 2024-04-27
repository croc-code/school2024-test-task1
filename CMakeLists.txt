cmake_minimum_required(VERSION 3.10)
project(task1)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)
set(CMAKE_CXX_EXTENSIONS OFF)

# Add executable
add_executable(task1
    src/task1.cpp
    src/task1_functions.cpp
)

# Specify include directories
target_include_directories(task1 PRIVATE include)

# Set the output directory for the executable
set(EXECUTABLE_OUTPUT_PATH ${CMAKE_SOURCE_DIR}/src)

