package _10_white_box_testing;

import _09_intro_to_white_box_testing.models.DeliveryService;
import _09_intro_to_white_box_testing.models.Order;
import _10_white_box_testing.models.BakeryService;
import _10_white_box_testing.models.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MyDonutShopTest {
	
    MyDonutShop myDonutShop;
    
    @Mock
    BakeryService bakeryService;
    
    @Mock
    DeliveryService deliveryService;
    
    @Mock
    PaymentService paymentService;
    
//    @Mock
//    Order order;
    
    @BeforeEach
    void setUp() {
    	MockitoAnnotations.openMocks(this);
    	myDonutShop = new MyDonutShop(paymentService, deliveryService, bakeryService);
    	myDonutShop.setPaymentService(paymentService);
    	myDonutShop.setDeliveryService(deliveryService);
    }

    @Test
    void itShouldTakeDeliveryOrder() throws Exception {
        //given
    	Order order = new Order("CUSTOMER_NAME",
                "CUSTOMER_PHONE_NUMBER",
                1,
                5.00,
                "CREDIT_CARD_NUMBER",
                true);
    	when(paymentService.charge(order)).thenReturn(true);
    	when(bakeryService.getDonutsRemaining()).thenReturn(1);
        //when
    	myDonutShop.openForTheDay();
    	myDonutShop.takeOrder(order);
        //then
    	verify(deliveryService, times(1)).scheduleDelivery(order);
    		
    }

    @Test
    void givenInsufficientDonutsRemaining_whenTakeOrder_thenThrowIllegalArgumentException() throws Exception {
        //given
    	Order order = new Order("CUSTOMER_NAME",
                "CUSTOMER_PHONE_NUMBER",
                1,
                5.00,
                "CREDIT_CARD_NUMBER",
                true);
    	when(paymentService.charge(order)).thenReturn(true);
    	when(bakeryService.getDonutsRemaining()).thenReturn(0);
        //when
    	myDonutShop.openForTheDay();
    	
        //then
    	Throwable exceptionThrown = assertThrows(IllegalArgumentException.class, () -> myDonutShop.takeOrder(order));
    	assertEquals(exceptionThrown.getMessage(), "Insufficient donuts remaining");
        
    }

    @Test
    void givenNotOpenForBusiness_whenTakeOrder_thenThrowIllegalStateException(){
        //given
    	Order order = new Order("CUSTOMER_NAME",
                "CUSTOMER_PHONE_NUMBER",
                1,
                5.00,
                "CREDIT_CARD_NUMBER",
                true);
    	myDonutShop.closeForTheDay();
        //when
    	
        //then
    	Throwable exceptionThrown = assertThrows(IllegalStateException.class, () -> myDonutShop.takeOrder(order));
    	assertEquals(exceptionThrown.getMessage(), "Sorry we're currently closed");
    }

}