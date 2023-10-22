package com.customeer.customeer.ui;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.component.html.H1;

@Route("")
public class MainLayout extends VerticalLayout implements RouterLayout {
    // Add common UI components here like headers, footers, menus, etc.
    public MainLayout() {
        add(new H1("Hello, world"));
    }
}


