#include "menu.h"
#include "window.h"
#include "ui.h"


void UI::Init() {
    create_menu();
    create_window();
};

void UI::Destroy() {
    destroy_window();
    destroy_menu();
}
