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
    private final Grid<Customer> grid = new Grid<>();
    private final TextField nameField = new TextField("Name");
    private final TextField emailField = new TextField("Email");
    private final TextArea notesField = new TextArea("Notes");

    // Constructor for dependency injection
    @Autowired
    public CustomerView(CustomerService customerService) {
        this.customerService = customerService;

        configureGrid();
        addAddCustomerButton();
        updateList();
        add(grid);
    }

    /**
     * Configures the grid columns and actions.
     */
    private void configureGrid() {
        grid.addColumn(Customer::getId).setHeader("ID");
        grid.addColumn(Customer::getName).setHeader("Name");
        grid.addColumn(Customer::getEmail).setHeader("Email");
        grid.addColumn(Customer::getNotes).setHeader("Notes");
        grid.addComponentColumn(this::createButtonsLayout).setHeader("Actions");
    }

    /**
     * Updates the list of customers displayed in the grid.
     */
    public void updateList() {
        grid.setItems(customerService.findAll());
    }

    /**
     * Creates the layout for action buttons (update and delete) for each customer row.
     *
     * @param customer the customer for the row
     * @return a horizontal layout containing action buttons
     */
    private HorizontalLayout createButtonsLayout(Customer customer) {
        return new HorizontalLayout(
                new Button("Update", event -> showUpdateForm(customer)),
                new Button("Delete", event -> deleteCustomer(customer))
        );
    }

    /**
     * Deletes the given customer and updates the grid.
     *
     * @param customer the customer to delete
     */
    private void deleteCustomer(Customer customer) {
        customerService.deleteById(customer.getId());
        updateList();
    }

    /**
     * Displays a form pre-filled with the data of the given customer for updating.
     *
     * @param customer the customer to update
     */
    private void showUpdateForm(Customer customer) {
        FormLayout formLayout = createFormLayout(
                new Button("Save", event -> saveUpdatedCustomer(customer)),
                new Button("Cancel", event -> removeForm())
        );
        setFieldValues(customer);
        add(formLayout);
    }

    /**
     * Saves the updated customer data and refreshes the grid.
     *
     * @param customer the customer to update
     */
    private void saveUpdatedCustomer(Customer customer) {
        customer.setName(nameField.getValue());
        customer.setEmail(emailField.getValue());
        customer.setNotes(notesField.getValue());
        customerService.save(customer);
        clearFields();
        updateList();
        removeForm();
    }

    /**
     * Sets the form fields with the values from the given customer.
     *
     * @param customer the customer whose data is to be set in the form
     */
    private void setFieldValues(Customer customer) {
        nameField.setValue(customer.getName());
        emailField.setValue(customer.getEmail());
        notesField.setValue(customer.getNotes());
    }

    /**
     * Adds a button to the layout that, when clicked, displays a form for adding a new customer.
     */
    private void addAddCustomerButton() {
        add(new Button("Add Customer", click -> {
            FormLayout formLayout = createFormLayout(
                    new Button("Save", event -> saveNewCustomer()),
                    new Button("Cancel", event -> removeForm())
            );
            add(formLayout);
        }));
    }

    /**
     * Saves the new customer data entered in the form and refreshes the grid.
     */
    private void saveNewCustomer() {
        Customer newCustomer = new Customer(nameField.getValue(), emailField.getValue(), notesField.getValue());
        customerService.save(newCustomer);
        clearFields();
        updateList();
        removeForm();
    }

    /**
     * Creates a form layout for entering customer data.
     *
     * @param saveButton   the button to save the customer data
     * @param cancelButton the button to cancel and close the form
     * @return the form layout
     */
    private FormLayout createFormLayout(Button saveButton, Button cancelButton) {
        FormLayout formLayout = new FormLayout(nameField, emailField, notesField, saveButton, cancelButton);
        formLayout.setColspan(notesField, 2);
        return formLayout;
    }

    /**
     * Clears all the input fields in the form.
     */
    private void clearFields() {
        nameField.clear();
        emailField.clear();
        notesField.clear();
    }

    /**
     * Removes the form from the view.
     */
    private void removeForm() {
        remove(nameField, emailField, notesField);
    }
}
