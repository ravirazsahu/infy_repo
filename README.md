# Retailer Reward System

Retailer Reward System application provides a rewards program to the customers, awarding points based on each recorded purchase.

A customer receives 2 points for every dollar spent over $100 in each transaction, plus 1 point

for every dollar spent between $50 and $100 in each transaction.

(e.g. a $120 purchase = 2x$20 + 1x$50 = 90 points).

The application processes transaction records over a three-month period and calculates the reward points earned by each customer on a monthly basis as well as their total points.


## Technologies Used
* Java 8
* Spring Boot 2.6.3
* Swagger 2.7.0
* Junit
* Maven


## Installation Guide

1.Clone the repository using the below url  
```bash
https://github.com/ravirazsahu/spring-boot-rewards-system.git
```
2. Navigate to the project directory 
```bash
cd spring-boot-rewards-system
```
3. Build the project using Maven command : 
```bash
mvn clean install
```
4. After successfully installing the project, you can run the project from Eclipse/STS.

5. The application will be available at 
```bash
http://localhost:9010/retailer-rewards
```

## Flow Diagram for Fetch API Process

![fetch-api-flow-diagram](https://github.com/user-attachments/assets/05450d81-237f-4065-94f5-6b9c8558eeae)




## API Endpoints


1.Bulk Entry of reward transactions
```bash
/api/reward/bulk/entry
```
**Method:**  POST

**Description:** This API allows you to insert sample bulk transaction data.

**Example:** To inserting data, send a POST request to 
```bash
http://localhost:9010/retailer-rewards/api/reward/bulk/entry
```
**Request Body**

```bash

{
  "transactions": [
    {
      "customerName": "Roxy",
      "amount": 120,
      "date": "2024-04-18"
    },
    {
      "customerName": "Roxy",
      "amount": 90,
      "date": "2024-06-25"
    },
    {
      "customerName": "Roxy",
      "amount": 60,
      "date": "2024-07-01"
    },
    {
      "customerName": "Jack",
      "amount": 60,
      "date": "2024-02-11"
    },
    {
      "customerName": "Jack",
      "amount": 50,
      "date": "2024-04-15"
    },
    {
      "customerName": "Sam",
      "amount": 200,
      "date": "2024-07-18"
    },
    {
      "customerName": "Sam",
      "amount": 130,
      "date": "2024-06-15"
    },
    {
      "customerName": "Sam",
      "amount": 90,
      "date": "2024-06-22"
    },
    {
      "customerName": "John",
      "amount": 55,
      "date": "2024-07-01"
    },
    {
      "customerName": "John",
      "amount": 95,
      "date": "2024-04-10"
    }
  ]
}

```
**Response:**
```bash

{
    "data": [
        {
            "customerId": "1",
            "customerName": "Roxy",
            "amount": 120,
            "date": "2024-04-18"
        },
        {
            "customerId": "2",
            "customerName": "Roxy",
            "amount": 90,
            "date": "2024-06-25"
        },
        {
            "customerId": "3",
            "customerName": "Roxy",
            "amount": 60,
            "date": "2024-07-01"
        },
        {
            "customerId": "4",
            "customerName": "Jack",
            "amount": 60,
            "date": "2024-02-11"
        },
        {
            "customerId": "5",
            "customerName": "Jack",
            "amount": 50,
            "date": "2024-04-15"
        },
        {
            "customerId": "6",
            "customerName": "Sam",
            "amount": 200,
            "date": "2024-07-18"
        },
        {
            "customerId": "7",
            "customerName": "Sam",
            "amount": 130,
            "date": "2024-06-15"
        },
        {
            "customerId": "8",
            "customerName": "Sam",
            "amount": 90,
            "date": "2024-06-22"
        },
        {
            "customerId": "9",
            "customerName": "John",
            "amount": 55,
            "date": "2024-07-01"
        },
        {
            "customerId": "10",
            "customerName": "John",
            "amount": 95,
            "date": "2024-04-10"
        }
    ],
    "message": "Record Inserted Successfully.",
    "status": 200
}

```


2. Fetch Reward points using this api
```bash
/api/reward/fetch/all
```
**Method:** GET

**Description:** Calculates and retrieves the reward points for each customer per month.

**Example:** To retrieve reward points, send a GET request to 
```bash
http://localhost:9010/retailer-rewards/api/reward
```

**sample data:**
```bash

{ "customerName": "Roxy", "amount": 120, "date": "2024-04-18" }
{ "customerName": "Roxy", "amount": 90, "date": "2024-06-25" }
{ "customerName": "Roxy", "amount": 60, "date": "2024-07-01" }
{ "customerName": "Jack", "amount": 60, "date": "2024-02-11" }
{ "customerName": "Jack", "amount": 50, "date": "2024-04-15" }
{ "customerName": "Sam", "amount": 200, "date": "2024-07-18" }
{ "customerName": "Sam", "amount": 130, "date": "2024-06-15" }
{ "customerName": "Sam", "amount": 90, "date": "2024-06-22" }
{ "customerName": "John", "amount": 55, "date": "2024-07-01" }
{ "customerName": "John", "amount": 95, "date": "2024-04-10" }

```



**Response:**
```bash

{
    "data": [
        {
            "name": "John",
            "year": 2024,
            "totalPoints": 5,
            "monthWisePoints": [
                {
                    "month": "JULY",
                    "points": 5
                }
            ]
        },
        {
            "name": "Roxy",
            "year": 2024,
            "totalPoints": 50,
            "monthWisePoints": [
                {
                    "month": "JUNE",
                    "points": 40
                },
                {
                    "month": "JULY",
                    "points": 10
                }
            ]
        },
        {
            "name": "Sam",
            "year": 2024,
            "totalPoints": 400,
            "monthWisePoints": [
                {
                    "month": "JUNE",
                    "points": 150
                },
                {
                    "month": "JULY",
                    "points": 250
                }
            ]
        }
    ],
    "message": "Data Retrieved Successfully.",
    "status": 200
}

```

## Swagger URL

```bash
http://localhost:9010/retailer-rewards/swagger-ui.html#!/reward45calculation45controller/getRewardsUsingGET
```

## H2-Database Console URL

```bash
http://localhost:9010/retailer-rewards/h2-console/
```
