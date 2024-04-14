#include <cstring>
#include "XPLMMenus.h"
#include "window.h"
#include "menu.h"


XPLMMenuID g_menu_id;
int g_menu_container_idx;

void menu_handler(void *, void *);

int create_menu() {
    g_menu_container_idx = XPLMAppendMenuItem(XPLMFindPluginsMenu(), "Checklist Tester", nullptr, 0);
    g_menu_id = XPLMCreateMenu("Checklist Tester", XPLMFindPluginsMenu(), g_menu_container_idx, menu_handler, nullptr);

    XPLMAppendMenuItem(g_menu_id, "Open Checklist Tester", (void *)"Open Menu", 1);

    return g_menu_id != nullptr;
}

void destroy_menu() {
    XPLMDestroyMenu(g_menu_id);
}

void menu_handler(void * in_menu_ref, void * in_item_ref) {
    if(!strcmp((const char *)in_item_ref, "Open Menu")) {
        toggle_window();
    }
}
