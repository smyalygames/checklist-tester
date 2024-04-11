cmake_minimum_required(VERSION 3.27)

project(XPL-Checklist-Connector CXX)

set(CMAKE_CXX_STANDARD 17)

# Set compile options for XPLM
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -DXPLM200=1 -DXPLM210=1 -DXPLM300=1 -DXPLM301=1 -DXPLM302=1 -DXPLM303=1 -DXPLM401=1 -fvisibility=hidden -shared -rdynamic -nodefaultlibs -undefined_warning -lGL -lGLU")

# Set output directory in X-Plane plugin structure
set(CMAKE_RUNTIME_OUTPUT_DIRECTORY ${CMAKE_BINARY_DIR}/bin/lin_x64)

find_package(unofficial-x-plane CONFIG REQUIRED)

add_executable(XPL-Checklist-Connector main.cpp)

target_link_libraries(XPL-Checklist-Connector PRIVATE unofficial::x-plane::xplm unofficial::x-plane::xpwidgets unofficial::x-plane::xplm_cpp)

# Rename compiled file to X-Plane conventions
set_target_properties(XPL-Checklist-Connector PROPERTIES RUNTIME_OUTPUT_NAME "lin")
set_target_properties(XPL-Checklist-Connector PROPERTIES SUFFIX ".xpl")
