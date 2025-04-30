package com.example.moyaserpayment.Controller;

import com.example.moyaserpayment.Model.PaymentRequest;
import com.example.moyaserpayment.Service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/card")
    public ResponseEntity<ResponseEntity<String>> processPayment(@RequestBody PaymentRequest paymentRequest){
        return ResponseEntity.status(200).body(paymentService.processPayment(paymentRequest));
    }


    @GetMapping("/get-status/{id}")
    public ResponseEntity getPaymentStatus(@PathVariable String id){
        return ResponseEntity.status(200).body(paymentService.getPaymentStatus(id));
    }

    @GetMapping("/callback")
    public ResponseEntity callbackUrl(){
        return ResponseEntity.status(200).body("Paid !!");
    }
}
