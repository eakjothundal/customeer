# Customeer: Customer Relationship Management (CRM) Web Application

<p align="center">
  <img src="https://github.com/eakjothundal/customeer/assets/44451960/bbb674a0-c47e-4e66-8640-9803e23f04c3" alt="DALL·E 2023-10-22 17 30 18 - Vector design of a minimalist logo for 'customeer'. The 'o' in customeer is replaced by a stylized icon that combines a shopping cart and a handshake." width="250" height="250">
</p>




Customeer is a robust and intuitive CRM web application designed to streamline the management of customer data. Built using modern Java technologies, Customeer offers seamless CRUD operations, ensuring that businesses can efficiently track, modify, and access client information.

## Features

- **Efficient CRUD Operations**: Easily create, read, update, and delete customer records.
- **Intuitive UI**: Built with Vaadin, offering a user-friendly interface for all operations.
- **Robust Backend**: Powered by Spring Boot, ensuring scalability and reliability.
- **In-memory Database**: Utilizes H2 database for rapid development and testing.

## Technologies Used

- **Spring Boot**: For creating stand-alone, production-grade Spring-based applications.
- **Vaadin**: For building modern web apps efficiently in plain Java.
- **Spring Data JPA**: For easily implementing JPA-based repositories.
- **H2 Database**: An in-memory database for development purposes.
- **JUnit 5**: The next generation testing framework for Java. Used for unit and integration testing.
- **Mockito**: A mocking framework for Java. Used in conjunction with JUnit for testing.

## Demo

https://github.com/eakjothundal/customeer/assets/44451960/185fc41f-d342-4b08-95c8-d731b3f2aa7d


## Getting Started

### Prerequisites

- Java JDK 11 or higher
- Maven

### Installation

1. Clone the repository:
`git clone https://github.com/eakjothundal/customeer.git`

2. Navigate to the project directory:
`cd customeer`

3. Build and run the application:
`mvn spring-boot:run`

4. Access the application at `http://localhost:8080`.

## Usage

1. **View Customers**: Navigate to the main page to view a list of all customers.
2. **Add Customers**: Click on the "Add Customer" button and fill in the required details.
3. **Update/Delete Customers**: Update customer details or delete customer record.

## Future Additions

- [ ] Persistent Database Integration: Transition from an in-memory database to a persistent database solution for long-term data storage and better scalability.
- [ ] Confirmation/Error Modals: Implement modals to provide feedback and confirmation messages for user actions, ensuring a better user experience.


## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
