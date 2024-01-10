package steps;

import page.BonusCodeRules;
import page.InvestorDetails;
import page.LoginPage;
import page.TaxDocuments;

public class DownloadTaxDocuments extends testBase{
    // Shared between all tests in this class.

    InvestorDetails investorDetails = new InvestorDetails(page);
    TaxDocuments taxDocuments = new TaxDocuments(page);

    public void DownloadTaxDocuments(String investorId) throws InterruptedException {
        try{
            String url = investorDetails.setUrlInvestorDetails(investorId);
            page.navigate(url);
            Thread.sleep(6000);
            page.waitForLoadState();

            int yearsCount = 0;
            yearsCount = taxDocuments.countYearsAtSelectbox();

            System.out.println("There are: "+ yearsCount +" years");
            String[] years = new String[yearsCount];
            years = taxDocuments.getYears();

            for (int i =0; i<yearsCount; i++){
                String year = years[i];
                taxDocuments.gotoNextTaxPage(investorId,year);
                int count = taxDocuments.returnRows();
                System.out.println(years[i] + ": " +count);

                int countEntries = 0;

                //Check entries
                if(taxDocuments.returnRows()==0){
                    System.out.println("NO ENTRIES");
                }else{
                    System.out.println("THERE ARE ENTRIES");
                    countEntries = countEntries + taxDocuments.returnRows();
                }

                String[][] arr_taxDocs = new String[countEntries][8];



            }



        }catch (Exception e){
            System.out.println(e);
        }
    }

}
