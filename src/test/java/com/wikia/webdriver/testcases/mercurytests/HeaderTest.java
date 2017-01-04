package com.wikia.webdriver.testcases.mercurytests;

import com.wikia.webdriver.common.contentpatterns.MercuryWikis;
import com.wikia.webdriver.common.core.Assertion;
import com.wikia.webdriver.common.core.annotations.Execute;
import com.wikia.webdriver.common.core.annotations.InBrowser;
import com.wikia.webdriver.common.core.api.ArticleContent;
import com.wikia.webdriver.common.core.api.TemplateContent;
import com.wikia.webdriver.common.core.drivers.Browser;
import com.wikia.webdriver.common.core.helpers.ContentLoader;
import com.wikia.webdriver.common.core.helpers.Emulator;
import com.wikia.webdriver.common.templates.NewTestTemplate;
import com.wikia.webdriver.elements.mercury.components.Header;
import com.wikia.webdriver.elements.mercury.pages.ArticlePage;

import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

@Test(groups = "Mercury_Header")
@Execute(onWikia = MercuryWikis.MERCURY_AUTOMATION_TESTING)
@InBrowser(browser = Browser.CHROME, emulator = Emulator.GOOGLE_NEXUS_5)
public class HeaderTest extends NewTestTemplate {

  private static final String ARTICLE_WITHOUT_INFOBOX = ContentLoader
      .loadWikiTextContent("Article_without_infobox");

  private static final String TEMPLATE_INFOBOX_AUTOMATION_NAME = "InfoboxAutomation";
  private static final String TEMPLATE_INFOBOX_AUTOMATION = ContentLoader
      .loadWikiTextContent("Template_MercuryInfoboxAutomation");
  private static final String TEMPLATE_INFOBOX_AUTOMATION_STACKED_NAME = "InfoboxAutomationStacked";
  private static final String TEMPLATE_INFOBOX_AUTOMATION_STACKED = ContentLoader
      .loadWikiTextContent("Template_MercuryInfoboxAutomationStacked");

  private static final String ARTICLE_INFOBOX1 = ContentLoader
      .loadWikiTextContent("Mercury_Infobox1");
  private static final String ARTICLE_INFOBOX2 = ContentLoader
      .loadWikiTextContent("Mercury_Infobox2");
  private static final String ARTICLE_INFOBOX4 = ContentLoader
      .loadWikiTextContent("Mercury_Infobox4");

  private static final String HEADER_MESSAGE = "Header";
  private static final String PAGE_TITLE_MESSAGE = "Page title";
  private static final String HERO_IMAGE_MESSAGE = "Hero image";

  private static final String VISIBLE_MESSAGE = "visible";
  private static final String INVISIBLE_MESSAGE = "invisible";

  private static final String ELEMENT_EXPECTATION_MESSAGE_TEMPLATE = "%s was expected to be %s.";
  private static final String INVALID_ATTRIBUTE_MESSAGE_TEMPLATE = "%s has invalid %s attribute.";


  @Test(groups = "mercury_header_checkElementsVisibilityWithoutInfobox")
  public void mercury_header_checkElementsVisibilityWithoutInfobox() {
    new ArticleContent().push(ARTICLE_WITHOUT_INFOBOX);

    Header header = new ArticlePage()
        .open()
        .getHeader();

    Assertion.assertTrue(
        header.isHeaderVisible(),
        String.format(ELEMENT_EXPECTATION_MESSAGE_TEMPLATE, HEADER_MESSAGE, VISIBLE_MESSAGE)
    );
    Assertion.assertTrue(
        header.isPageTitleVisible(),
        String.format(ELEMENT_EXPECTATION_MESSAGE_TEMPLATE, PAGE_TITLE_MESSAGE, VISIBLE_MESSAGE)
    );
    Assertion.assertFalse(
        header.isHeroImageVisible(),
        String.format(ELEMENT_EXPECTATION_MESSAGE_TEMPLATE, HERO_IMAGE_MESSAGE, INVISIBLE_MESSAGE)
    );
  }

  @Test(groups = "mercury_header_checkElementsVisibilityWithInfoboxAndWithHeroImage")
  public void mercury_header_checkElementsVisibilityWithInfoboxAndWithHeroImage() {
    new TemplateContent().push(TEMPLATE_INFOBOX_AUTOMATION, TEMPLATE_INFOBOX_AUTOMATION_NAME);
    new ArticleContent().push(ARTICLE_INFOBOX1);

    Header header = new ArticlePage()
        .open()
        .getHeader();

    Assertion.assertTrue(
        header.isHeaderVisible(),
        String.format(ELEMENT_EXPECTATION_MESSAGE_TEMPLATE, HEADER_MESSAGE, VISIBLE_MESSAGE)
    );
    Assertion.assertTrue(
        header.isPageTitleVisible(),
        String.format(ELEMENT_EXPECTATION_MESSAGE_TEMPLATE, PAGE_TITLE_MESSAGE, VISIBLE_MESSAGE)
    );
    Assertion.assertTrue(
        header.isHeroImageVisible(),
        String.format(ELEMENT_EXPECTATION_MESSAGE_TEMPLATE, HERO_IMAGE_MESSAGE, VISIBLE_MESSAGE)
    );
  }

  @Test(groups = "mercury_header_checkElementsVisibilityWithInfoboxAndWithoutHeroImage")
  public void mercury_header_checkElementsVisibilityWithInfoboxAndWithoutHeroImage() {
    new TemplateContent().push(TEMPLATE_INFOBOX_AUTOMATION_STACKED, TEMPLATE_INFOBOX_AUTOMATION_STACKED_NAME);
    new ArticleContent().push(ARTICLE_INFOBOX2);

    Header header = new ArticlePage()
            .open()
            .getHeader();

    Assertion.assertTrue(
        header.isHeaderVisible(),
        String.format(ELEMENT_EXPECTATION_MESSAGE_TEMPLATE, HEADER_MESSAGE, VISIBLE_MESSAGE)
    );
    Assertion.assertTrue(
        header.isPageTitleVisible(),
        String.format(ELEMENT_EXPECTATION_MESSAGE_TEMPLATE, PAGE_TITLE_MESSAGE, VISIBLE_MESSAGE)
    );
    Assertion.assertFalse(
        header.isHeroImageVisible(),
        String.format(ELEMENT_EXPECTATION_MESSAGE_TEMPLATE, HERO_IMAGE_MESSAGE, INVISIBLE_MESSAGE)
    );
  }

  @Test(groups = "mercury_header_heroImageIsProperlyStyled")
  public void mercury_header_heroImageIsProperlyStyled() {
    new TemplateContent().push(TEMPLATE_INFOBOX_AUTOMATION, TEMPLATE_INFOBOX_AUTOMATION_NAME);
    new ArticleContent().push(ARTICLE_INFOBOX1);

    Header header = new ArticlePage()
            .open()
            .getHeader();

    Map<String, String> attributeExpectedValues = new HashMap<String, String>() {{
      put("background-color", "rgba(255, 255, 255, 1)");
      put("background-position", "50% 50%");
      put("background-repeat", "no-repeat");
    }};

    for (Map.Entry<String, String> attribute : attributeExpectedValues.entrySet()) {
      String attributeName = attribute.getKey();
      Assertion.assertEquals(
          header.getHeroImageCssValue(attributeName),
          attribute.getValue(),
          String.format(INVALID_ATTRIBUTE_MESSAGE_TEMPLATE, HERO_IMAGE_MESSAGE, attributeName)
      );
    }
  }

  @Test(groups = "mercury_header_heroImageIsSquare")
  public void mercury_header_heroImageIsSquare() {
    new TemplateContent().push(TEMPLATE_INFOBOX_AUTOMATION, TEMPLATE_INFOBOX_AUTOMATION_NAME);
    new ArticleContent().push(ARTICLE_INFOBOX1);

    Header header = new ArticlePage()
            .open()
            .getHeader();

    Assertion.assertTrue(
        header.isHeroImageSquare(),
        String.format(ELEMENT_EXPECTATION_MESSAGE_TEMPLATE, HERO_IMAGE_MESSAGE, "square")
    );
  }

  @Test(groups = "mercury_header_heroImageIsRectangle")
  public void mercury_header_heroImageIsRectangle() {
    new TemplateContent().push(TEMPLATE_INFOBOX_AUTOMATION, TEMPLATE_INFOBOX_AUTOMATION_NAME);
    new ArticleContent().push(ARTICLE_INFOBOX4);

    Header header = new ArticlePage()
            .open()
            .getHeader();

    Assertion.assertFalse(
        header.isHeroImageSquare(),
        String.format(
            ELEMENT_EXPECTATION_MESSAGE_TEMPLATE, HERO_IMAGE_MESSAGE, "rectangular (not square)"
        )
    );
  }
}
