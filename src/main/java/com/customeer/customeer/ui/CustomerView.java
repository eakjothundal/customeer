package com.customeer.customeer.ui;

import com.customeer.customeer.models.Customer;
import com.customeer.customeer.services.CustomerService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route("")
public class CustomerView extends VerticalLayout {

    private final CustomerService customerService;

    private Grid<Customer> grid = new Grid<>();
    private TextField nameField = new TextField("Name");
    private TextField emailField = new TextField("Email");
    private TextArea notesField = new TextArea("Notes");
    private Button saveButton;
    private Button cancelButton;
    private FormLayout formLayout;

    @Autowired
    public CustomerView(CustomerService customerService) {
        this.customerService = customerService;

        grid.addColumn(Customer::getId).setHeader("ID");
        grid.addColumn(Customer::getName).setHeader("Name");
        grid.addColumn(Customer::getEmail).setHeader("Email");
        grid.addColumn(Customer::getNotes).setHeader("Notes");
        // Define the delete and update buttons column
        grid.addComponentColumn(customer -> createButtonsLayout(customer)).setHeader("Actions");


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
        formLayout = new FormLayout(nameField, emailField, notesField, saveButton, cancelButton);
        formLayout.setColspan(notesField, 2);
    }

    public void addCustomer() {
        initFormLayout();
        notesField.setWidthFull();

        saveButton.addClickListener(event -> {
            Customer newCustomer = new Customer();
            newCustomer.setName(nameField.getValue());
            newCustomer.setEmail(emailField.getValue());
            newCustomer.setNotes(notesField.getValue());

            customerService.save(newCustomer);
            nameField.clear();
            emailField.clear();
            notesField.clear();

            updateList();
            remove(formLayout);
        });

        cancelButton.addClickListener(event -> {
            remove(formLayout);
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
        notesField.setValue(customer.getNotes());

        saveButton.addClickListener(event -> {
            customer.setName(nameField.getValue());
            customer.setEmail(emailField.getValue());
            customer.setNotes(notesField.getValue());

            customerService.save(customer);
            nameField.clear();
            emailField.clear();
            notesField.clear();

            updateList();
            remove(formLayout);
        });

        add(formLayout);
    }
}
