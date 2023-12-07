package page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import steps.testBase;

public class ActivityLog {
    Page page;

    public ActivityLog(Page page){
        this.page = page;
    }

    public String url_activityLog = testBase.getPropValue("url_activity_log");;

    private String btn = "//form/div[2]/div[2]/nav/span[9]/a";

    //locators
    public String pagination = "//nav[@class=\"pagy-nav pagination\"]";

    public String pageTitle = "/html/body/div[2]/section/div[2]/section/div[1]/h3";

    public int getTotalPage() throws InterruptedException {
        String total_items = "//div[2]/section/div[2]/section/div[2]/div[1]/div[1]/span/b[2]";
        int totalPage = 1;
        String totalitems = "";
        int i = 0;
        float j = 0f;

        try {
            totalitems = page.locator(total_items).textContent().trim();
            i = Integer.parseInt(totalitems);
            j = (float) i/25;
            i = (int) j;
            if(j>i){
                totalPage = i + 1;
            }
            return totalPage;
        }catch (Exception e){
            System.out.print(e);
        }
        return totalPage;
    }

    public int getRowsPerPage(){
        Locator listEle = page.locator("//section/div[2]/div[2]/table/tbody/tr");
        int count = listEle.count();
        return count;
    }

    public String[] getCelData(int row){
        String[] cel_data = new String[6];
        String cel_locator = "";
        for (int i = 1; i<=6; i++){
            cel_locator = "//section/div[2]/section/div[2]/div[2]/table/tbody/tr["+row+"]/td["+i+"]";
            cel_data[i-1] = page.locator(cel_locator).textContent().trim();
        }
        return cel_data;
    }

    public String[] getAtributeSpecification(int row){
        String [] attr = new String[2];
        String cel_locator = "";
//        cel_locator = "//section/div[2]/section/div[2]/div[2]/table/tbody/tr["+row+"]/td[5]/a/text()";
        cel_locator = "//section/div[2]/section/div[2]/div[2]/table/tbody/tr["+row+"]/td[5]/a";
        attr[0] = page.locator(cel_locator).textContent().trim();
        attr[1] = page.locator(cel_locator).getAttribute("href").trim();
        return attr;
    }

    public String getActivityID (int row){
        String cel_data ="";
        String cel_locator = "//section/div[2]/section/div[2]/div[2]/table/tbody/tr["+row+"]/td[1]";
        cel_data = page.locator(cel_locator).textContent().trim();
        return cel_data;
    }

}
