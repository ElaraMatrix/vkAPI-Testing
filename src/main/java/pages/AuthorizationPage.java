package pages;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.utilities.JsonSettingsFile;
import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import logger.Log;
import org.openqa.selenium.By;

public class AuthorizationPage extends Form {

    private final ITextBox emailField = AqualityServices.getElementFactory().getTextBox(By.id("index_email"), "EmailField");
    private final ITextBox passwordField = AqualityServices.getElementFactory().getTextBox(By.id("index_pass"), "PassField");
    private final IButton loginButton = AqualityServices.getElementFactory().getButton(By.id("index_login_button"), "LoginButton");

    public AuthorizationPage() {
        super(By.id("index_login_button"), "AuthorizationPage");
    }

    public void login() {
        Log.info("Log in");
        emailField.type(new JsonSettingsFile("credentials.json").getValue("/login").toString());
        passwordField.type(new JsonSettingsFile("credentials.json").getValue("/password").toString());
        loginButton.click();
    }
}