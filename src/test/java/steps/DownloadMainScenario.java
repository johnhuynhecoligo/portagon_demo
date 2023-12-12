package steps;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import page.LoginPage;

public class DownloadMainScenario extends testBase{
    // Shared between all tests in this class.

    LoginPage loginPage = new LoginPage(page);

    DownloadActivityLog downloadActivityLog = new DownloadActivityLog();

    public String investorId = "112522";

    @Test
    @DisplayName("001. Login")
    void enterDataAndLogin() throws InterruptedException {
        try{
            //Get email and password from the configs.properties
            String email = testBase.getPropValue("email");
            String pass = testBase.getPropValue("pass");
            loginPage.enterDataAndLogin(email, pass);
            Thread.sleep(2000);
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("003. Download investor activity log")
    void downloadInvestorActivityLog() throws Exception {
        System.out.println("-----Working with InvesterID = "+investorId);
        System.out.println("--Start download activity log--");
        downloadActivityLog.downloadInvestorActivityLog(investorId);
        System.out.println("--End download activity log--");
    }

}
