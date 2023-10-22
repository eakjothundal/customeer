package com.customeer.customeer.ui;

import com.customeer.customeer.models.Customer;
import com.customeer.customeer.services.CustomerService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
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
    private Button saveButton;
    private Button cancelButton;
    private FormLayout formLayout;

    @Autowired
    public CustomerView(CustomerService customerService) {
        this.customerService = customerService;

        // Define the delete and update buttons column
        grid.addComponentColumn(customer -> createButtonsLayout(customer))
                .setHeader("Actions");

        add(new Button("Add Customer", click -> addCustomer()));
        updateList();
        add(grid);
    }

    public void updateList() {
        grid.setItems(customerService.findAll());
    }

    private void initFormLayout() {
        saveButton = new Button("Save");
        cancelButton = new Button("Cancel");
        formLayout = new FormLayout(nameField, emailField, saveButton, cancelButton);
    }

    public void addCustomer() {
        initFormLayout();

        saveButton.addClickListener(event -> {
            Customer newCustomer = new Customer();
            newCustomer.setName(nameField.getValue());
            newCustomer.setEmail(emailField.getValue());
            customerService.save(newCustomer);
            nameField.clear();  // Clear the name field
            emailField.clear();  // Clear the email field
            updateList();
            remove(formLayout);  // Remove the form after saving
        });

        cancelButton.addClickListener(event -> {
            remove(formLayout);  // Remove the form without saving
        });

        // Open the form to input customer details
        add(formLayout);
    }

    private HorizontalLayout createButtonsLayout(Customer customer) {
        Button deleteButton = new Button("Delete");
        deleteButton.addClickListener(event -> {
            customerService.deleteById(customer.getId());
            updateList();
        });

        Button updateButton = new Button("Update");
        updateButton.addClickListener(event -> {
            showUpdateForm(customer);
        });

        return new HorizontalLayout(updateButton, deleteButton);
    }

    private void showUpdateForm(Customer customer) {
        initFormLayout();

        // Pre-fill the form fields with the customer's data
        nameField.setValue(customer.getName());
        emailField.setValue(customer.getEmail());

        saveButton.addClickListener(event -> {
            customer.setName(nameField.getValue());
            customer.setEmail(emailField.getValue());
            customerService.save(customer);
            nameField.clear();
            emailField.clear();
            updateList();
            remove(formLayout);  // Remove the form after saving
        });

        add(formLayout);
    }
}
