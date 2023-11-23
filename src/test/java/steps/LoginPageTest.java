package steps;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import page.LoginPage;

import java.io.IOException;
import java.nio.file.Paths;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class LoginPageTest extends testBase{
    // Shared between all tests in this class.

    LoginPage loginPage = new LoginPage(page);

    @Test
    @DisplayName("Enter Data and click Login button")
    void enterDataAndLogin() throws InterruptedException {
        try{
            //Get email and password from the configs.properties
            String email = testBase.getPropValue("email");
            String pass = testBase.getPropValue("pass");
            loginPage.enterDataAndLogin(email, pass);


        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

}
