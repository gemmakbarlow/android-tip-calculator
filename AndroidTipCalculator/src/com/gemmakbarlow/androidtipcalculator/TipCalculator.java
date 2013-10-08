package com.gemmakbarlow.androidtipcalculator;

import java.math.BigDecimal;
import java.util.Locale;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TipCalculator extends Activity {

	final int NUMBER_OF_DECIMAL_PLACES_FOR_USD = 2;
	final float TEN_PERCENT_FLOAT_MULTIPLIER = 0.10f;
	final float FIFTEEN_PERCENT_FLOAT_MULTIPLIER = 0.15f;
	final float TWENTY_PERCENT_FLOAT_MULTIPLIER = 0.20f;
	final float MIN_COST_VALUE = 1.0f;
	final float MAX_COST_VALUE = 1000000.0f;
	
	private EditText etEnterCost;
	private TextView tvFinalTip;
	private TextView tvOverallCost;
	private TextView tvInvalidCostEntered;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_calculator);
        
        // Initialize UI elements
        setEtEnterCost((EditText)findViewById(R.id.etTipInput));
        setTvFinalTip((TextView)findViewById(R.id.tvFinalTipValue));
        setTvOverallCost((TextView)findViewById(R.id.tvOverallValue));
        setTvInvalidCostEntered((TextView)findViewById(R.id.tvInvalidCostEntered));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tip_calculator, menu);
        return true;
    }

    private void calculateFinalTipFromPercentage(float percentageMultiplier) {
    	
    	// Only do something if a cost has been entered
    	String cost = this.etEnterCost.getText().toString();
    	if(cost.length() > 0) {
    		
    		float costValue = Float.parseFloat(cost);
    		if(costValue > MIN_COST_VALUE && costValue <= MAX_COST_VALUE) {
    			float tipValue = costValue * percentageMultiplier;
    			float roundedTipValue = round(tipValue, NUMBER_OF_DECIMAL_PLACES_FOR_USD);
    			
    			String finalTipText = String.format(Locale.US, "$ \t%.2f USD", roundedTipValue); 
    			this.tvFinalTip.setText(finalTipText);
    			
    			float roundedOverallValue = round(costValue + tipValue, NUMBER_OF_DECIMAL_PLACES_FOR_USD);
    			String finalOverallText = String.format(Locale.US, "$ \t%.2f USD", roundedOverallValue);
    			this.tvOverallCost.setText(finalOverallText);
    		}
    		else {
    			showInvalidCostToast();
    		}
    	}
    	else {
    		showInvalidCostToast();
    	}
    }
    
    
    /*
     * Helper Methods
     */
    
    private void resetInput() {
    	this.etEnterCost.setText("");
    	this.tvFinalTip.setText("");
    	this.tvOverallCost.setText("");
    }
    
    private void showInvalidCostToast() {
    	Context context = getApplicationContext();
    	int duration = Toast.LENGTH_SHORT;
    	Toast toast = Toast.makeText(context, R.string.tvInvalidValueEnteredString, duration);
    	toast.setGravity(Gravity.TOP|Gravity.CENTER, 0, 100);
    	toast.show();
    }
    
    /**
     * From http://bit.ly/17aht8Y
     * 
     * @param valueToRound Value to round up or down
     * @param numDecimalPlaces Number of decimal places desired in result. 
     * @return float rounded to the correct number of decimal places
     */
    public static float round(float valueToRound, int numDecimalPlaces) {
        BigDecimal bd = new BigDecimal(Float.toString(valueToRound));
        bd = bd.setScale(numDecimalPlaces, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }
    
    /*
     * Actions
     */
    
    /** Called when the user touches the button */
    public void btn10PercentTapped(View button) {
        calculateFinalTipFromPercentage(this.TEN_PERCENT_FLOAT_MULTIPLIER);
    }
    
    /** Called when the user touches the button */
    public void btn15PercentTapped(View button) {
    	calculateFinalTipFromPercentage(this.FIFTEEN_PERCENT_FLOAT_MULTIPLIER);
    }
    
    
    /** Called when the user touches the button */
    public void btn20PercentTapped(View button) {
        calculateFinalTipFromPercentage(this.TWENTY_PERCENT_FLOAT_MULTIPLIER);
    }
    
    /** Called when the user touches the button */
    public void btnClearInputTapped(View button) {
        resetInput();
    }
   
    
    /*
     * Getters / Setters
     */

	public EditText getEtEnterCost() {
		return etEnterCost;
	}


	public void setEtEnterCost(EditText etEnterCost) {
		this.etEnterCost = etEnterCost;
	}


	public TextView getTvFinalTip() {
		return tvFinalTip;
	}


	public void setTvFinalTip(TextView tvFinalTip) {
		this.tvFinalTip = tvFinalTip;
	}


	public TextView getTvOverallCost() {
		return tvOverallCost;
	}


	public void setTvOverallCost(TextView tvOverallCost) {
		this.tvOverallCost = tvOverallCost;
	}


	public TextView getTvInvalidCostEntered() {
		return tvInvalidCostEntered;
	}


	public void setTvInvalidCostEntered(TextView tvInvalidCostEntered) {
		this.tvInvalidCostEntered = tvInvalidCostEntered;
	}
    
}
