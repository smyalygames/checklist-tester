cmake_minimum_required(VERSION 3.27)

project(XPL-Checklist-Connector CXX)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_BUILD_TYPE Release)

# Set compile options for XPLM
add_definitions(-DXPLM200=1 -DXPLM210=1 -DXPLM300=1 -DXPLM301=1 -DXPLM302=1 -DXPLM303=1 -DXPLM401=1)

set(GCC_COVERAGE_COMPILE_FLAGS "-fPIC -fvisibility=hidden")
set(GCC_COVERAGE_LINK_FLAGS "-m64 -static-libgcc -shared")

find_package(unofficial-x-plane CONFIG REQUIRED)

add_library(${PROJECT_NAME} SHARED main.cpp)

target_link_libraries(${PROJECT_NAME} PRIVATE unofficial::x-plane::xplm unofficial::x-plane::xpwidgets unofficial::x-plane::xplm_cpp)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} ${GCC_COVERAGE_COMPILE_FLAGS}")

set_target_properties(${PROJECT_NAME} PROPERTIES
        LIBRARY_OUTPUT_DIRECTORY ${CMAKE_SOURCE_DIR}/build/${PROJECT_NAME}/lin_x64
        PREFIX ""
        SUFFIX ".xpl"
        LINK_FLAGS ${GCC_COVERAGE_LINK_FLAGS}
)
