#include "XPLMDisplay.h"
#include "XPLMGraphics.h"
#include "window.h"


static XPLMWindowID g_window;

// Callbacks for creating window
void draw_window(XPLMWindowID in_window_id, void * in_refcon);
int dummy_mouse_handler(XPLMWindowID in_window_id, int x, int y, int is_down, void * in_refcon) { return 0; }
XPLMCursorStatus dummy_cursor_status_handler(XPLMWindowID in_window_id, int x, int y, void * in_refcon) { return xplm_CursorDefault; }
int dummy_wheel_handler(XPLMWindowID in_window_id, int x, int y, int wheel, int clicks, void * in_refcon) { return 0; }
void dummy_key_handler(XPLMWindowID in_window_id, char key, XPLMKeyFlags flags, char virtual_key, void * in_refcon, int losing_focus) { }

int create_window() {
    XPLMCreateWindow_t params;
    params.structSize = sizeof(params);
    params.visible = 0;
    params.drawWindowFunc = draw_window;
    // Register mandatory "dummy" handlers
    params.handleMouseClickFunc = dummy_mouse_handler;
    params.handleRightClickFunc = dummy_mouse_handler;
    params.handleMouseWheelFunc = dummy_wheel_handler;
    params.handleKeyFunc = dummy_key_handler;
    params.handleCursorFunc = dummy_cursor_status_handler;
    params.refcon = nullptr;
    params.layer = xplm_WindowLayerFloatingWindows;
    // Newest X-Plane GUI styling
    params.decorateAsFloatingWindow = xplm_WindowDecorationRoundRectangle;

    // Set the window's initial bounds
    int left, bottom, right, top;
    XPLMGetScreenBoundsGlobal(&left, &top, &right, &bottom);
    params.left = left + 50;
    params.bottom = bottom + 150;
    params.right = params.left + 200;
    params.top = params.bottom + 200;

    g_window = XPLMCreateWindowEx(&params);

    // Make window free floating window
    XPLMSetWindowPositioningMode(g_window, xplm_WindowPositionFree, -1);
    // Window resizing limits
    XPLMSetWindowResizingLimits(g_window, 200, 200, 300, 300);

    XPLMSetWindowTitle(g_window, "Checklist Tester");

    return g_window != nullptr;
}

void draw_window(XPLMWindowID in_window_id, void * in_refcon) {
    // Set OpenGL state before drawing
    XPLMSetGraphicsState(
            0, // No fog
            0, // 0 texture units
            0, // No lighting
            0, // No alpha testing
            1, // Do alpha blend
            1, // Do depth testing
            0 // No depth writing
    );

    int l, t, r, b;
    XPLMGetWindowGeometry(in_window_id, &l, &t, &r, &b);

    float col_white[] = {1.0, 1.0, 1.0}; // RGB

    XPLMDrawString(col_white, l + 10, t - 20, "Hello World!", nullptr, xplmFont_Proportional);
}

void toggle_window() {
    int state = XPLMGetWindowIsVisible(g_window);
    XPLMSetWindowIsVisible(g_window, !state);
}

void destroy_window() {
    XPLMDestroyWindow(g_window);
    g_window = nullptr;
}
