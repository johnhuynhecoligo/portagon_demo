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

    public void writeCSV(String[][] courseArray) throws Exception {

        //create a File class object and give the file the name employees.csv
        java.io.File courseCSV = new java.io.File("investor_list.csv");

        //Create a Printwriter text output stream and link it to the CSV File
        java.io.PrintWriter outfile = new java.io.PrintWriter(courseCSV);


        for (int i = 0; i<courseArray.length; i++){
            for (int j=0; j<6; j++){
                outfile.write(courseArray[i][j] + ",");
            }
            outfile.write("\n");

        }

        outfile.close();
    } //end writeCSV()

    @Test
    @DisplayName("002. Count investors")
    void countInvestors() throws Exception {

        String[] cell_data;
        Thread.sleep(3000);
        page.navigate(testBase.getPropValue("url_investor_list"));
        Thread.sleep(2000);
//        int total_page = investorList.getTotalPage();
        int total_page = 2;
        int row_total = 0;

        if (total_page>1){
            page.navigate("https://admin.portagon.io/platforms/9/investors?page="+total_page);
            int count_row_lastpage = investorList.getRowsPerPage();
            row_total = (total_page-1)*25;
            row_total = row_total + count_row_lastpage;
        }

        System.out.println("Total: " + row_total + " rows");

        String[][] investors_list = new String[row_total][7];

        //Go to one page
        for (int pageCount = 1; pageCount<=total_page; pageCount++){
            int new_arr_row = 0;
            //Get row per page
            page.navigate("https://admin.portagon.io/platforms/9/investors?page="+pageCount);
            int count_row_per_page = 0;
            count_row_per_page = investorList.getRowsPerPage();
            for (int rowCount = 1; rowCount<=count_row_per_page; rowCount++){
                cell_data = investorList.getCelData(rowCount);
                //Get columns per row
                for(int columnCount=1; columnCount<=cell_data.length-1; columnCount++){
                    int count_first_row = 0;
                    if (pageCount>1){
                        count_first_row = count_first_row + ((pageCount-1)*25);
                    }

                    //Get InvestorID
                    String arr_id = investorList.getInvestorID(rowCount);


                    //Data
                    investors_list[rowCount-1 + count_first_row][0] = arr_id.trim();
                    investors_list[rowCount-1 + count_first_row][1] = cell_data[0].trim();
                    investors_list[rowCount-1 + count_first_row][2] = cell_data[2].trim();
                    investors_list[rowCount-1 + count_first_row][3] = cell_data[3].trim();
                    investors_list[rowCount-1 + count_first_row][4] = cell_data[4].trim();
                    investors_list[rowCount-1 + count_first_row][5] = cell_data[5].trim();
                }
            }
        }

        for (int i = 0; i<row_total; i++){
            for (int j=0; j<6; j++){
                System.out.print(investors_list[i][j] + "\t");
            }

            System.out.println("\n");
        }

        writeCSV(investors_list);
    }

}
