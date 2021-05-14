package Gmail;

import org.openqa.selenium.By;

public class SingnInPage extends HomePage{

    public void SignInPage(){
        driver1.findElement(By.id("identifierId")).sendKeys("chma616@gmail.com");
    }
}
