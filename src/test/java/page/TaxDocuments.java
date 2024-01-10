package page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import steps.testBase;

public class TaxDocuments extends  testBase{
    Page page;

    public TaxDocuments(Page page){
        this.page = page;
    }
    
    public String tab_taxDocuments = "//section[3]/div/ul/li[1]/a";

    public String yearsSelect = "//*[@id=\"b1\"]/div[1]/div/a";

    public String lbl_noEntries = "//*[@id=\"a1\"]/i";


    public int countYearsAtSelectbox(){
        Locator listEle = page.locator("//*[@id=\"b1\"]/div[1]/div/ul/a");
        int count = listEle.count();
        return count+1;
    }

    public String[] getYears(){
        Locator listEle = page.locator("//*[@id=\"b1\"]/div[1]/div/ul/a");
        int count = 0;
        count = listEle.count();
        String[] years = new String[1];
        page.locator(yearsSelect).click();
        if (count>0){
            years = new String[count+1];
            for (int i=0; i<count; i++){
                int j = i+1;
                years[j] = page.locator("//*[@id=\"b1\"]/div[1]/div/ul/a["+j+"]").textContent();
            }
            years[0] = page.locator("//*[@id=\"b1\"]/div[1]/div/a/span[1]").textContent();
        }else{
            years = new String[1];
            years[0] = "There not any years";
        }
        return years;
    }

    public int returnRows(){
//        Locator listEle = page.locator("//*[@id=\"a1\"]/div/table/tbody/tr");
        Locator listEle = page.locator("//div[2]/section/div[2]/div[2]/div[1]/section[3]/div/div[2]/div[1]/div[2]/div/div/table/tbody/tr");
        int count = 0;
        count = listEle.count();
        return count;
    }

    public void gotoNextTaxPage(String investorId, String year){
        String locator = "https://admin.portagon.io/platforms/9/investors/"+investorId+"?year="+year+"#b1";
        page.navigate(locator);
        page.waitForLoadState();
    }

    public String getDownloadLink (int row){
        String locator = "//div[2]/section/div[2]/div[2]/div[1]/section[3]/div/div[2]/div[1]/div[2]/div/div/table/tbody/tr["+row+"]/td[1]/a";
        String href = page.locator(locator).getAttribute("href").trim();
        return href;
    }

    public String getDocType (int row){
        String locator = "//div[2]/section/div[2]/div[2]/div[1]/section[3]/div/div[2]/div[1]/div[2]/div/div/table/tbody/tr["+row+"]/td[3]";
        String value = page.locator(locator).textContent().trim();
        return value;
    }

    public String getInvestmentId (int row){
        String locator = "//div[2]/section/div[2]/div[2]/div[1]/section[3]/div/div[2]/div[1]/div[2]/div/div/table/tbody/tr["+row+"]/td[4]";
        String value = page.locator(locator).getAttribute("href").trim();
        value = splitString(value, "/platforms/9/investments/");
        return value;
    }

    public String getInvestmentReference (int row){
        String locator = "//div[2]/section/div[2]/div[2]/div[1]/section[3]/div/div[2]/div[1]/div[2]/div/div/table/tbody/tr["+row+"]/td[4]";
        String value = page.locator(locator).textContent().trim();
        return value;
    }

    public String getProjectDescription (int row){
        String locator = "//div[2]/section/div[2]/div[2]/div[1]/section[3]/div/div[2]/div[1]/div[2]/div/div/table/tbody/tr["+row+"]/td[5]";
        String value = page.locator(locator).textContent().trim();
        return value;
    }

}
