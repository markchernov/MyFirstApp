package com.markche.myfirstapp;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int myQuantity = 0;
    int myPrice = 5;
    String myName = "";
    boolean myCheckedStateWhipped = false;
    boolean myCheckedStateChocolate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        displayQuantity(myQuantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        displayQuantity(myQuantity);
        int total = calculatePrice(myPrice,myQuantity);
        String orderMessage = displayOrder(total);
        Context context = getApplicationContext();
        Toast myToast = Toast.makeText(context,"Thanks:" + myName,Toast.LENGTH_SHORT);
        myToast.show();
        // intent to maps
        //Intent myIntent = new Intent(Intent.ACTION_VIEW);
        //myIntent.setData(Uri.parse("geo:47.6, -122.3"));
        Intent myIntent = new Intent(Intent.ACTION_SENDTO);
        myIntent.setData(Uri.parse("mailto:"));
        myIntent.putExtra(Intent.EXTRA_EMAIL, "korolvlondone@gmail.com");
        myIntent.putExtra(Intent.EXTRA_SUBJECT,"Java Order");
        myIntent.putExtra(Intent.EXTRA_TEXT,orderMessage);
        if(myIntent.resolveActivity(getPackageManager())!= null)  {startActivity(myIntent);}

    }
    public int calculatePrice(int myPrice, int myQuantity) {

        CheckBox myCheckBox = (CheckBox)findViewById(R.id.notify_me_checkbox);
        myCheckedStateWhipped = myCheckBox.isChecked();
        CheckBox myCheckBoxChocolate = (CheckBox)findViewById(R.id.chocolate_checkbox);
        myCheckedStateChocolate = myCheckBoxChocolate.isChecked();

        if (myQuantity == 0)
        {   Context context = getApplicationContext();
            Toast myToast = Toast.makeText(context, myName + "Please add a drink:" ,Toast.LENGTH_SHORT);
            myToast.show();
            return 0;
        }

        else {
        int extras = 0;
        if(myCheckedStateChocolate) extras += 2;
        if(myCheckedStateWhipped) extras += 1;
        return myPrice * myQuantity + (extras * myQuantity);  }
    }

    /**
     * This method displays the given price on the screen.
     */
    private String displayOrder(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.order_summary_text_view);
        EditText myNameText = (EditText) findViewById(R.id.enter_name_view);
        myName = myNameText.getText().toString();

        String message = "Name: " + myName + "\n" +
                "Add whipped cream? " + myCheckedStateWhipped + "\n"+
                "Add chocolate? " + myCheckedStateChocolate + "\n"+
                "Quantity: " + myQuantity + "\n" +
                "Total: " + NumberFormat.getCurrencyInstance().format(number) +"\n";
        priceTextView.setText(message);

        return message;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText(String.valueOf(number));
        //quantityTextView.setText(new Integer(number).toString());
    }

    public void increment(View view){

        if(myQuantity == 10) {

            Context context = getApplicationContext();
            Toast myToast = Toast.makeText(context, myName + "10 is max order:" ,Toast.LENGTH_SHORT);
            myToast.show();
            displayQuantity(myQuantity);
        }

        else {
            myQuantity++;
            displayQuantity(myQuantity);
        }
    }

    public void decrement(View view){

        if(myQuantity > 0) {
            myQuantity--;
            displayQuantity(myQuantity);
        }
            else {
            Context context = getApplicationContext();
            Toast myToast = Toast.makeText(context, myName + " your order is 0 cups:" ,Toast.LENGTH_SHORT);
            myToast.show();
            displayQuantity(myQuantity);
        }
    }
}
