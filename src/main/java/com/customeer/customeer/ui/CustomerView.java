package com.customeer.customeer.ui;

import com.customeer.customeer.models.Customer;
import com.customeer.customeer.services.CustomerService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route("customers")
public class CustomerView extends VerticalLayout {

    @Autowired
    private CustomerService customerService;

    private Grid<Customer> grid = new Grid<>(Customer.class);

    public CustomerView() {
        add(new Button("Add Customer", click -> addCustomer()));
        updateList();
        add(grid);
    }

    public void updateList() {
        grid.setItems(customerService.findAll());
    }

    public void addCustomer() {
        // Logic to add a new customer
        // This can be a new dialog or form
    }
}

