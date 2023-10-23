package com.customeer.customeer.ui.components;

import com.customeer.customeer.models.Customer;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

public class CustomerForm extends FormLayout {

    private final TextField nameField = new TextField("Name");
    private final TextField emailField = new TextField("Email");
    private final TextArea notesField = new TextArea("Notes");

    public CustomerForm(Button saveButton, Button cancelButton) {

        setColspan(notesField, 2);
        add(nameField, emailField, notesField, saveButton, cancelButton);
    }

    public void setCustomer(Customer customer) {
        nameField.setValue(customer.getName());
        emailField.setValue(customer.getEmail());
        notesField.setValue(customer.getNotes());
    }

    public Customer getCustomer() {
        return new Customer(nameField.getValue(), emailField.getValue(), notesField.getValue());
    }

    public void clearFields() {
        nameField.clear();
        emailField.clear();
        notesField.clear();
    }
}
