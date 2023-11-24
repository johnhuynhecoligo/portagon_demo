package page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.util.Arrays;

public class InvestorList {
    Page page;

    public InvestorList(Page page){
        this.page = page;
    }

    //locators
    private String btn = "//form/div[2]/div[2]/nav/span[9]/a";

    public int getTotalPage() throws InterruptedException {
        String url_last_button = page.getAttribute(btn, "href");
        String[] arrayString = url_last_button.split("=");
        String last_page = arrayString[1];
        int count = Integer.parseInt(last_page);
        System.out.println("Total page: "+count);
        return count;
    }

    public int getRowsPerPage(){
        Locator listEle = page.locator("//table/tbody/tr");
        int count = listEle.count();
        return count;
    }

    public String[] getCelData(int row){
        String[] cel_data = new String[6];
        String cel_locator = "";
        for (int i = 1; i<=6; i++){
            cel_locator = "//form/div/table/tbody/tr["+row+"]/td["+i+"]";
            cel_data[i-1] = page.locator(cel_locator).textContent();
        }
        return cel_data;
    }

    public String getInvestorID(int row){
        String cel_data ="";
        String cel_locator = "//form/div/table/tbody/tr["+row+"]/td[1]/a";

        cel_data = page.locator(cel_locator).getAttribute("href");
        return cel_data;
    }

    public String getCelPerRow(int row){
        String value = "";
        String cel_locator = "//form/div/table/tbody/tr["+row+"]/td";
        value = page.locator(cel_locator).textContent();
        return value;
    }

}
