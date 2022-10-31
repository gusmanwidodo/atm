# ATM CLI
This project is a Command Line Interface (CLI) to simulate an interaction of an ATM with a retail bank.

## Architecture
![App Architecture](./atm-cli-architecture.png?raw=true "App Architecture")
### presenter: spring shell
### core module: business logic
### DB infra: springboot JPA + H2 database

## Prerequisites
Ensure you have this installed before proceeding further.
- Java 11
- Maven 3.8.1+

## Get Started
- Clone repository
- Build project
- Run the built image
- Enjoy!

### Running for development
#### Build
```shell
$ mvn clean install
```
#### Start
```shell
java -jar ./cli/target/cli-0.0.1-SNAPSHOT.jar
```
** Testable Class
`com.github.gusmanwidodo.atm.core.service.ATMServiceTest`


## Specification
### Commands
* `login [name]` - Logs in as this customer and creates the customer if not exist

* `deposit [amount]` - Deposits this amount to the logged in customer

* `withdraw [amount]` - Withdraws this amount from the logged in customer

* `transfer [target] [amount]` - Transfers this amount from the logged in customer to the target customer

* `logout` - Logs out of the current customer


### Entity Relational Diagram (ERD)
![App Architecture](./atm-cli-erd.png?raw=true "App Architecture")

### Structure
#### Core Module
```shell
➜  atm git:(main) ✗  tree core/src/main/java/com/github/gusmanwidodo/atm/core
core/src/main/java/com/github/gusmanwidodo/atm/core
|-- constant
|   |-- AuthData.java
|   |-- Bank.java
|   |-- RefType.java
|   `-- Status.java
|-- exception
|   `-- AmountInvalidException.java
|-- model
|   |-- Account.java
|   |-- Customer.java
|   |-- Payment.java
|   `-- Transaction.java
|-- repository
|   |-- AccountRepository.java
|   |-- CustomerRepository.java
|   |-- PaymentRepository.java
|   `-- TransactionRepository.java
`-- service
    |-- ATMService.java
    |-- ATMServiceImpl.java
    `-- TransferManager.java

5 directories, 16 files


```
#### Cli
```shell
➜  atm git:(main) ✗  tree cli/src/main/java/com/github/gusmanwidodo/atm/cli
cli/src/main/java/com/github/gusmanwidodo/atm/cli
|-- CliApplication.java
`-- command
    `-- ATMCommand.java

1 directory, 2 files

```

## Todo List
- Enrich Unit Testing with various cases
- Detail Exception class
- more...


## Screenshots

![Unit Testing](./test-screenshot.png?raw=true "Unit Testing")

![CLI](./atm-cli-screenshot.png?raw=true "CLI")


## Author
- [Gusman Widodo](https://linkedin.com/gusmanwidodo) <[gusmanwidodo@gmail.com](mailto:gusmanwidodo@gmail.com)>