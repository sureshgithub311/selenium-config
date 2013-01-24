package com.wikia.webdriver.PageObjects.PageObject.ForumPageObject;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;

import com.wikia.webdriver.Common.Logging.PageObjectLogging;
import com.wikia.webdriver.PageObjects.PageObject.BasePageObject;
import com.wikia.webdriver.PageObjects.PageObject.MiniEditor.MiniEditorComponentObject;

/**
 * Abstract representation of a Forum Thread. 
 * Example: http://mediawiki119.wikia.com/wiki/Thread:41679
 */

public class ForumThreadPageObject extends BasePageObject{

	@FindBy(css="div.msg-title a")
	private WebElement discussionTitle;
	@FindBy(css=".replyButton")
	private WebElement replyButton;
	@FindBy(css=".quote-button")
	private WebElement quoteButton;
	@FindBy(css=".speech-bubble-message nav")
	private WebElement moreButton;
	@FindBy(css=".WikiaMenuElement .remove-message")
	private WebElement removeButton;
	@FindBy(css=".wall-action-reason")
	private WebElement removeThreadModal_Textarea;
	@FindBy(css="#WikiaConfirmOk")
	private WebElement removeThreadModal_removeButton;
	@FindBy(css=".speech-bubble-message-removed")
	private WebElement threadRemovedMessage;
	@FindBy(css=".speech-bubble-message-removed a")
	private WebElement undoThreadRemoveButton;
	@FindBys(@FindBy(css="div.msg-body p"))
	private List<WebElement> discussionBody;
	
	private String wikiaEditorTextarea = "textarea.replyBody";
	
	MiniEditorComponentObject miniEditor;
	
	public ForumThreadPageObject(WebDriver driver) {
		super(driver);
		miniEditor = new MiniEditorComponentObject(driver, Domain);
		PageFactory.initElements(driver, this);
	}

	public void verifyDiscussionTitleAndMessage(String title, String message) {
		waitForElementByElement(discussionTitle);
		waitForElementByElement(discussionBody.get(0));
		waitForTextToBePresentInElementByElement(discussionTitle, title);
		waitForTextToBePresentInElementByElement(discussionBody.get(0), message);
		PageObjectLogging.log("verifyDiscussionWithTitle", "discussion with title and message verified", true);			
	}

	public void reply(String message) {
		jQueryFocus(wikiaEditorTextarea);		
		driver.switchTo().frame(miniEditor.miniEditorIframe);
		miniEditor.writeMiniEditor(message);
		driver.switchTo().defaultContent();	
		clickReplyButton();
		PageObjectLogging.log("reply", "write a reply with the following text: "+message, true, driver);								
	}

	public void verifyReplyMessage(int replyNumber, String message) {
		WebElement replyMessage = driver.findElement(By.cssSelector(".replies li:nth-child("+replyNumber+") p"));
		waitForTextToBePresentInElementByElement(replyMessage, message);
		PageObjectLogging.log("verifyReplyMessage", "verify that message number "+replyNumber+" has the following message: "+message, true);					
	}
	
	public void verifyMessageWithQuotation(int replyNumber, String message) {
		WebElement replyMessage = driver.findElement(By.cssSelector(".replies li:nth-child("+replyNumber+") p"));
		waitForTextToBePresentInElementByElement(replyMessage, message);
		waitForElementClickableByCss(".replies li:nth-child("+replyNumber+") div.quote");
		PageObjectLogging.log("verifyQuotedMessage", "verify that message number "+replyNumber+" has a quotation of the author and the following message: "+message, true);					
	}

	public void clickReplyButton() {
		waitForElementByElement(replyButton);
		waitForElementClickableByElement(replyButton);
		clickAndWait(replyButton);
		PageObjectLogging.log("clickReplyButton", "reply button clicked", true, driver);					
	}

	public void quoteTheThreadsAuthor(String message) {
		refreshPage();
		executeScript("document.getElementsByClassName(\"buttons\")[1].style.display = \"block\"");
		waitForElementByElement(quoteButton);
		waitForElementClickableByElement(quoteButton);
		clickAndWait(quoteButton);
		waitForElementByElement(miniEditor.miniEditorIframe);
		driver.switchTo().frame(miniEditor.miniEditorIframe);
		miniEditor.writeMiniEditor(message);
		driver.switchTo().defaultContent();	
		clickReplyButton();
		PageObjectLogging.log("quoteTheThreadsAuthor", "quote the author of the thread with the following text: "+message, true, driver);								
	}

	public void removeThread(String reason) {	
		clickOnMoreButton();
		clickOnRemoveButton();
		waitForElementByElement(removeThreadModal_Textarea);
		removeThreadModal_Textarea.sendKeys(reason);
		waitForElementByElement(removeThreadModal_removeButton);
		waitForElementClickableByElement(removeThreadModal_removeButton);
		clickAndWait(removeThreadModal_removeButton);		
		PageObjectLogging.log("removeThread", "removed thread with the following reason: "+reason, true, driver);								
	}

	private void clickOnRemoveButton() {
		waitForElementByElement(removeButton);
		waitForElementClickableByElement(removeButton);
		clickAndWait(removeButton);		
		PageObjectLogging.log("clickOnRemoveButton", "click on 'remove' button", true, driver);								
	}

	public void clickOnMoreButton() {
		executeScript("document.getElementsByClassName(\"buttons\")[1].style.display = \"block\"");
		waitForElementByElement(moreButton);
		waitForElementClickableByElement(moreButton);
		clickAndWait(moreButton);	
		PageObjectLogging.log("clickOnMoreButton", "click on 'more' button on a message", true, driver);								
	}

	public void verifyThreadRemoved() {
		waitForTextToBePresentInElementByElement(threadRemovedMessage, "thread has been removed");
		PageObjectLogging.log("verifyThreadRemoved", "Thread has been removed", true);								
	}

	public void undoRemove() {
		waitForElementByElement(undoThreadRemoveButton);
		waitForElementClickableByElement(undoThreadRemoveButton);
		clickAndWait(undoThreadRemoveButton);		
		PageObjectLogging.log("undoRemove", "click on 'undo' button", true, driver);
	}
}
