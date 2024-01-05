package steps;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.junit.jupiter.api.Assertions;
import page.InboxPage;
import page.InvestorDetails;

public class DownloadInvestorsDocumentInbox extends testBase{
    // Shared between all tests in this class.

    InvestorDetails investorDetails = new InvestorDetails(page);
    InboxPage inboxPage = new InboxPage(page);
    public String[][] messages_array;

    public void downloadInvestorActivityLog(String investorId) throws InterruptedException {
        //Go to the investor detail
        String actual = "";
        String expected = "";
        try{
            String url = investorDetails.setUrlInvestorDetails(investorId);
            page.navigate(url);
            Thread.sleep(3000);
            actual = investorDetails.loginasInvestor(investorId);
            expected = testBase.getPropValue("url_investorInvestments_page");
            if (expected.equals(actual)){
                //Go to the message page
                page.navigate(testBase.getPropValue("url_inbox_page"));
                inboxPage.clickAllowCookies();
                int totalPage = inboxPage.getNoOfPage();
                int totalCol = 0;
                totalCol = inboxPage.getColsPerRow();
                System.out.println("There are: "+totalPage+" pages");

                messages_array = new String[inboxPage.getTotalMessages()+1][8];
                //Header for CSV
                messages_array[0][0]="investor_id";
                messages_array[0][1]="document_id";
                messages_array[0][2]="document_name";
                messages_array[0][3]="document_type";
                messages_array[0][4]="category";
                messages_array[0][5]="campaign";
                messages_array[0][6]="date";
                messages_array[0][7]="document_url";

                //Create an array to save the download url
                for(int pageCount=1;pageCount<=totalPage;pageCount++)
                {
                    page.navigate("https://my.ecoligo.investments/investor/account/messages?page="+pageCount);
                    page.waitForLoadState();
                    for(int rowCount=1; rowCount<=inboxPage.getRowsPerPage(); rowCount++){
                        int count_first_row = 0;
                        if (pageCount>1){
                            count_first_row = count_first_row + ((pageCount-1)*10);
                        }
                        messages_array[rowCount+count_first_row][0]=investorId;

                        //Generate UUID for document
                        String uuid = genUUID();
                        messages_array[rowCount+count_first_row][1]=uuid;
                        messages_array[rowCount+count_first_row][2]=inboxPage.getDocumentName(rowCount);
                        messages_array[rowCount+count_first_row][3]="document_type";
                        messages_array[rowCount+count_first_row][4]=inboxPage.getCategory(rowCount);
                        messages_array[rowCount+count_first_row][5]=inboxPage.getProject(rowCount);
                        messages_array[rowCount+count_first_row][6]=inboxPage.getDate(rowCount);
                        messages_array[rowCount+count_first_row][7]="document_S3url";
                        inboxPage.downloadTheInvestorDocument(rowCount, uuid, investorId);

                    }

                }

                System.out.println("There are: "+inboxPage.getTotalMessages()+" messages");
                System.out.println("There are: "+totalCol+" cols");


                //Print the array
                for (int i = 0; i<messages_array.length; i++){
                    for (int j=0; j<messages_array[i].length; j++){
                        System.out.print(messages_array[i][j] + "\t");
                    }
                    System.out.println("\n");
                }

                writeCSV(messages_array, investorId, "Inbox", "Investor_documents_inbox");
            }else{
                Assertions.assertEquals(expected, actual);
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
