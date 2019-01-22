package com.example.hospitalbilling.pojo;

import java.util.Calendar;
import java.util.Date;

import com.example.hospitalbilling.pojo.model.BillDescription;
import com.example.hospitalbilling.pojo.model.Patient;

public class BillingService {
	
	public double calculateBillAmtWithPrimitives(String insuranceId, String name,
            boolean diabetic, Date dob, double deposit, Date joinDateTime,
            int numDays, Date outTime) {

        double billAmount = 0.0;
        double charges = 0.0;
        double totAmt = 0.0;

        if (null != insuranceId && null != name) {
            /** calculate age from dob */
            Calendar cal = Calendar.getInstance();
            cal.setTime(dob);
            int birthDate = cal.get(Calendar.DATE);
            int birthYear = cal.get(Calendar.YEAR);

            Date today = new Date();
            cal.setTime(today);
            int currentYear = cal.get(Calendar.YEAR);

            int age = currentYear - birthYear;

            /** depending on the age and diabetic nature of the patient, hospital
            charges are calculated */
            if (diabetic) {
            	if (age < 21) {
                    charges = 6.35;
                } else if (age > 21 && age < 60) {
                    charges = 8.50;
                } else {
                    charges = 5.44;
                }	
            } else {
            	if (age < 21) {
                    charges = 4.35;
                } else if (age > 21 && age < 60) {
                    charges = 6.50;
                } else {
                    charges = 3.44;
                }
            }

            /** handle out time */
            cal.setTime(outTime);
            if (cal.get(Calendar.HOUR) < 18) {
                updateRoomsAvailable();
            } else {
                batchUpdateRoomsAvailable();
            }

            /** finally calculate the bill amount */
            totAmt = charges * numDays * 24;
            billAmount = totAmt - deposit;

            /** if your joining date is same as your birth date then you get 50%
             discount in your bill */
            if (null != joinDateTime) {
                cal.setTime(joinDateTime);
                int dateFromJoinDT = cal.get(Calendar.DATE);
                if (dateFromJoinDT == birthDate) {
                    billAmount = billAmount / 2;
                }
            }
        }

        return billAmount;
    }

	private void batchUpdateRoomsAvailable() {
        // do nothing as of today
        /** may be a batch program could be run to update the database with the
        vacant rooms for that day*/
    }

    private void updateRoomsAvailable() {
        // do nothing as of today
        /** update the database with the vacant rooms for that day */
    }
    
    public BillDescription calculateBillAmtWithBDS(Patient patient, double deposit, Date joinDateTime,
            int numDays, Date outTime) {
    	BillDescription billDesc = new BillDescription();
    	
    	double billAmount = 0.0;
        double charges = 0.0;
        double totAmt = 0.0;
        if (null != patient.getinsuranceId5() && null != patient.getName()) {
            /** calculate age from dob */
            Calendar cal = Calendar.getInstance();
            cal.setTime(patient.getDob());
                        
            int birthDate = cal.get(Calendar.DATE);
            int birthYear = cal.get(Calendar.YEAR);

            Date today = new Date();
            cal.setTime(today);
            int currentYear = cal.get(Calendar.YEAR);

            int age = currentYear - birthYear;

            /** depending on the age and diabetic nature of the patient, hospital
            charges are calculated */
            if (patient.isdiabetic5()) {
            	if (age < 21) {
                    charges = 6.35;
                } else if (age > 21 && age < 60) {
                    charges = 8.50;
                } else {
                    charges = 5.44;
                }
            } else {
            	if (age < 21) {
                    charges = 4.35;
                } else if (age > 21 && age < 60) {
                    charges = 6.50;
                } else {
                    charges = 3.44;
                }
            }

            /** handle out time */
            cal.setTime(outTime);
            if (cal.get(Calendar.HOUR) < 18) {
                updateRoomsAvailable();
            } else {
                batchUpdateRoomsAvailable();
            }

            /** finally calculate the bill amount */
            totAmt = charges * numDays * 24;
            billAmount = totAmt - deposit;

            /** if your joining date is same as your birth date then you get 50%
            discount in your bill */
            if (null != joinDateTime) {
                cal.setTime(joinDateTime);
                int dateFromJoinDT = cal.get(Calendar.DATE);
                if (dateFromJoinDT == birthDate) {
                    billAmount = billAmount / 2;
                    String billAmountStr = "" + (billAmount<0 ? billAmount*(-1) : billAmount);
                    int i = billAmountStr.indexOf( "." ) + 3;
                    String billDescription = "After a 50% discount, your hospital bill is " + billAmountStr.substring(0, i>billAmountStr.length() ? billAmountStr.length() : i);
                    
                    billDesc.setBillAmount(billAmount);
                    billDesc.setBillDate(today);
                    billDesc.setPaymentDueDate(today);
                    billDesc.setBillDescription(billDescription);
                }
            }
        }
        
    	return billDesc;
    }
}
