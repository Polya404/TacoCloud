package com.example.tacocloud.config;

import com.example.tacocloud.integration.EmailOrderTransformer;
import com.example.tacocloud.integration.EmailProperties;
import com.example.tacocloud.integration.OrderSubmitMessageHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.mail.dsl.Mail;

@Configuration
public class TacoOrderEmailIntegrationConfig {

    public IntegrationFlow tacoOrderEmailFlow(EmailProperties emailProperties, EmailOrderTransformer emailOrderTransformer,
                                              OrderSubmitMessageHandler submitMessageHandler) {
        return IntegrationFlows
                .from(Mail.imapInboundAdapter(emailProperties.getImapUrl()),
                        e -> e.poller(
                                Pollers.fixedDelay(emailProperties.getPollRate())
                        )).transform(emailOrderTransformer)
                .handle(submitMessageHandler).get();
    }
}
