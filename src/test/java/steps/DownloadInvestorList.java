package steps;

import org.junit.jupiter.api.*;
import page.InvestorList;
import page.LoginPage;

import java.util.Scanner;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class DownloadInvestorList extends testBase{
    // Shared between all tests in this class.

    LoginPage loginPage = new LoginPage(page);
    InvestorList investorList = new InvestorList(page);

    @Test
    @DisplayName("001. Login")
    void enterDataAndLogin() throws InterruptedException {
        try{
            //Get email and password from the configs.properties
            String email = testBase.getPropValue("email");
            String pass = testBase.getPropValue("pass");
            loginPage.enterDataAndLogin(email, pass);

        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("002. Count investors")
    void countInvestors() throws  InterruptedException {

        String[] cell_data;
        Thread.sleep(3000);
        page.navigate(testBase.getPropValue("url_investor_list"));
        Thread.sleep(2000);
//        int total_page = investorList.getTotalPage();
        int total_page = 3;
        int row_total = 0;

        if (total_page>1){
            page.navigate("https://admin.portagon.io/platforms/9/investors?page="+total_page);
            int count_row_lastpage = investorList.getRowsPerPage();
            row_total = (total_page-1)*25;
            row_total = row_total + count_row_lastpage;
        }

        System.out.print("Total: " + row_total + " rows");

        String[][] investors_list = new String[row_total][6];

        //Go to one page
        for (int pageCount = 1; pageCount<=total_page; pageCount++){
            //Get row per page
            page.navigate("https://admin.portagon.io/platforms/9/investors?page="+pageCount);
            int count_row_per_page = 0;
            count_row_per_page = investorList.getRowsPerPage();
            for (int rowCount = 1; rowCount<count_row_per_page; rowCount++){
                cell_data = investorList.getCelData(rowCount);
                //Get columns per row
                for(int columnCount=0; columnCount<=cell_data.length-1; columnCount++){
                    investors_list[rowCount-1][columnCount] = cell_data[columnCount].trim();
                }
            }
        }

        for (int i = 0; i<row_total; i++){
            for (int j=0; j<6; j++){
                System.out.print(investors_list[i][j] + "\t");
            }

            System.out.println("\n");
        }

    }

}
