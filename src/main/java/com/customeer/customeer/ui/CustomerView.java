package com.customeer.customeer.ui;

import com.customeer.customeer.models.Customer;
import com.customeer.customeer.services.CustomerService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route("customers")
public class CustomerView extends VerticalLayout {

    private final CustomerService customerService;

    private Grid<Customer> grid = new Grid<>(Customer.class);

    private TextField nameField = new TextField("Name");
    private TextField emailField = new TextField("Email");
    private Button saveButton = new Button("Save");
    private Button cancelButton = new Button("Cancel");
    private FormLayout formLayout = new FormLayout(nameField, emailField, saveButton, cancelButton);


    @Autowired
    public CustomerView(CustomerService customerService) {
        this.customerService = customerService;

        add(new Button("Add Customer", click -> addCustomer()));
        updateList();
        add(grid);
    }

    public void updateList() {
        grid.setItems(customerService.findAll());
    }

    public void addCustomer() {
        // Open the form to input customer details
        add(formLayout);

        saveButton.addClickListener(event -> {
            Customer newCustomer = new Customer();
            newCustomer.setName(nameField.getValue());
            newCustomer.setEmail(emailField.getValue());
            customerService.save(newCustomer);
            updateList();
            remove(formLayout);  // Remove the form after saving
        });

        cancelButton.addClickListener(event -> {
            remove(formLayout);  // Remove the form without saving
        });
    }

}
