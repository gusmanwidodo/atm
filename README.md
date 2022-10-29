# ATM CLI

## Architecture
![App Architecture](./atm-cli-architecture.png?raw=true "App Architecture")
### presenter: spring shell
### core module: business logic
### DB infra: springboot JPA + H2 database

## Get Started
- Clone repository
- Build project
- Run the built image
- Enjoy!

### Running for development
```shell
$ mvn clean install
$ mvn spring-boot:run
```

## Specification
### Commands
* `login [name]` - Logs in as this customer and creates the customer if not exist

* `deposit [amount]` - Deposits this amount to the logged in customer

* `withdraw [amount]` - Withdraws this amount from the logged in customer

* `transfer [target] [amount]` - Transfers this amount from the logged in customer to the target customer

* `logout` - Logs out of the current customer


### Entity Relational Diagram (ERD)
![App Architecture](./atm-cli-erd.png?raw=true "App Architecture")

## Author
- [Gusman Widodo](https://linkedin.com/gusmanwidodo) <[gusmanwidodo@gmail.com](mailto:gusmanwidodo@gmail.com)>