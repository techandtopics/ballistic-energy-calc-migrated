/* 	   Ballistic Energy Calculator - calculates muzzle energy and Taylor Knock Out Factor
 *         Copyright (C) 2011 George Yauneridge
 * 
 *         This program is free software: you can redistribute it and/or modify
 *         it under the terms of the GNU General Public License as published by
 *         the Free Software Foundation, either version 3 of the License, or (at
 *         your option) any later version.
 * 
 *         This program is distributed in the hope that it will be useful, but
 *         WITHOUT ANY WARRANTY; without even the implied warranty of
 *         MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 *         General Public License for more details.
 * 
 *         You should have received a copy of the GNU General Public License
 *         along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.blogspot.techandtopics;

import java.math.BigDecimal;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Ballistic Energy Calculator Source and support can be found at
 * <http://techandtopics.blogspot.com>. Version: 1.0 Target device: HTC
 * Incredible API 8
 * 
 * This program calculates the muzzle energy and Taylor KO Factor based on the
 * mass, velocity and diameter input by the user.
 * 
 * @author George Yauneridge
 * 
 */

public class EnergyCalc extends Activity {

	private EditText velocityEntry; // gets velocity input
	private EditText massEntry; // gets mass input
	private EditText diameterEntry; // gets diameter input
	
	private TextView labelME; // label for muzzle energy
	private TextView labelMomentum; // label for momentum
	
	private TextView displayTKO; // displays calculated TKO
	private TextView displayME; // displays calculated Energy
	private TextView displayMomentum; // displays calculated Momentum
	
	public static final String MASS_TYPE_GRAMS = "g";
	public static final String VELOCITY_TYPE_METERS = "m/s";
	public static final String ME_TYPE_JOULES = "J";
	public static final String DIAMETER_TYPE_MILLIMETERS = "mm";
	
	public boolean isMetric = false;

	/**
	 * Set layout
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		velocityEntry = (EditText) findViewById(R.id.velocityEntry);
		massEntry = (EditText) findViewById(R.id.massEntry);
		diameterEntry = (EditText) findViewById(R.id.diameterEntry);

		labelME	= (TextView) findViewById(R.id.labelME);
		labelMomentum = (TextView) findViewById(R.id.labelMomentum);
		
		displayTKO = (TextView) findViewById(R.id.displayTKO);
		displayME = (TextView) findViewById(R.id.displayME);
		displayMomentum = (TextView) findViewById(R.id.displayMomentum);
	}

	/**
	 * Override to save the displayTKO and displayME textView values that were
	 * generate during runtime.
	 */
	@Override
	protected void onSaveInstanceState(Bundle savedInstanceState) {
		savedInstanceState.putString("bundleValueTKO",
				(String) displayTKO.getText());
		savedInstanceState.putString("bundleValueME",
				(String) displayME.getText());
		savedInstanceState.putString("bundleValueMomentum",
				(String) displayMomentum.getText());
		savedInstanceState.putBoolean("bundleIsMetric",isMetric);
		super.onSaveInstanceState(savedInstanceState);
	}

	/**
	 * 
	 */
	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		displayTKO.setText(savedInstanceState.getString("bundleValueTKO"));
		displayME.setText(savedInstanceState.getString("bundleValueME"));
		displayMomentum.setText(savedInstanceState.getString("bundleValueMomentum"));
		isMetric = savedInstanceState.getBoolean("bundleIsMetric");
		setLabels();
	}

	/**
	 * Inflate the menu from R.layout.menu.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.layout.menu, menu);
		return true;
	}

	/**
	 * Display content based on menu item selected.
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_about:
			showAbout();
			return true;
		case R.id.menu_help:
			showHelp();
			return true;
		case R.id.menu_metric:
			switchUnitTypes();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * Displays a dialog box with the about message. Called by about button in
	 * the main.xml.
	 */
	public void showAbout(View view) {
		showAbout();
	}

	/**
	 * Displays a dialog box with the about message.
	 */
	public void showAbout() {

		AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
		alertbox.setMessage(R.string.about_content); 

		alertbox.setNeutralButton(R.string.ok, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {

			}
		});
		alertbox.show();
	}

	/**
	 * Displays a dialog box with the help message. Used by help button in the
	 * main.xml.
	 */
	public void showHelp(View view) {
		showHelp();
	}

	/**
	 * Displays a dialog box with the help message.
	 */
	public void showHelp() {

		AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
		if(isMetric == true){
			alertbox.setMessage(R.string.metric_help_content);
		}else{
			alertbox.setMessage(R.string.help_content);
		}

		alertbox.setNeutralButton(R.string.ok, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {

			}
		});
		alertbox.show();
	}
	
	/**
	 * Displays a dialog box with the error message.
	 */
	public void showError() {

		AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
		alertbox.setMessage(R.string.error_content);

		alertbox.setNeutralButton(R.string.ok, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {

			}
		});
		alertbox.show();
	}
	
	/**
	 * Displays a dialog box with the help message. Used by switch unit types button.
	 */
	public void switchUnitTypes() {

		AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
		if(!isMetric){
			alertbox.setMessage(R.string.switch_to_metric);
		}else{
			alertbox.setMessage(R.string.switch_to_english);
		}

		alertbox.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface arg0, int arg1) {
				isMetric = !isMetric;
				blankAllFields();
				setLabels();
			}
		});
		
		alertbox.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface arg0, int arg1) {
				
			}
		});
		
		alertbox.show();
	}
	
	/**
	 * Displays a dialog box with the help message.
	 */
	public void switchUnitTypes(View view) {
		switchUnitTypes();
	}

	/**
	 * Closes the software keyboard, sets the variables, computes and displays
	 * the result.
	 * 
	 * @param View
	 *            v
	 * @throws NumberFormatException
	 * @throws ArithmeticException
	 * @throws IllegalArgumentException
	 */
	public void calculate(View v) throws NumberFormatException,
			ArithmeticException, IllegalArgumentException {
		BigDecimal resultMomentumMetric = new BigDecimal("0");
			
		InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
		// close soft keyboard
		if (!isValid(massEntry) || !isValid(velocityEntry)){
			showError();
			blankOutputFields();
		} else {
			BigDecimal velocity = new BigDecimal(velocityEntry.getText().toString());
			BigDecimal mass = new BigDecimal(massEntry.getText().toString());
			BigDecimal grainsInPound = new BigDecimal("7000");
			BigDecimal resultMomentum = new BigDecimal("0");
		
			if(isMetric == true){ //calculate momentum in kg*m/s, convert velocity and mass to English units for use with ME formula
				resultMomentum = velocity.multiply(mass);
				resultMomentum = resultMomentum.divide(new BigDecimal("1000"),3,0);
				
				velocity = convertInputFromMetric(velocity, VELOCITY_TYPE_METERS);
				mass = convertInputFromMetric(mass, MASS_TYPE_GRAMS);			
			}else{
				// (mass/7000) * velocity = momentum ft/lbs
				resultMomentum = mass.divide(grainsInPound,10,0);
				resultMomentum = velocity.multiply(resultMomentum);
			}
			displayMomentum.setText(myFormat(resultMomentum.toString()));
			
			// mass/2 * velocity^2 / (7000*32.163) = muzzle energy
			BigDecimal result = mass.divide(new BigDecimal("2"),10,0);
			result = result.multiply(velocity.pow(2));		
			result = result.divide(new BigDecimal("225141"),3,0); // 3=scale, 0=ROUND_UP
			
			if(isMetric == true){
				result = convertOutputToMetric(result, ME_TYPE_JOULES);
			}
			displayME.setText(myFormat(result.toString()));
			
			if (!isValid(diameterEntry)) {
				displayTKO.setText("-");
			} else {
				BigDecimal diameter = new BigDecimal(diameterEntry.getText().toString());
				if(isMetric == true){					
					diameter = convertInputFromMetric(diameter,DIAMETER_TYPE_MILLIMETERS);
				}
				result = mass.multiply(velocity);
				result = result.multiply(diameter);
				result = result.divide(new BigDecimal("7000"),3,0);
				
				displayTKO.setText(myFormat(result.toString()));
				
			}
		}
	}

	/**
	 * Formats the decimal result to two decimal places.
	 * 
	 * @param String
	 *            numberString
	 * @return String
	 */
	static public String myFormat(String numberString) {
		int indexDot = numberString.indexOf(".");
		if (numberString.contains(".")
				&& (numberString.length() - indexDot) > 3) {
			return numberString.substring(0, indexDot + 3);
		} else {
			return numberString;
		}
	}
	
	/**
	 * Checks to see if an EditText is valid
	 * @param EditText field
	 * @return true if valid
	 */
	public boolean isValid(EditText field){
		if ((field.getText().toString()).equalsIgnoreCase("")
				|| (field.getText().toString()).equalsIgnoreCase(" ")
				|| (field.getText().toString()).equalsIgnoreCase(".")) {			
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * Converts inputs from metric values to standard values for use with the firearms
	 * industry standard formula for muzzle energy.
	 * @param BigDecimal var
	 * @param String originalUnit
	 * @return BigDecimal result
	 */
	public BigDecimal convertInputFromMetric(BigDecimal var, String originalUnit){
		BigDecimal result = new BigDecimal("0");
		
		if(originalUnit.equalsIgnoreCase(MASS_TYPE_GRAMS)){
			BigDecimal grains = new BigDecimal("15.4323584");	//1 Gram = 15.4323584 Grains
			result = var.multiply(grains);		
		}
		if(originalUnit.equalsIgnoreCase(VELOCITY_TYPE_METERS)){
			BigDecimal fps = new BigDecimal("3.28084");			//1.0 m/s= 3.28084 fps
			result = var.multiply(fps);		
		}
		if(originalUnit.equalsIgnoreCase(DIAMETER_TYPE_MILLIMETERS)){
			BigDecimal inch = new BigDecimal(".039370078740157");	//1 mm = .039370078740157 inch
			result = var.multiply(inch);		
		}
		return result;
	}
	
	/**
	 * Converts outputs from standard values to metric values after computation.
	 * @param BigDecimal var
	 * @param String outputUnit
	 * @return BigDecimal result
	 */
	public BigDecimal convertOutputToMetric(BigDecimal var, String outputUnit){
		BigDecimal result = new BigDecimal("0");		
		
		if(outputUnit.equalsIgnoreCase(ME_TYPE_JOULES)){
			BigDecimal joules = new BigDecimal("1.35581795");		//ft-lbs x 1.35581795 = Joules
			result = var.multiply(joules);			
		}
		return result;
		
	}
	
	public void blankAllFields(){
		velocityEntry.setText("");
		massEntry.setText("");
		diameterEntry.setText("");
		blankOutputFields();
	}
	
	public void blankOutputFields(){
		displayTKO.setText("-");
		displayME.setText("-");
		displayMomentum.setText("-");
	}
	
	public void setLabels(){
		if(isMetric == true){
			velocityEntry.setHint(R.string.metric_velocity_hint);
			massEntry.setHint(R.string.metric_mass_hint);				
			diameterEntry.setHint(R.string.metric_diameter_hint);
			labelME.setText(R.string.metric_energy);
			labelMomentum.setText(R.string.metric_momentum);
		}else{
			velocityEntry.setHint(R.string.velocity_hint);
			massEntry.setHint(R.string.mass_hint);				
			diameterEntry.setHint(R.string.diameter_hint);
			labelME.setText(R.string.energy);
			labelMomentum.setText(R.string.momentum);
		}
	}
}