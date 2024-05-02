
package com.mycompany.demoproj;

import java.net.URL;
import java.util.ResourceBundle;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author ma782165
 */
public class CancelReservationControllerTest {
    
    public CancelReservationControllerTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of initialize method, of class CancelReservationController.
     */
    @Test
    public void testInitialize() {
        System.out.println("initialize");
        URL url = null;
        ResourceBundle rb = null;
        CancelReservationController instance = new CancelReservationController();
        instance.initialize(url, rb);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of cancellation method, of class CancelReservationController.
     * uses success = false
     */
    @Test
    public void testCancelation_false() throws Exception {
        System.out.println("cancelation_false");
        boolean success = false;
        CancelReservationController instance = new CancelReservationController();
        instance.cancelation(success);
        System.out.println("cancelation not succesul, test passed");
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
    /**
     * Test of cancellation method, of class CancelReservationController.
     * uses success = true
     */
    @Test
    public void testCancelation_true() throws Exception {
        System.out.println("cancelation_true");
        boolean success = true;
        CancelReservationController instance = new CancelReservationController();
        instance.cancelation(success);
        System.out.println("cancelation succesful, test passed");
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
}
