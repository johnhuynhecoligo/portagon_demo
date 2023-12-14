package page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import steps.testBase;

public class InvestorDetails {
    Page page;

    public InvestorDetails(Page page){
        this.page = page;
    }

    String url = testBase.getPropValue("url_investor_list");

    String btnInvestorLogbook = "//section/div[2]/div[2]/div[2]/section[1]/div/div/div/a[1]";
    String btnLoginasInvestor = "//section/div[2]/div[2]/div[2]/section[1]/div/div/div/a[2]";

    public String setUrlmyEcoligo(String investorID){
        String url = "https://admin.portagon.io/platforms/9/investors/"+investorID+"/login_as";
        return url;
    }

    public String setUrlInvestorDetails(String investorId){
        String urlTarget = url+"/"+investorId;
        return urlTarget;
    }

    public void clickbtnInvestorLogbook() throws InterruptedException{

    }

    public String loginasInvestor(String investorId) throws InterruptedException {
        String targetUrl = setUrlmyEcoligo(investorId);
        page.navigate(targetUrl);
        page.waitForLoadState();
        Thread.sleep(3000);
        String currentURL = page.url();
        return currentURL;
    }

}
