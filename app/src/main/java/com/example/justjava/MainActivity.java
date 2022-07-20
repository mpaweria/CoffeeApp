package com.example.justjava;



import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    int quantity = 2;
    public void submitOrders(View view) {

        EditText userNameText = (EditText) findViewById(R.id.user_name);
        String userName = userNameText.getText().toString();

        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String message = orderSummary(price, hasWhippedCream, hasChocolate, userName);

        String subject = "Just Java order for " + userName;

        composeEmail(message, subject);
    }

    public void composeEmail(String content, String subject) {

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_TEXT, content);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void increment(View view) {
        if(quantity >= 100) {
            Toast.makeText(this, "You can not order more than 100 cups of coffee!", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    public void decrement(View view) {
        if(quantity <= 1) {
            Toast.makeText(this, "You can not order less than 1 cup of coffee!", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int quan) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + quan);
    }
    /**
     * This method calculates the price of coffee.
     */
    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        int price = 5;
        if(hasWhippedCream)
            price+=1;
        if(hasChocolate)
            price+=2;
        return quantity * price;
    }
    /**
     * This method creates the order summary
     */
    private String orderSummary(int price, boolean hasWhippedCream, boolean hasChocolate, String name) {
        String message = "Name: " + name + "\n";
        message += "Add Whipped Cream? " +  hasWhippedCream;
        message += "\nAdd Chocolate? " +  hasChocolate;
        message += "\nQuantity: " + quantity + "\n";
        message += "Total: " + NumberFormat.getCurrencyInstance().format(price);
        message += "\nThank You!";

        return message;
    }
}