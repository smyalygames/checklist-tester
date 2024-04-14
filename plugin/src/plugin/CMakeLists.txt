file(GLOB PLUGIN_SRC "${CMAKE_CURRENT_SOURCE_DIR}/*.cpp")
file(GLOB PLUGIN_HDR "${CMAKE_CURRENT_SOURCE_DIR}/*.h")
file(GLOB UI_SRC "${CMAKE_CURRENT_SOURCE_DIR}/ui/*.cpp")
file(GLOB UI_HDR "${CMAKE_CURRENT_SOURCE_DIR}/ui/*.h")

add_library(XPL-Checklist-Connector MODULE ${PLUGIN_SRC} ${PLUGIN_HDR} ${UI_SRC} ${UI_HDR})

# Set compile options for XPLM
add_definitions(-DXPLM200=1 -DXPLM210=1 -DXPLM300=1 -DXPLM301=1 -DXPLM302=1 -DXPLM303=1 -DXPLM401=1)

set(GCC_COVERAGE_COMPILE_FLAGS "-fPIC -fvisibility=hidden")

find_package(unofficial-x-plane CONFIG REQUIRED)

target_link_libraries(XPL-Checklist-Connector PRIVATE unofficial::x-plane::xplm unofficial::x-plane::xpwidgets unofficial::x-plane::xplm_cpp)
