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
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import java.io.IOException;
import java.nio.file.Paths;

@Execution(ExecutionMode.CONCURRENT) //Enable parallel execution. Config in the playwright.config file
 @TestMethodOrder(MethodOrderer.DisplayName.class) //Order test execution by Display name
public class testBase {
    private static String url = "";
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
    public static APIRequestContext apiRequestContext;

    @BeforeAll
    public static void beforeAll(){
        try{
            playwright = Playwright.create();
            setPropertiesFile(projectPath, propertiesFilePathRoot);

            //Change setHeadless to True for headless running
            BrowserType.LaunchOptions options = new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(50);
            browser = playwright.chromium().launch(options);
            context = browser.newContext();
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
            System.out.println(exp.getMessage());
            System.out.println(exp.getCause());
            exp.printStackTrace();
        }
    }

    public static String getPropValue(String KeyProp) {
        String value = "";
        try {
            //get values from properties file
            value = properties.getProperty(KeyProp);
            System.out.println(value);
            return value;
        } catch (Exception exp) {
            System.out.println(exp.getMessage());
            System.out.println(exp.getCause());
            exp.printStackTrace();
        }
        return value;
    }

}
