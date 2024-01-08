package steps;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import page.LoginPage;

public class DownloadMainScenario extends testBase{
    // Shared between all tests in this class.

    LoginPage loginPage = new LoginPage(page);

    DownloadActivityLog downloadActivityLog = new DownloadActivityLog();
    DownloadInvestorsDocumentInbox downloadInvestorsDocumentInbox = new DownloadInvestorsDocumentInbox();
    DownloadBonusCodeRules downloadBonusCodeRules = new DownloadBonusCodeRules();

    public String investorId = "112604";

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

//    @Test
//    @DisplayName("002. Download investor activity log")
//    void downloadInvestorActivityLog() throws Exception {
//        System.out.println("-----Working with InvesterID = "+investorId);
//        System.out.println("--Start downloading activity log--");
//        downloadActivityLog.downloadInvestorActivityLog(investorId);
//        System.out.println("--End downloading activity log--");
//    }
//
//    @Test
//    @DisplayName("003. Download investor document / inbox")
//    void setDownloadInvestorsDocumentInbox() throws Exception {
//        System.out.println("-----Working with InvesterID = "+investorId);
//        System.out.println("--Start downloading investors document--");
//        downloadInvestorsDocumentInbox.downloadInvestorActivityLog(investorId);
//        System.out.println("--End downloading investors document--");
//    }

    @Test
    @DisplayName("004. Download bonus code rules")
    void setDownloadInvestorsDocumentInbox() throws Exception {
        System.out.println("--Start downloading bonus code rules--");
        downloadBonusCodeRules.DownloadBonusCodeRules();
        System.out.println("--End downloading bonus code rules--");
    }

}
