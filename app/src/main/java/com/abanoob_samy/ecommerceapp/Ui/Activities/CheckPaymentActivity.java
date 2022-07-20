package com.abanoob_samy.ecommerceapp.Ui.Activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.abanoob_samy.ecommerceapp.databinding.ActivityPaymentBinding;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

public class CheckPaymentActivity extends AppCompatActivity {

    private static final String TAG = CheckPaymentActivity.class.getSimpleName();

    private static String clientKey = "AdC2yDw8k1coFLYMC9FW2doOgLVJpyAo4Ng5W1MxbIA6LL-d_ODhFITUl6_uxUgRZnCgsNUAfllxFJHT";

    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_PRODUCTION)
            .clientId(clientKey)
            .acceptCreditCards(false)
            .rememberUser(true);


    private ActivityPaymentBinding binding;

    double subTotalShipping = 9.0;
    double totalAmount = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        double amount;
        amount = getIntent().getDoubleExtra("amount", 0.0);

        totalAmount = amount + subTotalShipping;

        binding.subTotal.setText("$" + amount);
        binding.subTotalShipping.setText("$" + subTotalShipping);
        binding.totalAmt.setText("$ " + totalAmount);

        binding.payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setUpPaymentMethod();
            }
        });
    }

    private void setUpPaymentMethod() {

        // Getting the amount from editText
//        String amount = amountEdt.getText().toString();

        // Creating a paypal payment on below line.
        PayPalPayment payment = new PayPalPayment(new BigDecimal(String.valueOf(/*amount*/10)),
                "USD", "Course Fees",
                PayPalPayment.PAYMENT_INTENT_SALE);

        // Creating Paypal Payment activity intent
        Intent intent = new Intent(this, PaymentActivity.class);

        //putting the paypal configuration to the intent
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        // Putting paypal payment to the intent
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);

        // Starting the intent activity for result
        // the request code will be used on the method onActivityResult
        someActivityResultLauncher.launch(intent);

    }

    // You can do the assignment inside onAttach or onCreate, i.e, before the activity is displayed
    private ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                    Intent data = result.getData();

                    if (result.getResultCode() == Activity.RESULT_OK) {

                        // Getting the payment confirmation
                        PaymentConfirmation confirm = data.getParcelableExtra(
                               PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                        // if confirmation is not null
                        if (confirm != null) {
                            try {
                                // Getting the payment details
                                String paymentDetails = confirm.toJSONObject().toString(4);
                                // on below line we are extracting json response and displaying it in a text view.
                                JSONObject payObj = new JSONObject(paymentDetails);
                                String payID = payObj.getJSONObject("response").getString("id");
                                String state = payObj.getJSONObject("response").getString("state");
                                binding.totalAmt.setText("Payment " + state + "\n with payment id is " + payID);
                            } catch (JSONException e) {
                                // handling json exception on below line
                                Log.e("Error", "an extremely unlikely failure occurred: ", e);
                                Toast.makeText(getApplicationContext(),
                                        "an extremely unlikely failure occurred: " + e, Toast.LENGTH_SHORT).show();
                            }
                            catch (IllegalStateException e) {
                                // handling json exception on below line
                                Log.e("Error", "an extremely unlikely failure occurred: ", e);
                                Toast.makeText(getApplicationContext(),
                                        "an extremely unlikely failure occurred: " + e, Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else if (result.getResultCode() == Activity.RESULT_CANCELED) {
                        // on below line we are checking the payment status.
                        Log.i("paymentExample", "The user canceled.");

                        Toast.makeText(getApplicationContext(),
                                "The user canceled.", Toast.LENGTH_SHORT).show();

                    } else if (result.getResultCode() ==
                            PaymentActivity.RESULT_EXTRAS_INVALID) {
                        // on below line when the invalid paypal config is submitted.
                        Log.i("paymentExample",
                                "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");

                        Toast.makeText(getApplicationContext(),
                                "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.", Toast.LENGTH_SHORT).show();

                    }
                }
            });

}