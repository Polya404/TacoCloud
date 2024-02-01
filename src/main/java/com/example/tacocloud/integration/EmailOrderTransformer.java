package com.example.tacocloud.integration;

import com.example.tacocloud.models.Ingredient;
import com.example.tacocloud.models.Taco;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.mail.transformer.AbstractMailMessageTransformer;
import org.springframework.integration.support.AbstractIntegrationMessageBuilder;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class EmailOrderTransformer extends AbstractMailMessageTransformer<EmailOrder> {
    private static final Ingredient[] ALL_INGREDIENTS = new Ingredient[]{
            new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP),
            new Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP),
            new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN),
            new Ingredient("CARN", "Carnitas", Ingredient.Type.PROTEIN),
            new Ingredient("TMTO", "Diced Tomatoes", Ingredient.Type.VEGGIES),
            new Ingredient("LETC", "Lettuce", Ingredient.Type.VEGGIES),
            new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE),
            new Ingredient("JACK", "Monterrey Jack", Ingredient.Type.CHEESE),
            new Ingredient("SLSA", "Salsa", Ingredient.Type.SAUCE),
            new Ingredient("SRCR", "Sour Cream", Ingredient.Type.SAUCE)
    };
    private static Logger log = LoggerFactory.getLogger(EmailOrderTransformer.class);
    private static final String SUBJECT_KEYWORDS = "TACO ORDER";
    @Override
    protected AbstractIntegrationMessageBuilder<EmailOrder> doTransform(Message mailMessage) throws Exception {
        EmailOrder tacoOrder = processPayLoad(mailMessage);
        return MessageBuilder.withPayload(tacoOrder);
    }

    private EmailOrder processPayLoad(Message mailMessage) {
        try {
            String subject = mailMessage.getSubject();
            if (subject.toUpperCase().contains(SUBJECT_KEYWORDS)){
                String email = ((InternetAddress) mailMessage.getFrom()[0]).getAddress();
                String content = mailMessage.getContent().toString();
                return parseEmailToOrder(email, content);
            }
        }catch (MessagingException e){
            log.error("MessagingException: ", e);
        }catch (IOException e){
            log.error("IOException: ", e);
        }
        return null;
    }

    private EmailOrder parseEmailToOrder(String email, String content) {
        EmailOrder order = new EmailOrder(email);
        String[] lines = content.split("\\r?\\n");
        for (String line : lines){
            if (!line.trim().isEmpty() && line.contains(":")){
                String[] lineSplit = line.split(":");
                String tacoName = lineSplit[0].trim();
                Taco taco = getTaco(lineSplit, tacoName);
                order.addTaco(taco);
            }
        }
        return null;
    }

    private Taco getTaco(String[] lineSplit, String tacoName) {
        String ingredients = lineSplit[1].trim();
        String[] ingredientsSplit = ingredients.split(",");
        List<String> ingredientCodes = new ArrayList<>();
        for (String ingredientName : ingredientsSplit){
            String code = lookupIngredientCode(ingredientName.trim());
            if (code != null){
                ingredientCodes.add(code);
            }
        }
        Taco taco = new Taco(tacoName);
//        taco.setIngredients(ingredientCodes);  // TODO
        return taco;
    }

    private String lookupIngredientCode(String ingredientName) {
        for (Ingredient ingredient : ALL_INGREDIENTS){
            String ucIngredientName = ingredientName.toUpperCase();
            if (LevenshteinDistance.getDefaultInstance().apply(ucIngredientName, ingredient.getName()) < 3 ||
            ucIngredientName.contains(ingredient.getName()) || ingredient.getName().contains(ucIngredientName)){
                return ingredient.getId();
            }
        }
        return null;
    }
}
