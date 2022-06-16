package pages;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.forms.Form;
import logger.Log;
import org.openqa.selenium.By;

public class SideBar extends Form {

    private final IButton myProfile = AqualityServices.getElementFactory().getButton(By.id("l_pr"), "MyPageButton");

    public SideBar() {
        super(By.id("side_bar_inner"), "SideBar");
    }

    public void clickOnMyProfile() {
        Log.info("Click on My Profile button");
        myProfile.click();
    }
}