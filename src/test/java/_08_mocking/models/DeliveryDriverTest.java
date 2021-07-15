package _08_mocking.models;

import _07_intro_to_mocking.models.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class DeliveryDriverTest {

    DeliveryDriver deliveryDriver;
    
    @Mock
    Car car;
    
//    @Mock
//    String name;
    
    @Mock
    CellPhone cellPhone;
    
//    @Mock
//    int octane;
//    
//    @Mock String phoneNumber;
    
    @BeforeEach
    void setUp() {
    	MockitoAnnotations.openMocks(this);
    	
    	String name = "Jeff";
    	deliveryDriver = new DeliveryDriver(name,car,cellPhone);
    }

    @Test
    void itShouldWasteTime() {
        //given
    	boolean expectedWaste = true;
        //when
    	when(cellPhone.browseCatMemes()).thenReturn(true);
    	boolean actualWaste = cellPhone.browseCatMemes();
        //then
    	assertEquals(expectedWaste,actualWaste);
    }

    @Test
    void itShouldRefuel() {
        //given
    	boolean expectedRefuel = true;
    	int octane = 85;
        //when
    	when(car.fillTank(octane)).thenReturn(true);
    	boolean actualRefuel = car.fillTank(octane);
        //then
    	assertEquals(expectedRefuel, actualRefuel);
    }

    @Test
    void itShouldContactCustomer() {
        //given
    	boolean expectedCall = true;
    	String phoneNumber = "1111111111";
        //when
    	when(cellPhone.call(phoneNumber)).thenReturn(true);
    	boolean actualCall = cellPhone.call(phoneNumber);
        //then
    	assertEquals(expectedCall, actualCall);
    }

}