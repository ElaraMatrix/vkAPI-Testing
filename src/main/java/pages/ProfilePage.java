package pages;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.elements.interfaces.IElementFactory;
import aquality.selenium.elements.interfaces.ILabel;
import aquality.selenium.forms.Form;
import logger.Log;
import models.Photo;
import org.openqa.selenium.By;
import utils.VkAPI;

public class ProfilePage extends Form {

    private final IElementFactory elements = AqualityServices.getElementFactory();

    private final static String postAuthorXPath = "//a[@class='author']";
    private final static String postTextXPath = "//div[contains(@class, 'wall_post_text')]";
    private final static String replyTextXPath = "//div[contains(@class, 'wall_reply_text')]";
    private final static String repliesNextLabelXPath = "//span[@class='js-replies_next_label']";
    private final static String likePostButtonXPath = "//span[contains(@class, 'PostBottomAction')]";

    public ProfilePage() {
        super(By.id("profile"), "ProfilePage");
    }

    private static String getFormatPostIDXpath(String id) {
        Log.info("Get formatted post ID");
        return "//div[@id='post" + VkAPI.getAccount().getId() + "_" + id + "']";
    }

    public boolean isPostAdded(String text, String postID) {
        Log.info("Post add check");
        elements.getLabel(By.xpath(getFormatPostIDXpath(postID)), "").state().waitForDisplayed();
        return elements.getLabel(By.xpath(getFormatPostIDXpath(postID) + postAuthorXPath), "").getAttribute("href").contains(String.valueOf(VkAPI.getAccount().getId()))
        && elements.getLabel(By.xpath(getFormatPostIDXpath(postID) + postTextXPath), "").getText().equals(text);
    }

    public boolean isPostUpdated(String previousText, Photo photo, String postID) {
        Log.info("Post update check");
        return elements.getLabel(By.xpath(getFormatPostIDXpath(postID) + "//a[contains(@href, '" + photo.getId() + "')]"), "").state().waitForDisplayed()
        && !elements.getLabel(By.xpath(getFormatPostIDXpath(postID) + postTextXPath), "").getText().equals(previousText);
    }

    public boolean isPostDeleted(String postID) {
        Log.info("Post delete check");
        return elements.getLabel(By.xpath(getFormatPostIDXpath(postID)), "").state().waitForNotDisplayed();
    }

    public boolean isReplyAdded(String text, String replyID, String postID) {
        Log.info("Reply add check");
        ILabel replyNext = elements.getLabel(By.xpath(getFormatPostIDXpath(postID) + repliesNextLabelXPath), "");
        replyNext.state().waitForClickable();
        replyNext.click();
        return elements.getLabel(By.xpath(getFormatPostIDXpath(replyID) + postAuthorXPath), "").getAttribute("href").contains(String.valueOf(VkAPI.getAccount().getId()))
        && elements.getLabel(By.xpath(getFormatPostIDXpath(replyID) + replyTextXPath), "").getText().equals(text);
    }

    public void clickOnLike(String postID) {
        Log.info("Add like to post");
        elements.getButton(By.xpath(getFormatPostIDXpath(postID) + likePostButtonXPath), "").click();
    }
}