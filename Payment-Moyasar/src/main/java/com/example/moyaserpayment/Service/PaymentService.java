package com.example.moyaserpayment.Service;

import com.example.moyaserpayment.Model.PaymentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class PaymentService {

    @Value("${moyasar.api.key}")
    private String apiKey;

    private static final String MOYASAR_API_URL = "https://api.moyasar.com/v1/payments/";

    public ResponseEntity<String> processPayment(PaymentRequest paymentRequest){

        String url= "https://api.moyasar.com/v1/payments";

        // your server endpoint
        String callbackUrl= "https://aa/callback";

        String requestBody = String.format(
                "source[type]=creditcard&" +
                        "source[name]=%s&" +
                        "source[number]=%s&" +
                        "source[month]=%d&" +
                        "source[year]=%d&" +
                        "source[cvc]=%d&" +
                        "amount=%d&" +
                        "currency=%s&" +
                        "description=%s&" +
                        "callback_url=%s",
                paymentRequest.getName(),
                paymentRequest.getNumber(),
                paymentRequest.getMonth(),
                paymentRequest.getYear(),
                paymentRequest.getCvc(),
                (int)(paymentRequest.getAmount() * 100),
                paymentRequest.getCurrency(),
                paymentRequest.getDescription(),
                paymentRequest.getCallbackUrl()
        );

        // set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(apiKey,"");
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<String> entity = new HttpEntity<>(requestBody,headers);

        //send the request
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST,entity,String.class);

        //return the api response

        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());

    }


    public String getPaymentStatus(String payment_id){

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(apiKey,"");
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(MOYASAR_API_URL + payment_id,
                HttpMethod.POST,entity, String.class);

        return response.getBody();
    }


}
