cmake_minimum_required(VERSION 3.27)

project(XPL-Checklist-Connector CXX)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_BUILD_TYPE Release)

set(GCC_COVERAGE_LINK_FLAGS "-m64 -static-libgcc -shared")

add_subdirectory(src)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} ${GCC_COVERAGE_COMPILE_FLAGS}")

set_target_properties(XPL-Checklist-Connector PROPERTIES
        LIBRARY_OUTPUT_DIRECTORY ${CMAKE_SOURCE_DIR}/build/${PROJECT_NAME}/lin_x64
        PREFIX ""
        SUFFIX ".xpl"
        LINK_FLAGS ${GCC_COVERAGE_LINK_FLAGS}
)
