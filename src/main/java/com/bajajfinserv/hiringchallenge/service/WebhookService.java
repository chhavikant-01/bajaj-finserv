package com.bajajfinserv.hiringchallenge.service;

import com.bajajfinserv.hiringchallenge.model.FinalQueryRequest;
import com.bajajfinserv.hiringchallenge.model.WebhookRequest;
import com.bajajfinserv.hiringchallenge.model.WebhookResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WebhookService {

    private static final Logger log = LoggerFactory.getLogger(WebhookService.class);
    private final RestTemplate restTemplate;
    private static final String GENERATE_WEBHOOK_URL = "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA";

    public WebhookService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    // Generates a SQL query based on the problem statement

    public String generateSqlQuery() {
        // Query for problem statement 2 with the specified format
        return "SELECT e1.EMP_ID, e1.FIRST_NAME, e1.LAST_NAME, d.DEPARTMENT_NAME, " +
                "COUNT(e2.EMP_ID) AS YOUNGER_EMPLOYEES_COUNT " +
                "FROM EMPLOYEE e1 " +
                "JOIN DEPARTMENT d ON e1.DEPARTMENT = d.DEPARTMENT_ID " +
                "LEFT JOIN EMPLOYEE e2 ON e1.DEPARTMENT = e2.DEPARTMENT AND e1.DOB > e2.DOB " +
                "GROUP BY e1.EMP_ID, e1.FIRST_NAME, e1.LAST_NAME, d.DEPARTMENT_NAME " +
                "ORDER BY e1.EMP_ID DESC";
    }

    // Generates a webhook by sending a POST request

    public WebhookResponse generateWebhook() {
        WebhookRequest request = new WebhookRequest(
                "Chhavikant Mahobia",
                "1032222012",
                "1032222012@mitwpu.edu.in"
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<WebhookRequest> entity = new HttpEntity<>(request, headers);

        log.info("Sending request to generate webhook");
        WebhookResponse response = restTemplate.postForObject(
                GENERATE_WEBHOOK_URL,
                entity,
                WebhookResponse.class
        );
        log.info("Webhook generated successfully: {}", response);
        return response;
    }

    // Submit the SQL solution to the webhook URL

    public void submitSolution(String webhookUrl, String accessToken, String sqlQuery) {
        FinalQueryRequest request = new FinalQueryRequest(sqlQuery);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", accessToken);
        HttpEntity<FinalQueryRequest> entity = new HttpEntity<>(request, headers);

        log.info("Submitting SQL solution to webhook");
        String response = restTemplate.postForObject(
                webhookUrl,
                entity,
                String.class
        );
        log.info("Solution submitted successfully. Response: {}", response);
    }
} 