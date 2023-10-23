package com.customeer.customeer.ui.components;

import com.customeer.customeer.models.Customer;
import com.vaadin.flow.component.grid.Grid;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CustomerGrid extends Grid<Customer> {

    public CustomerGrid() {
        addColumn(Customer::getId).setHeader("ID");
        addColumn(Customer::getName).setHeader("Name");
        addColumn(Customer::getEmail).setHeader("Email");
        addColumn(Customer::getNotes).setHeader("Notes");
    }

    public void setItems(Iterable<Customer> customers) {
        List<Customer> customerList = StreamSupport.stream(customers.spliterator(), false)
                .collect(Collectors.toList());
        super.setItems(customerList);
    }
}
