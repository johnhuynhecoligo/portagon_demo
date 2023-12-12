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

    public Locator getNoOfColLogDetailTable(int row, String activityID) throws  InterruptedException{
        String cel_locator = "";
        cel_locator = "//section/div[2]/section/div[2]/div[2]/table/tbody/tr["+row+"]/td[5]/a";
        page.locator(cel_locator).click();
        Thread.sleep(1500);
        Locator listEle = page.locator("//*[@id=\"audited_audit_"+activityID+"\"]/div/div/div[2]/table/tbody/tr/td");
        String btnClose = "//*[@id=\"audited_audit_"+activityID+"\"]/div/div/div[1]/button/span";
        page.locator(btnClose).click();
        return listEle;
    }

    public String[] getContentLogDetailTable(int row, String activityID, int noCol) throws  InterruptedException{
        String cel_locator = "";
        cel_locator = "//section/div[2]/section/div[2]/div[2]/table/tbody/tr["+row+"]/td[5]/a";
        page.locator(cel_locator).click();
        Thread.sleep(1500);
        String[] arr = new String[noCol];
        for(int i =1; i<=noCol; i++){
            arr[i-1] = page.locator("//*[@id=\"audited_audit_"+activityID+"\"]/div/div/div[2]/table/tbody/tr/td["+i+"]").textContent().trim();
        }
        String btnClose = "//*[@id=\"audited_audit_"+activityID+"\"]/div/div/div[1]/button/span";
        page.locator(btnClose).click();
        return arr;
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

    public void clickToOpenActivityDetails (int row){
        String cel_locator = "";
        cel_locator = "//section/div[2]/section/div[2]/div[2]/table/tbody/tr["+row+"]/td[5]/a";
        page.locator(cel_locator).click();
    }

    public void clickSpecification(int row){
        String cel_locator = "";
        cel_locator = "//section/div[2]/section/div[2]/div[2]/table/tbody/tr["+row+"]/td[5]/a";
        page.locator(cel_locator).click();
    }

    public String getActivityID (int row){
        String cel_data ="";
        String cel_locator = "//section/div[2]/section/div[2]/div[2]/table/tbody/tr["+row+"]/td[1]";
        cel_data = page.locator(cel_locator).textContent().trim();
        return cel_data;
    }

}
