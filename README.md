# Bajaj Finserv Hiring Challenge

## Download JAR file

[Download JAR file](https://github.com/chhavikant-01/bajaj-finserv/blob/main/target/hiring-challenge-0.0.1-SNAPSHOT.jar)

This Spring Boot application solves the SQL challenge by:
1. Sending a POST request to generate a webhook URL and access token
2. Solving the SQL problem (counting younger employees in each department)
3. Submitting the solution to the webhook URL using the JWT token

## Working

On application startup, the `AppInitializer` component:
1. Generates a webhook by calling the specified API
2. Creates a SQL query solution for the problem
3. Submits the solution to the webhook URL using the provided access token

## Solution Explanation

The SQL problem requires counting employees who are younger than each employee within the same department.

The implemented solution:
```sql
SELECT e1.EMP_ID, e1.FIRST_NAME, e1.LAST_NAME, d.DEPARTMENT_NAME, 
       COUNT(e2.EMP_ID) AS YOUNGER_EMPLOYEES_COUNT 
FROM EMPLOYEE e1 
JOIN DEPARTMENT d ON e1.DEPARTMENT = d.DEPARTMENT_ID 
LEFT JOIN EMPLOYEE e2 ON e1.DEPARTMENT = e2.DEPARTMENT AND e1.DOB > e2.DOB 
GROUP BY e1.EMP_ID, e1.FIRST_NAME, e1.LAST_NAME, d.DEPARTMENT_NAME 
ORDER BY e1.EMP_ID DESC
```

This query:
- Joins the employee with their department
- For each employee, counts other employees in the same department with a later DOB (younger)
- Groups by employee details and orders by EMP_ID in descending order

## Running the Application

To run the application:
1. using maven

```bash
mvn spring-boot:run
```

2. using java

```bash
java -jar target/hiring-challenge-0.0.1-SNAPSHOT.jar
```

NOTE: The application will automatically execute the workflow upon startup. 