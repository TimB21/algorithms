package com.application.algorithms.lens;

import java.util.ArrayList;
import java.util.Arrays;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "lens")
public class Lens {

	private int id;
    private double rSquared;
    private double transmissionAvg;
    private double[] transmissions;
	private double[] copyTransmission;
    private double[] absorptionSpectrum;
	private double[] copyAbsorption;
    private double eyeProtectionFactor;
    private double melatoninProductionFactor;
    private double glareReductionFactor;
	private String name; 


	private ArrayList<Double> wavelengthValues = new ArrayList<>(Arrays.asList(
		380.0, 390.0, 400.0, 410.0, 420.0, 430.0, 440.0, 450.0,
		460.0, 470.0, 480.0, 490.0, 500.0, 510.0, 520.0, 530.0,
		540.0, 550.0, 560.0, 570.0, 580.0, 590.0, 600.0, 610.0,
		620.0, 630.0, 640.0, 650.0, 660.0, 670.0, 680.0, 690.0,
		700.0, 710.0, 720.0, 730.0, 740.0, 750.0
	));

    public Lens(int id, String name, double[] transmissions, double[] absorptionSpectrum) {
        this.id = id;
		this.name = name;
        // this.rSquared = rSquared;
        // this.transmissionAvg = transmissionAvg;
        this.transmissions = transmissions;
		copyTransmission = transmissions;
        this.absorptionSpectrum = absorptionSpectrum;
		copyAbsorption = absorptionSpectrum;
		initialize();
        // this.eyeProtectionFactor = eyeProtectionFactor;
        // this.melatoninProductionFactor = melatoninProductionFactor;
        // this.glareReductionFactor = glareReductionFactor;

		// this.rSquared = calculateRSquared();
        // this.transmissionAvg = calculateTransmissionAvg();
        // this.eyeProtectionFactor = calculateEPF();
        // this.melatoninProductionFactor = calculateMPF();
        // this.glareReductionFactor = calculateGRF();

		// // Print array lengths for verification
		// System.out.println("Length of copy array: " + copyTransmission.length);
		// System.out.println("Length of absorption spectrum array: " + copyAbsorption.length);
    }

	public void initialize() {
		this.transmissionAvg = calculateTransmissionAvg();
        this.rSquared = calculateRSquared();
        this.eyeProtectionFactor =calculateEPF();
        this.melatoninProductionFactor =calculateMPF();
        this.glareReductionFactor = calculateGRF();
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRSquared() {
        return rSquared;
    }

    public void setRSquared(double rSquared) {
        this.rSquared = rSquared;
    }

    public double getTransmissionAvg() {
        return copyTransmission[17];
    }

    public void setTransmissionAvg(double transmissionAvg) {
        this.transmissionAvg = transmissionAvg;
    }

    public double[] getTransmissions() {
        return transmissions;
    }

    public void setTransmissions(double[] transmissions) {
        this.transmissions = transmissions;
    }

    public double[] getAbsorptionSpectrum() {
        return absorptionSpectrum;
    }

    public void setAbsorptionSpectrum(double[] absobtionSpectrum) {
        this.absorptionSpectrum = absobtionSpectrum;
    }

    public double getEyeProtectionFactor() {
        return eyeProtectionFactor;
    }

    public void setEyeProtectionFactor(double eyeProtectionFactor) {
        this.eyeProtectionFactor = eyeProtectionFactor;
    }

    public double getMelatoninProductionFactor() {
        return melatoninProductionFactor;
    }

    public void setMelatoninProductionFactor(double melatoninProductionFactor) {
        this.melatoninProductionFactor = melatoninProductionFactor;
    }

    public double getGlareReductionFactor() {
        return glareReductionFactor;
    }

    public void setGlareReductionFactor(double glareReductionFactor) {
        this.glareReductionFactor = glareReductionFactor;
    }

    /** 
	 * Calculates sum of an array list
     * Overload findSumalculates
     * 
     * @param int[] array - array of values
     * @return sum of array lists values
     */ 
	private static double findSum(ArrayList<Double> array) {
        double sum = 0; 
        for (double i : array) {	
            sum += i;
        } 
        return sum;
	}     

	public static double[] linearSquareRegression(ArrayList<Double> xValues, ArrayList<Double> yValues) {
		// Get the number of data points
		int n = xValues.size();

		// Initialize variables to hold the sums of x, y, x^2, and x*y
		double sumX = 0;
		double sumY = 0;
		double sumXX = 0;
		double sumXY = 0;

		// Loop through the data points and calculate the sums
		for (int i = 0; i < n; i++) {
			double x = xValues.get(i);
			double y = yValues.get(i);
			sumX += x;
			sumY += y;
			sumXX += x * x;
			sumXY += x * y;
		}

		// Calculate the means of x and y
		double xMean = sumX / n;
		double yMean = sumY / n;

		// Calculate the slope of the linear square regression line
		double slope = (n * sumXY - sumX * sumY) / (n * sumXX - sumX * sumX);

		// Calculate the intercept of the linear square regression line
		double intercept = yMean - slope * xMean;

		// Return the intercept and slope as an array
		return new double[]{intercept, slope};
	}

	public static double calculateR(ArrayList<Double> xValues, ArrayList<Double> yValues, double intercept, double slope) 
	{
		int n = xValues.size();
		double sumX = 0;
		double sumY = 0;
		double sumXX = 0;
		double sumYY = 0;
		double sumXY = 0;
		for (int i = 0; i < n; i++) {
			double x = xValues.get(i);
			double y = yValues.get(i);
			sumX += x;
			sumY += y;
			sumXX += x * x;
			sumYY += y * y;
			sumXY += x * y;
		}
		double xMean = sumX / n;
		double yMean = sumY / n;
		double sumXDiffYDiff = 0;
		double sumXDiff2 = 0;
		double sumYDiff2 = 0;
		for (int i = 0; i < n; i++) {
			double x = xValues.get(i);
			double y = yValues.get(i);
			double predictedY = intercept + slope * x;
			sumXDiffYDiff += (x - xMean) * (y - predictedY);
			sumXDiff2 += (x - xMean) * (x - xMean);
			sumYDiff2 += (y - predictedY) * (y - predictedY);
		}
		double numerator = sumXDiffYDiff;
		double denominator = Math.sqrt(sumXDiff2 * sumYDiff2);
		return numerator / denominator;
	}  
	public static double findAverage(ArrayList<Double> values) {
		double sum = 0;
		for (double value : values) {
			sum += value;
		}
		return sum / values.size();
	} 

    /**
     * Helper method which gets the wavelength values for a specfic range 
	 * required in the algorithm
     * 
     * @param start - desired start position of the wavelength data
     * @param end - the desired end position of the wavelength data
     */ 
	private ArrayList<Double> getWavelengthValues(int start, int end) {
        // Convert 1-based index to 0-based index
        int startIndex = start - 1;
        int endIndex = end - 1;
    
        // Ensure start and end are within bounds of wavelengthValues
        if (startIndex < 0 || endIndex >= wavelengthValues.size() || startIndex > endIndex) {
            throw new IllegalArgumentException("Invalid range for wavelengthValues");
        }
    
        // Get the sublist of wavelengthValues from startIndex (inclusive) to endIndex (inclusive)
        return new ArrayList<>(wavelengthValues.subList(startIndex, endIndex + 1));
    }


    private ArrayList<Double> getAbsorptionValues(int start, int end) {
        // Convert 1-based index to 0-based index
        int startIndex = start - 1;
        int endIndex = end - 1;
    
        // Ensure start and end are within bounds of absorptionSpectrum
        if (startIndex < 0 || endIndex >= copyAbsorption.length || startIndex > endIndex) {
            int absorptionLength = copyAbsorption.length;
            String message = "Invalid range for absorptionSpectrum. Start index: " + startIndex + ", End index: " + endIndex
                             + ", Absorption spectrum length: " + absorptionLength;
            throw new IllegalArgumentException(message);
        }
    
        // Create a new ArrayList to hold the result
        ArrayList<Double> result = new ArrayList<>();
    
        // Add values from absorptionSpectrum to result ArrayList
        for (int i = startIndex; i <= endIndex; i++) {
            result.add(copyAbsorption[i]);
        }
    
        return result;
    }
    
    

    /**
     * Helper method to get transmission values from a specific range in the transmission spectrum.
     *
     * @param start Desired start index of the transmission data (1-based index).
     * @param end Desired end index of the transmission data (1-based index).
     * @return ArrayList<Double> containing transmission values within the specified range.
     * @throws IllegalArgumentException if the start or end indices are out of bounds.
     */
    private ArrayList<Double> getTransmissionValues(int start, int end) {
        // Convert 1-based index to 0-based index
        int startIndex = start - 1;
        int endIndex = end - 1;

        // Ensure start and end are within bounds of transmissions array
        if (startIndex < 0 || endIndex >= transmissions.length || startIndex > endIndex) {
            throw new IllegalArgumentException("Invalid range for transmissions array");
        }

        // Create an ArrayList to hold transmission values
        ArrayList<Double> transmissionValues = new ArrayList<>();

        // Iterate over the specified range and add transmission values to the ArrayList
        for (int i = startIndex; i <= endIndex; i++) {
            transmissionValues.add(copyTransmission[i]);
        }

        return transmissionValues;
    }

    
	/**  
	* Calculates the R-Squared Value for the data set 
	* 
	* Returns R^2 value 
	*/ 
	public double calculateRSquared() 
	{   
		// gets values of X
		ArrayList<Double> xValues = getWavelengthValues(5 , 38);  
		
		// Initialize ArrayList to hold the copied values
        ArrayList<Double> yValues = new ArrayList<>();

        // Copy values from 5th to 38th position (assuming positions are 0-indexed)
        int start = 4; // 5th position in 0-indexed array
        int end = 38; // 38th position in 0-indexed array

        for (int i = start; i < end; i++) {
            yValues.add(copyAbsorption[i]);
        }

		// array list for log of Y
		ArrayList<Double> logOfY = new ArrayList<>();    
		// loops Y array and finds log of each element 
		for (double i : yValues)  
		{
			double logValue = Math.log(i); 
			logOfY.add(logValue);  	
		}  
		// array list for X^2 Values
		ArrayList<Double> valuesXX = new ArrayList<>(); 
		for (double i : xValues) 
		{ 
			double value = Math.pow(i , 2); 
			valuesXX.add(value); 
		}   
		// array list for product of XY 
		ArrayList<Double> valuesXY = new ArrayList<>(); 
		for (int i = 0; i < 34; i++) 
		{ 
			double value = xValues.get(i) * logOfY.get(i); 
			valuesXY.add(value); 
		} 
		double sumX = findSum(xValues); 
		double sumY = findSum(logOfY); 
		double sumXX = findSum(valuesXX); 
		double sumXY = findSum(valuesXY); 
		double slopeNumerator = (34 * sumXY) - (sumY * sumX); 
		double slopeDenominator = (34 * sumXX) - Math.pow(sumX, 2); 
		double slope = slopeNumerator/slopeDenominator;  
		double yIntercept = (sumY - (slope * sumX)) / 34;  
		double averageY = findAverage(logOfY);  
		// array list for predicted y values
		ArrayList <Double> predictedY = new ArrayList<>(); 
		for (int i = 0; i < 34; i++) 
		{ 
			double yValue = slope * xValues.get(i) + yIntercept; 
			predictedY.add(yValue); 
		}  
		// sum of square residuals
		double ssr = 0;  
		// total sum of squares
		double sst = 0; 
		// finds each individual element of the ssr and sst and adds 
		// them to the respective running total
		for (int i = 0; i < 34; i++) 
		{ 
			double r = Math.pow(logOfY.get(i) - predictedY.get(i), 2); 
			double t = Math.pow(logOfY.get(i) - averageY, 2);
			ssr += r; 
			sst += t; 
		} 
		return rSquared = Math.round((1 - (ssr/sst)) * 1000.0) / 1000.0;	
	}

    /**
     * Calculates average transmission at 550 nm
	 * Returns transmission average instance variable of the lens object
     */ 
	public double calculateTransmissionAvg() 
	{  
		// Retrieve transmission value at index550nm from transmissions array
        return transmissions[17];
	}       

    /**
     * Calculates Eye Protection Factor 
     * 
     * @return value of EPF
     */  
	public double calculateEPF() 
	{   	 
		// array of iPad Emission Spectrum from 380nm-500nm
		double [] iPadES = {0.001, 0.001, 0.001, 0.001, 0.02, 0.1, 0.42, 0.96, 0.56, 0.31, 0.18, 0.16, 0.22};  
		// damage spectrum of eye from 380nm-550nm
		double [] damageSpectrum = {0.006, 0.02, 0.1, 0.4, 0.9, 0.98, 1.0, 0.94, 0.8, 0.62, 0.45, 0.22, 0.1};  
		// array for product of iPad Emssion Spectrum and damage spectrum 
		ArrayList<Double> sAValues = new ArrayList<>();   
		// loops through each data point from 380nm-550nm 
		// and multiplies the emission spectrum and damage spetrum 
		for(int i = 0; i < iPadES.length; i++) 
		{   
			double product = iPadES[i] * damageSpectrum[i];  
			sAValues.add(product);
		} 
		// array for product of iPad Emssion Spectrum, damage spectrum, and transmission/100
		ArrayList<Double> sATValues = new ArrayList<>();  
		// retrieves transmission values from range 380nm-550nm
		ArrayList<Double> transmissionValues = getTransmissionValues(1, 13);  
		// loops through each data point from 380nm-550nm 
		// and multiplies the emission spectrum, damage spectrum, and transmission spectrum
		for(int i = 0; i < iPadES.length; i++) 
		{  
			double product = sAValues.get(i) * (transmissionValues.get(i)/100); 
			sATValues.add(product);
		}  
		double sASum = findSum(sAValues); 
		double sATSum = findSum(sATValues);  
		double sumsDivided = (sATSum)/(sASum);  
		double epf = 1/ sumsDivided; 
		epf = Math.round(epf * 100.0) / 100.0; 	
        return epf;	
	}   

    /**
     * Calculates Melatonin Protection Factor 
     * 
     * @return value of MPF
     */  
	public double calculateMPF()
	{    
		// array of iPad Emission Spectrum from 400nm-600nm
		double [] iPadES = {0.00, 0.00, 0.02, 0.1, 0.42, 0.96, 0.56, 0.31, 0.18, 0.16, 0.22, 0.36, 0.48, 0.51, 0.49, 0.46, 0.4, 0.34, 0.38, 0.5, 0.44}; 
		// action spectrum of melatonin from 400nm-600nm
		double [] aSMel = {0.36, 0.4, 0.55, 0.7, 0.8, 0.9, 0.98, 0.95, 0.87, 0.8, 0.6, 0.45, 0.3, 0.185, 0.125, 0.0625, 0.025, 0.01, 0.005, 0.00, 0.00};  
		// array for product of iPad Emssion Spectrum and action spectrum
		ArrayList<Double> sAValues = new ArrayList<>();  
		// loops through each data point from 400nm-600nm 
		// and multiplies the emission spectrum and action spetrum 
		for(int i = 0; i < iPadES.length; i++) 
		{  
			double product = iPadES[i] * aSMel[i];
			sAValues.add(product);
		} 
		// array for product of iPad Emssion Spectrum, action spectrum, and transmission/100
		ArrayList<Double> sATValues = new ArrayList<>();   
		// retrieves transmission values from range 400nm-600nm
		ArrayList<Double> transmissionValues = getTransmissionValues(3, 23);  
		// loops through each data point from 400nm-600nm 
		// and multiplies the SA Values and transmission data 
		for(int i = 0; i < iPadES.length; i++) 
		{  
			double product = sAValues.get(i) * (transmissionValues.get(i)/100); 
			sATValues.add(product);
		} 
		double sASum = findSum(sAValues);
		double sATSum = findSum(sATValues); 
		double sumsDivided = (sATSum) /(sASum); 
		double mpf = 1/ sumsDivided; 
        mpf = Math.round(mpf * 100.0) / 100.0; 
		return mpf;
	}  

    /**
     * Calculates Glare Reduction Factor 
     * 
     * @return value of GRF
     */  
	public double calculateGRF() 
	{  
		// array of iPad Emission Spectrum from 440nm-660nm
		double [] iPadES = {0.42, 0.96, 0.56, 0.31, 0.18, 0.16, 0.22, 0.36, 0.48, 0.51, 0.49, 0.46, 0.4, 0.34, 0.38, 0.5, 0.44, 0.41, 0.33, 0.275, 0.22};  
		// glare spectrum of melatonin from 400nm-600nm
		double [] glareSpectrum = {17.7828, 14.1254, 10.0, 14.1254, 11.2202, 10.0, 11.2202, 5.01187, 2.51189, 1.99526, 1.58489, 1.41254, 1.25893, 1.00, 0.79433, 1.58489, 0.50119, 0.35481, 0.19953, 0.17783, 0.15849};  
		// array for product of iPad Emssion Spectrum and action spectrum
		ArrayList<Double> sPValues = new ArrayList<>(); 
		// loops through each data point from 440nm-640nm 
		// and multiplies the emission spectrum and glare spetrum
		for(int i = 0; i < iPadES.length; i++) 
		{  
			double product = iPadES[i] * glareSpectrum[i];
			sPValues.add(product);
		}  
		// array for product of iPad Emssion Spectrum, glare spectrum, and transmission/100
		ArrayList<Double> sPTValues = new ArrayList<>();     
		// retrieves transmission values from range 440nm-640nm
		ArrayList<Double> transmissionValues = getTransmissionValues(7, 27);  
		// loops through each data point from 440nm-640nm 
		// and multiplies the emission spectrum and glare spetrum
		for(int i = 0; i < iPadES.length; i++) 
		{  
			double product = sPValues.get(i) * (transmissionValues.get(i)/100); 
			sPTValues.add(product); 
		} 
		double sPSum = findSum(sPValues);
		double sPTSum = findSum(sPTValues); 
		double sumsDivided = (sPTSum)/(sPSum);  
		double grf = 1/ sumsDivided; 
        grf = Math.round(grf * 100.0) / 100.0;
		return grf;
	}  

    
    @Override
    public String toString() {
		this.initialize();
        return  
				"""
                Lens {
                id = """ + id +
				"\nname = " + name +
                "\n rSquared = " + rSquared +
                ", \n transmissionAvg = " + transmissionAvg +
                ", \n transmissions = " + Arrays.toString(transmissions) + ", length=" + transmissions.length +
                ", \n absorptions = " + Arrays.toString(absorptionSpectrum) + ", length=" + absorptionSpectrum.length +
                ", \n eyeProtectionFactor = " + eyeProtectionFactor +
                ", \n melatoninProductionFactor = " + melatoninProductionFactor +
                ", \n glareReductionFactor = " + glareReductionFactor + "\n" + "}";
    }
}