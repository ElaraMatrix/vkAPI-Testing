import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.utilities.JsonSettingsFile;
import models.Photo;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AuthorizationPage;
import pages.ProfilePage;
import pages.SideBar;
import utils.Randomizer;
import utils.VkAPI;

public class TestCase extends BaseTest {

    @Test
    public void smartVkTest() {
        AuthorizationPage authorizationPage = new AuthorizationPage();
        SideBar sideBar = new SideBar();
        ProfilePage profilePage = new ProfilePage();

        AqualityServices.getBrowser().goTo(new JsonSettingsFile("settings.json").getValue("/default_url").toString());
        authorizationPage.state().waitForDisplayed();

        authorizationPage.login();
        sideBar.state().waitForDisplayed();

        sideBar.clickOnMyProfile();
        profilePage.state().waitForDisplayed();

        String randomText = Randomizer.getRandomWord();
        String postID = VkAPI.createWallPost(randomText);
        Assert.assertTrue(profilePage.isPostAdded(randomText, postID), "Post hasn't added");

        Photo uploadedPhoto = VkAPI.uploadPhoto();
        VkAPI.updateWallPost(postID, "EDITED", uploadedPhoto);
        Assert.assertTrue(profilePage.isPostUpdated(randomText, uploadedPhoto, postID), "Post hasn't updated");

        String replyID = VkAPI.addReplyToWallPost(postID, randomText);
        Assert.assertTrue(profilePage.isReplyAdded(randomText, replyID, postID), "Reply hasn't added");

        profilePage.clickOnLike(postID);
        Assert.assertTrue(VkAPI.isPostLiked(postID), "Post hasn't liked");

        VkAPI.deleteWallPost(postID);
        Assert.assertTrue(profilePage.isPostDeleted(postID), "Post hasn't deleted");
    }
}