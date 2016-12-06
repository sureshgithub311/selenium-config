package com.wikia.webdriver.pageobjectsfactory.pageobject.wikipage;

import org.openqa.selenium.By;

import com.wikia.webdriver.pageobjectsfactory.pageobject.WikiBasePageObject;

public class WikiArticleHomePage extends WikiBasePageObject {

  private By wikiHomePageSpecificElement = By.cssSelector("#HOME_TOP_LEADERBOARD");

  /**
   * Check if current page is in fact home page of wiki
   */
  public void verifyThisIsWikiHomePage() {
    wait.forElementPresent(wikiHomePageSpecificElement);
  }
}
