package com.customeer.customeer.ui;

import com.customeer.customeer.models.Customer;
import com.customeer.customeer.services.CustomerService;
import com.customeer.customeer.ui.components.CustomerForm;
import com.customeer.customeer.ui.components.CustomerGrid;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route("")
@CssImport("/styles/CustomerViewStyles.css")
public class CustomerView extends VerticalLayout {

    private final CustomerService customerService;
    private final CustomerGrid grid = new CustomerGrid();
    private CustomerForm currentForm;

    // Constructor for dependency injection
    @Autowired
    public CustomerView(CustomerService customerService) {
        this.customerService = customerService;

        addClassName("centered-content");

        addHeader();
        addAddCustomerButton();
        updateList();
        grid.addComponentColumn(this::createButtonsLayout).setHeader("Actions");
        add(grid);
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
        currentForm = new CustomerForm(
                new Button("Save", event -> {
                    saveUpdatedCustomer(customer);
                    currentForm.close();
                }),
                new Button("Cancel", event -> currentForm.close())
        );
        currentForm.setCustomer(customer);
        currentForm.open();
    }

    /**
     * Saves the updated customer data and refreshes the grid.
     *
     * @param customer the customer to update
     */
    private void saveUpdatedCustomer(Customer customer) {
        Customer updatedCustomer = currentForm.getCustomer();
        customer.setName(updatedCustomer.getName());
        customer.setEmail(updatedCustomer.getEmail());
        customer.setNotes(updatedCustomer.getNotes());
        customerService.save(customer);
        currentForm.clearFields();
        updateList();
        removeForm();
    }

    /**
     * Adds a button to the layout that, when clicked, displays a form for adding a new customer.
     */
    private void addAddCustomerButton() {
        add(new Button("Add Customer", click -> {
            currentForm = new CustomerForm(
                    new Button("Save", event -> {
                        saveNewCustomer();
                        currentForm.close();
                    }),
                    new Button("Cancel", event -> currentForm.close())
            );
            currentForm.open();
        }));
    }

    private void addHeader() {
        // Create an Image component for the logo.
        Image logo = new Image("/images/logo.png", "Customeer Logo");
        logo.setWidth("300px");
        logo.setHeight("300px");

        // Create a horizontal layout for the logo and set its width to 100% to ensure it spans the full width.
        HorizontalLayout logoLayout = new HorizontalLayout(logo);
        logoLayout.setWidth("100%");
        logoLayout.setAlignItems(Alignment.CENTER); // Vertically center the logo
        logoLayout.setJustifyContentMode(JustifyContentMode.CENTER); // Horizontally center the logo

        // Add the logo layout to the top of the main layout.
        add(logoLayout);
    }



    /**
     * Saves the new customer data entered in the form and refreshes the grid.
     */
    private void saveNewCustomer() {
        Customer newCustomer = currentForm.getCustomer();
        customerService.save(newCustomer);
        currentForm.clearFields();
        updateList();
        removeForm();
    }

    /**
     * Removes the form from the view.
     */
    private void removeForm() {
        if (currentForm != null) {
            currentForm.close();
        }
    }
}
