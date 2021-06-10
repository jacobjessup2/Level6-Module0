package _06_payroll;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PayrollTest {

    Payroll payroll = new Payroll();

    @Test
    void itShouldCalculatePaycheck() {
        //given
    	double wage = 7.25;
    	int hours = 5;
    	double expected = 36.25;
        //when
    	double actual = (double) payroll.calculatePaycheck(wage, hours);
        //then
    	assertEquals(expected, actual);
    }

    @Test
    void itShouldCalculateMileageReimbursement() {
        //given
    	int miles = 30;
    	double expected = 17.25;
        //when
    	double actual = payroll.calculateMileageReimbursement(miles);
        //then
    	assertEquals(expected, actual);
    }

    @Test
    void itShouldCreateOfferLetter() {
        //given
    	String name = "Jeff";
    	double wage = 7.25;
    	String expected = "Hello Jeff, We are pleased to offer you an hourly wage of 7.25";
        //when
    	String actual = payroll.createOfferLetter(name, wage);
        //then
    	assertEquals(expected, actual);
    }

}