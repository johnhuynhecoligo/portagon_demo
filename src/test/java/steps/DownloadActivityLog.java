package steps;

import page.ActivityLog;

public class DownloadActivityLog extends testBase{
    // Shared between all tests in this class.

    ActivityLog activityLog = new ActivityLog(page);

    public String[][] activityLog_array;

    public void downloadInvestorActivityLog(String investorId) throws Exception {
        Thread.sleep(2000);
        try {
            String url = activityLog.url_activityLog+investorId;
            String[] cell_data;
            int row_total = 0;
            int total_page = 1;
            String temp = "";

            navigateToUrl(url);

            if(page.isHidden(activityLog.pagination)){
                System.out.println("There is 1 page");
                total_page = 1;
                int count_row_per_page = 0;
                count_row_per_page = activityLog.getRowsPerPage();
                System.out.println("There are: "+ count_row_per_page + " rows");
                //Create new array
                activityLog_array = new String[count_row_per_page+1][7];
                //Header for CSV
                activityLog_array[0][0]="activity_id";
                activityLog_array[0][1]="date";
                activityLog_array[0][2]="object";
                activityLog_array[0][3]="action";
                activityLog_array[0][4]="specification";
                activityLog_array[0][5]="specification_href";
                activityLog_array[0][6]="triggered_by";

                for (int rowCount = 1; rowCount<=count_row_per_page; rowCount++){
                    cell_data = activityLog.getCelData(rowCount);
                    //Get columns per row
                    for(int columnCount=1; columnCount<=cell_data.length; columnCount++){
                        String arr_id = activityLog.getActivityID(rowCount);

                        //Content
                        activityLog_array[rowCount][0]=arr_id;
                        activityLog_array[rowCount][1]=cell_data[1].trim();
                        activityLog_array[rowCount][2]=cell_data[2].trim();
                        activityLog_array[rowCount][3]=cell_data[3].trim();

                        //Get the element of Specification column
                        String[] attr = activityLog.getAtributeSpecification(rowCount);
                        temp = attr[0];
                        temp = splitString(temp, "\n");
                        temp = splitString(temp, " ");
                        activityLog_array[rowCount][4]= temp;
                        activityLog_array[rowCount][5]=attr[1];

                        activityLog_array[rowCount][6]=cell_data[5].trim();
                    }
                }

            }else{
                total_page = activityLog.getTotalPage();
                System.out.println("There are "+activityLog.getTotalPage()+" pages");

                String url1=url+"?page="+total_page;

                navigateToUrl(url1);
                int count_row_lastpage = activityLog.getRowsPerPage();
                row_total = (total_page-1)*25;
                row_total = row_total + count_row_lastpage;

                //Create new array
                activityLog_array = new String[row_total+1][7];
                //Header for CSV
                activityLog_array[0][0]="activity_id";
                activityLog_array[0][1]="date";
                activityLog_array[0][2]="object";
                activityLog_array[0][3]="action";
                activityLog_array[0][4]="specification";
                activityLog_array[0][5]="specification_href";
                activityLog_array[0][6]="triggered_by";

                //Go to one page
                for (int pageCount = 1; pageCount<=total_page; pageCount++){
                    int new_arr_row = 0;
                    //Count no. of rows at each page
                    String url2=url+"?page="+pageCount;
                    navigateToUrl(url2);
                    int count_row_per_page = 0;
                    count_row_per_page = activityLog.getRowsPerPage();
                    //Get row per page
                    for (int rowCount = 1; rowCount<=count_row_per_page; rowCount++){
                        cell_data = activityLog.getCelData(rowCount);
                        //Get columns per row
                        for(int columnCount=1; columnCount<=cell_data.length-1; columnCount++){
                            int count_first_row = 0;
                            if (pageCount>1){
                                count_first_row = count_first_row + ((pageCount-1)*25);
                            }

                            //Get InvestorID
                            String arr_id = activityLog.getActivityID(rowCount);

                            //Content
                            activityLog_array[rowCount + count_first_row][0]=arr_id;
                            activityLog_array[rowCount + count_first_row][1]=cell_data[1].trim();
                            activityLog_array[rowCount + count_first_row][2]=cell_data[2].trim();
                            activityLog_array[rowCount + count_first_row][3]=cell_data[3].trim();

                            //Get the element of Specification column
                            String[] attr = activityLog.getAtributeSpecification(rowCount);
                            temp = attr[0];
                            temp = splitString(temp, "\n");
                            temp = splitString(temp, " ");
                            activityLog_array[rowCount + count_first_row][4]= temp;
                            activityLog_array[rowCount + count_first_row][5]=attr[1];

                            activityLog_array[rowCount + count_first_row][6]=cell_data[5].trim();
                        }
                    }
                }
            }

            System.out.println("Total: " + row_total);

            for (int i = 0; i<activityLog_array.length; i++){
                for (int j=0; j<activityLog_array[i].length; j++){
                    System.out.print(activityLog_array[i][j] + "\t");
                }

                System.out.println("\n");
            }

            //Write CSV
            String folder_name = investorId;
            if (activityLog_array.length>0){
                writeCSV(activityLog_array, folder_name, "ActivityLog" ,"ActivityList");
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }

}
