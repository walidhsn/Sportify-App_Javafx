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
import com.stripe.param.PaymentMethodCreateParams;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author WALID
 */
public class PaymentProcessor {

    private static final String STRIPE_API_KEY = "sk_test_51MiLxXDj8DllmHoGdij9Kn3ssUzp6qdna36XzuWr5Lbq5jKb2TnMkygJ6bCOU45gFmrZz8xtnUB1NWGGJSZPXROP00bZcx8mmF"; // your API secret key

    public static boolean processPayment(String name,String email, int amount, String cardNumber,Integer cardExpMonth, Integer cardExpYear, String cardCvc) throws StripeException {
        boolean result = false;
        // Set your secret key
        Stripe.apiKey = STRIPE_API_KEY;
        
       Map<String, Object> customerParams = new HashMap<>();
        customerParams.put("name", name);
        customerParams.put("email", email);
        Customer customer = Customer.create(customerParams);

        // Create payment method
        PaymentMethodCreateParams.CardDetails card = PaymentMethodCreateParams.CardDetails.builder()
                .setNumber(cardNumber)
                .setExpMonth(cardExpMonth.longValue())
                .setExpYear(cardExpYear.longValue())
                .setCvc(cardCvc)
                .build();

        PaymentMethodCreateParams paymentMethodParams = PaymentMethodCreateParams.builder()
                .setCard(card)
                .setType(PaymentMethodCreateParams.Type.CARD)
                .build();

        PaymentMethod paymentMethod = PaymentMethod.create(paymentMethodParams);

        // Attach payment method to customer
        Map<String, Object> attachParams = new HashMap<>();
        attachParams.put("customer", customer.getId());
        paymentMethod.attach(attachParams);

        // Create the charge object
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", amount);
        chargeParams.put("currency", "eur");
        chargeParams.put("customer", customer.getId());
        chargeParams.put("payment_method", paymentMethod.getId());

        // Charge the customer's card
        Charge charge = Charge.create(chargeParams);

        // Check the charge status
        if (charge.getStatus().equals("succeeded")) {
            // Payment successful
            result =true;
        } else {
            // Payment failed
            result =false;
        }

        return result;
    }
}
