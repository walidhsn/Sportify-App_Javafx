/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sportify.edu.services;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.PaymentMethod;
import com.stripe.model.Token;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.CustomerRetrieveParams;
import com.stripe.param.PaymentMethodAttachParams;
import com.stripe.param.PaymentMethodCreateParams;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author WALID
 */
public class PaymentProcessor {
    
    private static final String STRIPE_API_KEY = "sk_test_51MiLxXDj8DllmHoGdij9Kn3ssUzp6qdna36XzuWr5Lbq5jKb2TnMkygJ6bCOU45gFmrZz8xtnUB1NWGGJSZPXROP00bZcx8mmF"; // your API secret key

    public static boolean processPayment(String name, String email, int amount, String cardNumber, int cardExpMonth, int cardExpYear, String cardCvc) throws StripeException {
        boolean result = false;
        // Set your secret key
        Stripe.apiKey = STRIPE_API_KEY;

        // Create a Customer
        Map<String, Object> customerParameter = new HashMap<String, Object>();
        customerParameter.put("name", name);
        customerParameter.put("email", email);
        Customer client = Customer.create(customerParameter);

        // Create a Card
        Map<String, Object> cardParameter = new HashMap<String, Object>();
        cardParameter.put("number", cardNumber);
        cardParameter.put("exp_month", cardExpMonth);
        cardParameter.put("exp_year", cardExpYear);
        cardParameter.put("cvc", cardCvc);
        // Create a Token 
        Map<String, Object> tokenParameter = new HashMap<String, Object>();
        tokenParameter.put("card", cardParameter);
        Token token = Token.create(tokenParameter);
        // Create a Source 
        Map<String, Object> sourceParameter = new HashMap<String, Object>();
        sourceParameter.put("source", token.getId());
        CustomerRetrieveParams params = CustomerRetrieveParams.builder()
                .addExpand("sources").build();
        Customer stripeCustomer = Customer.retrieve(String.valueOf(client.getId()), params, null);
        stripeCustomer.getSources().create(sourceParameter);
        // Create a Charge
        Map<String, Object> chargeParameter = new HashMap<String, Object>();
        chargeParameter.put("amount", amount);
        chargeParameter.put("currency", "eur");
        chargeParameter.put("customer", client.getId());
        Charge charge = Charge.create(chargeParameter);
        // Check if the charge was successful
        if (charge.getStatus().equals("succeeded")) {
            System.out.println("Payment successful!");
            result = true;
        } else {
            System.out.println("Payment failed!");
        }
        return result;
    }
    
}
