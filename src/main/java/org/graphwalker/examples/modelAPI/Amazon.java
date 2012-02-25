package org.graphwalker.examples.modelAPI;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.Assert;

import org.graphwalker.exceptions.InvalidDataException;
import org.graphwalker.generators.PathGenerator;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Amazon extends org.graphwalker.multipleModels.ModelAPI {
  WebDriver driver = null;

  public Amazon(File model, boolean efsm, PathGenerator generator, boolean weight) {
    super(model, efsm, generator, weight);
  }

  /**
   * This method implements the Edge 'e_AddBookToCart'
   */
  public void e_AddBookToCart() {
    driver.findElement(By.id("bb_atc_button")).click();
  }

  /**
   * This method implements the Edge 'e_ClickBook'
   */
  public void e_ClickBook() {
    driver.findElement(By.linkText("Practical Model-Based Testing: A Tools Approach")).click();
  }

  /**
   * This method implements the Edge 'e_EnterBaseURL'
   */
  public void e_EnterBaseURL() {
    driver.get("http://www.amazon.com");
  }

  /**
   * This method implements the Edge 'e_SearchBook'
   */
  public void e_SearchBook() {
    WebElement element;
    element = driver.findElement(By.id("twotabsearchtextbox"));
    element.clear();
    element.sendKeys("Model-based testing");
    try {
      driver.findElement(By.xpath("//*[@id='navGoButton']/input")).click();
    } catch (NoSuchElementException e) {
      driver.findElement(By.xpath("//*[@class='nav-submit-button nav-sprite']/input")).click();
    }
  }

  /**
   * This method implements the Edge 'e_ShoppingCart'
   */
  public void e_ShoppingCart() {
    try {
      driver.findElement(By.cssSelector("#navCartEmpty > a.destination > span.text")).click();
    } catch (NoSuchElementException e) {
      try {
        driver.findElement(By.cssSelector("a.destination.count")).click();
      } catch (NoSuchElementException e1) {
        driver.findElement(By.xpath("//*[@id='nav-cart-count']")).click();
      }
    }
  }

  /**
   * This method implements the Edge 'e_StartBrowser'
   */
  public void e_StartBrowser() {
    driver = new FirefoxDriver();
    // driver = new ChromeDriver();
  }

  /**
   * This method implements the Vertex 'v_BaseURL'
   */
  public void v_BaseURL() {
    Assert.assertTrue(driver.getTitle().matches("^Amazon\\.com: .*"));
  }

  /**
   * This method implements the Vertex 'v_BookInformation'
   */
  public void v_BookInformation() {}

  /**
   * This method implements the Vertex 'v_BrowserStarted'
   */
  public void v_BrowserStarted() {
    Assert.assertNotNull(driver);
  }

  /**
   * This method implements the Vertex 'v_OtherBoughtBooks'
   * @throws InterruptedException 
   */
  public void v_OtherBoughtBooks() throws InterruptedException {
    Assert.assertTrue(verifyTextPresent("Customers Who Bought "));
  }

  /**
   * This method implements the Vertex 'v_SearchResult'
   */
  public void v_SearchResult() {
    Assert.assertTrue(driver.findElement(By.linkText("Practical Model-Based Testing: A Tools Approach")) != null);
  }

  /**
   * This method implements the Vertex 'v_ShoppingCart'
   * 
   * @throws InvalidDataException
   * @throws InterruptedException 
   */
  public void v_ShoppingCart() throws InvalidDataException, InterruptedException {
    Assert.assertTrue(driver.getTitle().matches("^Amazon\\.com Shopping Cart.*"));
    Integer expected_num_of_books = Integer.parseInt(getMbt().getDataValue("num_of_books"));
    Integer actual_num_of_books = null;

    if (expected_num_of_books == 0) {
      Assert.assertTrue(verifyTextPresent("Your Shopping Cart is empty"));
      return;
    }

    String itemsInCart = driver.findElement(By.id("gutterCartViewForm")).getText();
    Pattern pattern = Pattern.compile("Subtotal \\(([0-9]+) items*\\):", Pattern.MULTILINE);
    Matcher matcher = pattern.matcher(itemsInCart);
    if (matcher.find()) {
      actual_num_of_books = Integer.parseInt(matcher.group(1));
    }
    Assert.assertEquals(expected_num_of_books, actual_num_of_books);
  }

  /**
   * Got this from:
   * http://www.seleniumwiki.com/webdriver/using-verifytextpresent-in-selenium-2-webdriver/
   * 
   * @param text The text to verify
   * @return true if the test is present on the web page
   * @throws InterruptedException 
   */
  public boolean verifyTextPresent(String text) throws InterruptedException {
    long end = System.currentTimeMillis() + 5000;
    while (System.currentTimeMillis() < end) {
      if (driver.findElements(By.xpath("//*[contains(text(),\"" + text + "\")]")).size() > 0) {
        return true;
      }
      Thread.sleep(1000);
    }
    return false;
  }
}
