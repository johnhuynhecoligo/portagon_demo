package page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import steps.testBase;

public class BonusCodeRules extends testBase {
    Page page;

    public BonusCodeRules(Page page){
        this.page = page;
    }

    public String url = testBase.getPropValue("url_marketing_tools");
    //locators

    //Bonus code rules list
    public int countRowsPerPage(){
        Locator listEle = page.locator("//section/div[2]/div/table/tbody/tr");
        int count = listEle.count();
        return count;
    }

    public String getCodeName(int row){
        String locator = "//section/div[2]/div/table/tbody/tr["+row+"]/td[1]/a";
        String value = "";
        value = page.locator(locator).textContent();
        return value;
    }

    public String getCodeID(int row){
        String locator = "//section/div[2]/div/table/tbody/tr["+row+"]/td[1]/a";
        String value = "";
        value = page.locator(locator).getAttribute("href").trim();

        //Cut  before
        value = splitString(value, "/platforms/9/bonus_code_rules/");

        //Cut  after
        value = splitString(value, "/edit");

        return value.trim();
    }

    public String getContingent(int row){
        String locator = "//section/div[2]/div/table/tbody/tr["+row+"]/td[2]";
        String value = "";
        value = page.locator(locator).textContent();
        value = splitString(value, "/").trim();
        return value;
    }

    public String getValidFrom(int row){
        String locator = "//section/div[2]/div/table/tbody/tr["+row+"]/td[3]";
        String value = "";
        value = page.locator(locator).textContent().trim();
        return value;
    }

    public String getValidUntil(int row){
        String locator = "//section/div[2]/div/table/tbody/tr["+row+"]/td[4]";
        String value = "";
        value = page.locator(locator).textContent().trim();
        return value;
    }

    //Bonus code rules details
    public String getMaxQuata(){
        String locator = "[id='bonus_code_rule_max_count']";
        String value = "";
        if(page.locator(locator).getAttribute("value")!=null){
            value = page.locator(locator).getAttribute("value").trim();
        }
        return value;
    }

    public String getMinInvestment(){
        String locator = "[id='bonus_code_rule_min_investment_amount']";
        String value = "";
        if(page.locator(locator).getAttribute("value")!=null){
            value = page.locator(locator).getAttribute("value").trim();
        }
        return value;
    }

    public String getMaxInvestment(){
        String locator = "[id='bonus_code_rule_max_investment_amount']";
        String value = "";
        if(page.locator(locator).getAttribute("value")!=null){
            value = page.locator(locator).getAttribute("value").trim();
        }
        return value;
    }

    public String getValueCheckbox_onlyNewInvestor(){
        String locator = "[id='bonus_code_rule_only_new_investor']";
        String value = "false";
        if(page.locator(locator).isChecked()){
            value = "true";
        }
        return value;
    }

    public String getValueCheckbox_single_use(){
        String locator = "[id='bonus_code_rule_once_per_investor']";
        String value = "false";
        if(page.locator(locator).isChecked()){
            value = "true";
        }
        return value;
    }

}
