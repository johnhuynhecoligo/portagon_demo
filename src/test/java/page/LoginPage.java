package page;

import com.microsoft.playwright.Page;
import org.junit.jupiter.api.Assertions;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class LoginPage {
    Page page;

    public LoginPage(Page page){
        this.page = page;
    }

    //locators
    private String txt_email = "[id='session_email']";
    private String txt_pass = "[id='session_password']";
    private String btn_login = "[name='commit']";
    private String lbl_welcome = "div[class=\"welcome__title\"]";

    public void enterDataAndLogin(String email, String pass) throws InterruptedException {
        page.locator(txt_email).fill(email);
        page.locator(txt_pass).fill(pass);
        page.locator(btn_login).click();
        page.locator(lbl_welcome).waitFor();
    }

}
