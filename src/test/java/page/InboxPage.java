package page;

import com.microsoft.playwright.Download;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import steps.testBase;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class InboxPage {
    Page page;

    public InboxPage(Page page){
        this.page = page;
    }

    public String urlInvestorInvestmentPage = testBase.getPropValue("url_investorInvestments_page");
    public String urlInboxPage = testBase.getPropValue("url_inbox_page");

    public void clickAllowCookies(){
        String xpath="//*[@id=\"CybotCookiebotDialogBodyButtonAccept\"]";
        page.locator(xpath).click();
    }

    public int getNoOfPage(){
        Locator listElement = page.locator("//div/div/div/div/div/div/nav/ul/li/a");
        int count = listElement.count()-2;
        return count;
    }

    public int getRowsPerPage(){
        Locator listEle = page.locator("//*[@id=\"messages\"]/div/table/tbody/tr");
        int count = listEle.count();
        return count;
    }

    public int getColsPerRow(){
        Locator listEle = page.locator("//*[@id=\"messages\"]/div/table/thead/tr/th");
        int count = listEle.count();
        return count;
    }

    public int getTotalMessages(){
        String xpath = "//div/div/div[2]/div[2]/form[1]/div/div[2]/ul/li[1]/a/span";
        String total = page.locator(xpath).textContent().trim();
        int count = 0;
        try{
            count = Integer.parseInt(total);
        }catch (Exception e){
           System.out.print(e) ;
        }
        return count;
    }

    public String getCategory(int row){
        String value = "";
        String xpath = "//*[@id=\"messages\"]/div/table/tbody/tr["+row+"]/td[2]";
        value = page.locator(xpath).textContent().trim();
        return value;
    }

    public String getProject(int row){
        String value = "";
        String xpath = "//*[@id=\"messages\"]/div/table/tbody/tr["+row+"]/td[3]";
        value = page.locator(xpath).textContent().trim();
        return value;
    }

    public String getDate(int row){
        String value = "";
        String xpath = "//*[@id=\"messages\"]/div/table/tbody/tr["+row+"]/td[4]";
        value = page.locator(xpath).textContent().trim();
        return value;
    }

    public String getDocumentName(int row){
        String value = "";
        String xpath = "//*[@id=\"messages\"]/div/table/tbody/tr["+row+"]/td[1]";
        value = page.locator(xpath).textContent().trim();
        return value;
    }

    public String getDocumentDownloadURL(int row) throws InterruptedException{
        String value ="";
        String btn3dot ="//*[@id=\"messages\"]/div/table/tbody/tr["+row+"]/td[5]/div/button";
        page.locator(btn3dot).click();
        Thread.sleep(1000);
        String xpath = "//*[@id=\"messages\"]/div/table/tbody/tr["+row+"]/td[5]/div/ul/li[2]/a";
        value = page.locator(xpath).getAttribute("href");
        page.locator(btn3dot).click();
        Thread.sleep(300);
        page.locator(btn3dot).click();
        return value;
    }

    public void downloadTheInvestorDocument(int row, String fileName, String investorId) throws InterruptedException{
        String btn3dot ="//*[@id=\"messages\"]/div/table/tbody/tr["+row+"]/td[5]/div/button";
        page.locator(btn3dot).click();
        Thread.sleep(1000);
        String xpath = "//*[@id=\"messages\"]/div/table/tbody/tr["+row+"]/td[5]/div/ul/li[2]/a";

        //Create folder location
        try{
            Path path = Paths.get("Downloads/"+investorId);
            Files.createDirectories(path);
            path = Paths.get("Downloads/"+investorId+"/"+"Inbox");
            Files.createDirectories(path);
        }catch (Exception ie){
            System.err.println(ie);
        }

        Download download = page.waitForDownload(()->{
            page.locator(xpath).click();
        });

        System.out.println("Downloading: "+download.url());
        download.saveAs(Paths.get("Downloads/"+investorId+"/Inbox/"+fileName+".pdf"));
        Thread.sleep(3000);
    }
}