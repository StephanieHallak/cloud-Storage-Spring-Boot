package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
    @FindBy(id = "logout-button")
    private WebElement logoutButton;

    @FindBy(id = "addnote-button")
    private WebElement addnoteButton;

    @FindBy(id = "note-title")
    private WebElement noteTitle;

    @FindBy(id = "note-description")
    private WebElement noteDescription;

    @FindBy(id = "noteSubmit")
    private WebElement noteSubmit;

    @FindBy(id="nav-notes-tab")
    public WebElement noteNavigation;

    @FindBy(xpath = "//*[@id=\"userTable\"]/tbody/tr[last()]/th")
    private WebElement noteContent;

    @FindBy(xpath = "//*[@id=\"userTable\"]/tbody/tr/td[1]/button")
    private WebElement noteEdit;

    @FindBy(id = "deleteNote")
    private WebElement noteDelete;

    @FindBy(id = "credential-url")
    private WebElement credentialUrl;

    @FindBy(id = "credential-username")
    private WebElement credentialUsername;

    @FindBy(id = "credential-password")
    private WebElement credentialPassword;

    @FindBy(id = "credentialSubmit")
    private WebElement credentialSubmit;

    @FindBy(id="addcredential-button")
    private WebElement addCredentialButton;

    @FindBy(id="nav-credentials-tab")
    public WebElement credentialNavigation;

    @FindBy(xpath = "//*[@id=\"credentialTable\"]/tbody/tr[last()]/td[1]/button")
    private WebElement credentialEdit;

    @FindBy(id = "deleteCredential")
    private WebElement credentialDelete;

    @FindBy(xpath = "//*[@id=\"credentialTable\"]/tbody/tr[last()]/th")
    private WebElement credentialUrlContent;

    @FindBy(xpath = "//*[@id=\"credentialTable\"]/tbody/tr[last()]/td[3]")
    private WebElement credentialPasswordContent;

    public HomePage (WebDriver webDriver){
        PageFactory.initElements(webDriver, this);
    }

    public void logout() {
        this.logoutButton.submit();
    }

    public void createNote(String title, String description, WebDriver driver){
        this.noteNavigation.click();
        var script = this.addnoteButton.getAttribute("onclick");
        var js = (JavascriptExecutor)driver;
        js.executeScript(script);
        driver.switchTo().activeElement();
        var w = new WebDriverWait(driver, 3);
        w.until(ExpectedConditions.visibilityOf(this.noteDescription));
        w.until(ExpectedConditions.visibilityOf(this.noteTitle));
        this.noteDescription.sendKeys(description);
        this.noteTitle.sendKeys(title);
        this.noteSubmit.submit();
    }

    public void editNote(String title,String description, WebDriver driver) {
        this.noteNavigation.click();
        var script = this.noteEdit.getAttribute("onclick");
        var js = (JavascriptExecutor)driver;
        js.executeScript(script);
        driver.switchTo().activeElement();
        var w = new WebDriverWait(driver,3);
        w.until(ExpectedConditions.visibilityOf(this.noteDescription));
        w.until(ExpectedConditions.visibilityOf(this.noteTitle));
        this.noteDescription.clear();
        this.noteDescription.sendKeys(description);
        this.noteTitle.clear();
        this.noteTitle.sendKeys(title);
        this.noteSubmit.submit();
    }

    public void deleteNote(WebDriver driver){
        this.noteNavigation.click();
        var w = new WebDriverWait(driver,3);
        w.until(ExpectedConditions.elementToBeClickable(this.noteDelete));
        this.noteDelete.click();
        driver.switchTo().activeElement();
    }

    public String getNoteContent(){
        this.noteNavigation.click();
        try {
            return this.noteContent.getAttribute("innerHTML");
        } catch (NoSuchElementException ex) {
            return "";
        }
    }

    public void createCredential(String url,String username,String password, WebDriver driver) {
        this.credentialNavigation.click();
        var script = this.addCredentialButton.getAttribute("onclick");
        var js = (JavascriptExecutor)driver;
        js.executeScript(script);
        driver.switchTo().activeElement();
        var w = new WebDriverWait(driver,3);
        w.until(ExpectedConditions.visibilityOf(this.credentialUrl));
        w.until(ExpectedConditions.visibilityOf(this.credentialUsername));
        w.until(ExpectedConditions.visibilityOf(this.credentialPassword));
        this.credentialUrl.sendKeys(url);
        this.credentialPassword.sendKeys(password);
        this.credentialUsername.sendKeys(username);
        this.credentialSubmit.submit();
    }

    public String editCredential(String url, String username, String password,WebDriver driver) {
        this.credentialNavigation.click();
        var script = this.credentialEdit.getAttribute("onclick");
        var js = (JavascriptExecutor) driver;
        js.executeScript(script);
        driver.switchTo().activeElement();
        var w = new WebDriverWait(driver, 10);
        w.until(ExpectedConditions.visibilityOf(this.credentialPassword));
        w.until(ExpectedConditions.visibilityOf(this.credentialUsername));
        w.until(ExpectedConditions.visibilityOf(this.credentialUrl));
        var decryptedPassword =  driver.findElement(By.xpath("//*[@id=\"credential-password\"]")).getAttribute("value");
        this.credentialUrl.clear();
        this.credentialUrl.sendKeys(url);
        this.credentialUsername.clear();
        this.credentialUsername.sendKeys(username);
        this.credentialPassword.clear();
        this.credentialPassword.sendKeys(password);
        this.credentialSubmit.submit();
        return decryptedPassword;
    }

    public void deleteCredential(WebDriver driver) {
        this.credentialNavigation.click();
        var w = new WebDriverWait(driver,3);
        w.until(ExpectedConditions.elementToBeClickable(this.credentialDelete));
        this.credentialDelete.click();
        driver.switchTo().activeElement();
    }

    public String getCredentialPassword() {
        this.credentialNavigation.click();
        try {
            return this.credentialPasswordContent.getAttribute("innerHTML");
        } catch (NoSuchElementException ex) {
            return "";
        }
    }

    public String getCredentialUrl() {
        this.credentialNavigation.click();
        try {
            return this.credentialUrlContent.getAttribute("innerHTML");
        } catch (NoSuchElementException ex) {
            return "";
        }
    }
}
