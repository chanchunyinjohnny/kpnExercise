package kpn;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

public class AppTest {

    @Test
    public void testCustomerOutrage() {

        try {
            File customerFile = new File("src/main/resources/customer_outages.json");
        	
            String customerOutrageAnswer = FileUtils.readFileToString(customerFile);
            
            Application.main(new String[]{"2017-07-20 08:11"});
            
            File customerOutputFile = new File("src/main/resources/runtime/customer_outages.json");
            
            String customerOutrageOutput = FileUtils.readFileToString(customerOutputFile);
            
            assertEquals(customerOutrageAnswer, customerOutrageOutput);
            
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testBusinessOutrage() {
        try {
            File businessFile = new File("src/main/resources/business_outages.json");
        	
            String businessOutrageAnswer = FileUtils.readFileToString(businessFile);
            
            Application.main(new String[]{"2017-07-18 08:11"});
            
            File businessOutputFile = new File("src/main/resources/runtime/business_outages.json");
            
            String businessOutrageOuput = FileUtils.readFileToString(businessOutputFile);
            
            assertEquals(businessOutrageAnswer, businessOutrageOuput);
            
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
