# Loan Manager - Backend Technical Challenge

This repository contains a solution for the Backend Technical Challenge. The challenge involves implementing a small set of RESTful APIs providing the ability to create and interact with an amortization schedule for an asset being financed.

## Table of Contents

- [Description](#description)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
- [Usage](#usage)
- [Testing](#testing)
- [Postman Collection](#postman-collection)

## Description

The project implements a set of RESTful APIs using Java and Spring Boot. These APIs allow users to create amortization schedules for loans with or without a balloon payment, list previously created schedules with details, and retrieve the full details of individual schedules.

The amortization schedule is generated based on the provided loan details such as the cost of the asset, any deposit, the yearly interest rate, the number of monthly payments, and an optional balloon payment.

## Getting Started

### Prerequisites

- Java (version 17 or above)
- Maven
- Your preferred IDE (e.g., IntelliJ IDEA, Eclipse)
- PostgreSQL database (create a database named "loanManager" and update username and password in application.yml file)

### Installation

1. Clone the repository:

```bash
git clone https://github.com/rintujude/loan-manager.git
```

2. Navigate to the project directory:

```bash
cd loan-manager
```

3. Build the project using Maven:

```bash
mvn clean install
```

## Usage

1. Start the Spring Boot application:

```bash
mvn spring-boot:run
```

2. Access the APIs using a REST client such as Postman or cURL.

3. Refer to the API documentation for details on how to use each endpoint.


3. Refer to the API documentation for details on how to use each endpoint.

## Testing

The project includes both unit and integration tests to ensure the correctness of the implemented functionality. There are a total of 4 tests:

### Unit Tests

- `calculateMonthlyRepaymentTestWithoutBalloonPayment`: Test for calculating the monthly repayment amount without a balloon payment.
- `calculateMonthlyRepaymentTestWithBalloonPayment`: Test for calculating the monthly repayment amount with a balloon payment.

### Integration Tests

- `testAmortisationEndpointWithBalloonPayment`: Integration test for the amortization endpoint with a balloon payment.
- `testAmortisationEndpointWithoutBalloonPayment`: Integration test for the amortization endpoint without a balloon payment.

To run the tests, use the following command:

```bash
mvn test
```


## Postman Collection

Find the Postman collection for the given APIs [here](https://api.postman.com/collections/11647431-4953e1e7-84fa-4904-bc14-865f24190037?access_key=PMAT-01HX6T19QTX06ME10KKRC13TP7).

