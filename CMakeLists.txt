cmake_minimum_required(VERSION 3.24)
project(school2024_test_task1)

set(CMAKE_CXX_STANDARD 17)

# set(CMAKE_RUNTIME_OUTPUT_DIRECTORY ${CMAKE_CURRENT_SOURCE_DIR}/bin)
set(CMAKE_INSTALL_PREFIX ${CMAKE_CURRENT_SOURCE_DIR}/solution)

find_package(nlohmann_json REQUIRED)
find_package(date REQUIRED)

find_package(Doxygen)
option(BUILD_DOC "Build documentation" ON)

add_subdirectory(src)

install(TARGETS test_app DESTINATION ${CMAKE_INSTALL_PREFIX})

install(DIRECTORY ${CMAKE_CURRENT_SOURCE_DIR}/input/
        DESTINATION ${CMAKE_INSTALL_PREFIX}
        FILES_MATCHING
        PATTERN "*.json")


add_custom_target(install_${PROJECT_NAME}
        ${CMAKE_COMMAND} -DCMAKE_INSTALL_CONFIG_NAME=${CMAKE_BUILD_TYPE}
        -P cmake_install.cmake
)

if (DOXYGEN_FOUND)
    # set input and output files
    set(DOXYGEN_IN ${CMAKE_CURRENT_SOURCE_DIR}/Doxyfile)
    set(DOXYGEN_OUT ${CMAKE_CURRENT_BINARY_DIR}/Doxyfile)

    # request to configure the file
    configure_file(${DOXYGEN_IN} ${DOXYGEN_OUT} @ONLY)
    message("Doxygen build started")

    add_custom_target( doc_doxygen ALL
            COMMAND ${DOXYGEN_EXECUTABLE} ${DOXYGEN_OUT}
            WORKING_DIRECTORY ${CMAKE_CURRENT_SOURCE_DIR}
            COMMENT "Generating API documentation with Doxygen"
            VERBATIM )
else (DOXYGEN_FOUND)
    message("Doxygen need to be installed to generate the doxygen documentation")
endif (DOXYGEN_FOUND)