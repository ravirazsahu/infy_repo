# Retailer Reward System

Retailer Reward System application provides a rewards program to the customers, awarding points based on each recorded purchase.

A customer receives 2 points for every dollar spent over $100 in each transaction, plus 1 point

for every dollar spent between $50 and $100 in each transaction.

(e.g. a $120 purchase = 2x$20 + 1x$50 = 90 points).

The application processes transaction records over a three-month period and calculates the reward points earned by each customer on a monthly basis as well as their total points.


## Installation

1.Clone the repository i.e  
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
4. After successfully installing the project, you can run the project from STS.

5. The application will be available at 
```bash
http://localhost:9010/retailer-rewards
```
6. Please execute the insert queries available in the transaction_queries.sql file manually. For some reason, h2 db does not pick up this file while executing. I will fix that issue.

## Technologies Used
* Java 8
* Spring Boot 2.6.3
* swagger
* Junit
* Maven



## API Endpoints
GET 
```bash
/api/reward
```
**Description:** Calculates and retrieves the reward points for each customer per month.

**Example:** To retrieve reward points, send a GET request to 
```bash
http://localhost:9010/retailer-rewards/api/reward
```

**sample data:**


![reward-system-input-db-data-new](https://github.com/user-attachments/assets/8688c3d9-be2e-4281-8356-ea5c3d603977)



**Response:**
```bash
{
  "data": [
    {
      "name": "John",
      "year": 2024,
      "monthWisePoints": [
        {
          "month": "JULY",
          "points": 5
        }
      ],
      "totalPoints": 5
    },
    {
      "name": "Roxy",
      "year": 2024,
      "monthWisePoints": [
        {
          "month": "JUNE",
          "points": 40
        },
        {
          "month": "JULY",
          "points": 10
        }
      ],
      "totalPoints": 50
    },
    {
      "name": "Sam",
      "year": 2024,
      "monthWisePoints": [
        {
          "month": "JUNE",
          "points": 150
        },
        {
          "month": "JULY",
          "points": 250
        }
      ],
      "totalPoints": 400
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
