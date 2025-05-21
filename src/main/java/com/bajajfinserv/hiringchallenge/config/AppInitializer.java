package com.bajajfinserv.hiringchallenge.config;

import com.bajajfinserv.hiringchallenge.model.WebhookResponse;
import com.bajajfinserv.hiringchallenge.service.WebhookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class AppInitializer implements ApplicationListener<ApplicationReadyEvent> {

    private static final Logger log = LoggerFactory.getLogger(AppInitializer.class);
    private final WebhookService webhookService;
    
    public AppInitializer(WebhookService webhookService) {
        this.webhookService = webhookService;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        log.info("Application started. Executing the webhook flow...");
        
        try {
            WebhookResponse webhookResponse = webhookService.generateWebhook();
            log.info("Webhook and access token received: {}", webhookResponse);
            
            String sqlQuery = webhookService.generateSqlQuery();
            log.info("Generated SQL query: {}", sqlQuery);
            
            webhookService.submitSolution(webhookResponse.getWebhook(), webhookResponse.getAccessToken(), sqlQuery);
            log.info("Solution submitted successfully: {}", sqlQuery);
        } catch (Exception e) {
            log.error("Error in application flow", e);
        }
    }
} 