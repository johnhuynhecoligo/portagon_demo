package steps;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import utils.NameFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.util.UUID;

@Execution(ExecutionMode.CONCURRENT) //Enable parallel execution. Config in the playwright.config file
 @TestMethodOrder(MethodOrderer.DisplayName.class) //Order test execution by Display name
public class testBase {
    private static Properties properties;
    private static FileInputStream fileIn;
    static NameFile nameFile = new NameFile();
    static Browser browser;
    static Page page;
    static Playwright playwright;
    static BrowserContext context;

    //Get the pat of the project
    static String projectPath = System.getProperty("user.dir") + "/";
    //Get the path of the configs.properties
    static String propertiesFilePathRoot = "src/test/java/resources/configs.properties";

    @BeforeAll
    public static void beforeAll(){
        try{
            playwright = Playwright.create();
            setPropertiesFile(projectPath, propertiesFilePathRoot);

            //Change setHeadless to True for headless running
            BrowserType.LaunchOptions options = new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(50);
            browser = playwright.chromium().launch(options);
            context = browser.newContext(new Browser.NewContextOptions().setViewportSize(1800,950));
//            context.tracing().start(new Tracing.StartOptions()
//                    .setScreenshots(true)
//                    .setSnapshots(true)
//                    .setSources(true));
            String url = getPropValue("url");

            page = context.newPage();
            page.navigate(url);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @AfterAll
    public static void afterAll() throws IOException {
        String traceName = nameFile.setFileName("trace");
//        context.tracing().stop(new Tracing.StopOptions()
//                .setPath(Paths.get("trace/"+traceName)));
        context.close();
        browser.close();
        playwright.close();
    }

    public static void setPropertiesFile(String projectPath, String propertiesFilePathRoot) {
        properties = new Properties();
        try {
            //Initial the object from class FileInputStream
            fileIn = new FileInputStream(projectPath + propertiesFilePathRoot);
            //Load properties file
            properties.load(fileIn);
        } catch (Exception exp) {
//            System.out.println(exp.getMessage());
//            System.out.println(exp.getCause());
            exp.printStackTrace();
        }
    }

    public static String getPropValue(String KeyProp) {
        String value = "";
        try {
            //get values from properties file
            value = properties.getProperty(KeyProp);
//            System.out.println(value);
            return value;
        } catch (Exception exp) {
//            System.out.println(exp.getMessage());
//            System.out.println(exp.getCause());
            exp.printStackTrace();
        }
        return value;
    }

    public void writeCSV(String[][] courseArray, String investorID, String folderName, String fileName) throws Exception {

        try{
            Path path = Paths.get("Downloads/"+investorID);
            Files.createDirectories(path);
            path = Paths.get("Downloads/"+investorID+"/"+folderName);
            Files.createDirectories(path);
        }catch (Exception ie){
            System.err.println(ie);
        }

        fileName = setNameFile(fileName);

        //create a File class object and give the file the name .csv
        java.io.File courseCSV = new java.io.File("Downloads/"+investorID+"/"+folderName+"/"+fileName+".csv");

        //Create a Printwriter text output stream and link it to the CSV File
        java.io.PrintWriter outfile = new java.io.PrintWriter(courseCSV);


        for (int row = 0; row<courseArray.length; row++){
            for (int col=0; col<courseArray[row].length; col++){
                outfile.write(courseArray[row][col] + ",");
            }
            outfile.write("\n");

        }

        outfile.close();
    } //end writeCSV()

    public void writeHTML(String htmlContent, String investorID, String folderName, String fileName){
        try{
            Path path = Paths.get("Downloads/"+investorID);
            Files.createDirectories(path);
            path = Paths.get("Downloads/"+investorID+"/"+folderName);
            Files.createDirectories(path);
            fileName = setNameFile(fileName);

            //create a File class object and give the file the name employees.csv
            java.io.File courseCSV = new java.io.File("Downloads/"+investorID+"/"+folderName+"/"+fileName+".html");

            //Create a Printwriter text output stream and link it to the CSV File
            java.io.PrintWriter outfile = new java.io.PrintWriter(courseCSV);
            outfile.write(htmlContent);

        }catch (Exception ie){
            System.err.println(ie);
        }
    }

    public void navigateToUrl (String url) throws InterruptedException{
        page.navigate(url);
        Thread.sleep(3000);
//        page.locator(locatorToVerify).wait(3000);
    }

    public String setNameFile (String fileName) {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
        String filename = "";
        filename = fileName + "_" + timeStamp;
        return filename;
    }

    public String splitString(String data, String unexpectedString){
        String[] splits = data.split(unexpectedString);
        StringBuilder stringBuilder = new StringBuilder();
        for (String item: splits){
            stringBuilder.append(item);
        }
        String split_data = stringBuilder.toString().trim();
        return split_data;
    }

    public String genUUID(){
        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();
        return uuidAsString;
    }

    public String cutString(String in, String splitString){
        String[] splits = in.split(splitString);
        StringBuilder stringBuilder = new StringBuilder();
        for (String item: splits){
            stringBuilder.append(item);
        }
        String value ="";
        value = stringBuilder.toString();
        return value;
    }

}
