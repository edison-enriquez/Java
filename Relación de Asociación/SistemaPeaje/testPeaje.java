package SistemaPeaje;


import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

public class testPeaje {
    private Peaje peaje;
    private Camion camion1;
    
    @Before
    public void setUp() {
        // Setup code here (if required)
        peaje = new Peaje ("La Lizama", "Santander");
        camion1 = new Camion("PKI-889", 2);
        
    }

    @After
    public void tearDown() {
        // Teardown code here (if required)
    }

    @Test
    public void testDefaultCase() {
        // You may rename this method to better suit the purpose of your test case
        // Your test case logic here
        peaje.addVehiculo(camion1);
        assertEquals(10000, peaje.calcularPeaje(camion1));
    }
}

