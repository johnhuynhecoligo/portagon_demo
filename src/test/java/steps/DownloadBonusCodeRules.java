package steps;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import page.BonusCodeRules;
import page.InvestorList;
import page.LoginPage;

public class DownloadBonusCodeRules extends testBase{
    // Shared between all tests in this class.

    LoginPage loginPage = new LoginPage(page);
    BonusCodeRules bonusCodeRules = new BonusCodeRules(page);
    public void DownloadBonusCodeRules() throws InterruptedException {
        //Go to the Bonus code rules
        page.navigate(bonusCodeRules.url);
//        page.waitForLoadState();
        Thread.sleep(3000);
        String actual = "";
        String expected = "";

        String[][] bonusCode_arr;

        try{
            int totalRow = bonusCodeRules.countRowsPerPage();
            String bonusCode_name ="";
            String bonusCodeID="";
            String contigent="";
            String validFrom = "";
            String validUntil = "";
            String maxQuota = "";
            String minInvestment = "";
            String maxInvestment = "";
            String singleUse = "";
            String onlyNewInvestor = "";

            bonusCode_arr = new String[totalRow+1][10];

            bonusCode_arr[0][0] = "bonus_code";
            bonusCode_arr[0][1] = "bonus_code_id";
            bonusCode_arr[0][2] = "single_use";
            bonusCode_arr[0][3] = "first_investment_only";
            bonusCode_arr[0][4] = "min_investment_amt";
            bonusCode_arr[0][5] = "max_investment_amt";
            bonusCode_arr[0][6] = "usage_count";
            bonusCode_arr[0][7] = "max_quota";
            bonusCode_arr[0][8] = "valid_from";
            bonusCode_arr[0][9] = "valid_until";

            for(int row=1; row<=totalRow; row++){
                bonusCode_name = bonusCodeRules.getCodeName(row);
                bonusCodeID = bonusCodeRules.getCodeID(row);
                contigent = bonusCodeRules.getContingent(row);
                validFrom = bonusCodeRules.getValidFrom(row);
                validUntil = bonusCodeRules.getValidUntil(row);


                page.navigate("https://admin.portagon.io/platforms/9/bonus_code_rules/"+bonusCodeID+"/edit");
                page.waitForLoadState();
                maxQuota = bonusCodeRules.getMaxQuata();
                minInvestment = bonusCodeRules.getMinInvestment();
                maxInvestment = bonusCodeRules.getMaxInvestment();
                singleUse = bonusCodeRules.getValueCheckbox_single_use();
                onlyNewInvestor = bonusCodeRules.getValueCheckbox_onlyNewInvestor();

                bonusCode_arr[row][0] = bonusCode_name;
                bonusCode_arr[row][1] = bonusCodeID;
                bonusCode_arr[row][2] = singleUse;
                bonusCode_arr[row][3] = onlyNewInvestor;
                bonusCode_arr[row][4] = minInvestment;
                bonusCode_arr[row][5] = maxInvestment;
                bonusCode_arr[row][6] = contigent;
                bonusCode_arr[row][7] = maxQuota;
                bonusCode_arr[row][8] = validFrom;
                bonusCode_arr[row][9] = validUntil;

                //Back to the list page
                page.navigate(bonusCodeRules.url);
//                page.waitForLoadState();
            }

            //Export CSV file
            writeCSV(bonusCode_arr, "Common", "BonusCodeRules", "Bonus_Code_Rules");
        }catch (Exception e){
            System.out.println(e);
        }
    }

}
