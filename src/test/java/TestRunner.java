import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.List;

public class TestRunner extends TestCase {

        private final String stewartry_url =  "https://agoldoffish.wordpress.com/criminal-minds-opening-and-closing-quotes";
        private final String hire_url = "https://www-5f042730fd2684721f73bc5c.recruit.eb7.io";

        @BeforeClass
        protected void setUp() {
                System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\src\\test\\resources\\webdrivers\\chromedriver.exe");

                WebDriver driver = new ChromeDriver();
                driver.get(stewartry_url);

                List<String> gideons = new ArrayList<String>();
                for (WebElement web_element : driver.findElements(By.xpath("//div[@class='entry-content']/p"))) {
                        String text = web_element.getText();
                        if (text.startsWith("Gideon"))
                        {
                                gideons.add(text);
                        }
                }

                driver.close();

                driver = new ChromeDriver();
                driver.get(hire_url);


                for (String gideon : gideons) {
                        driver.findElement(By.id("show-modal")).click();
                        driver.findElement(By.id("autorInput")).sendKeys("Gideon");
                        driver.findElement(By.id("quoteInput")).sendKeys(gideon);
                        driver.findElement(By.xpath("//button[@class='btn btn-success modal-default-button']")).click();
                };
                driver.close();


        }

        @Test
        public void testSimilar() {
                System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\src\\test\\resources\\webdrivers\\chromedriver.exe");
                WebDriver driver = new ChromeDriver();
                driver.get(hire_url);

                for (int j = 0 ; j <2 ; j ++) {
                        driver.findElement(By.id("show-modal")).click();
                        driver.findElement(By.id("autorInput")).sendKeys("myAuthor");
                        driver.findElement(By.id("quoteInput")).sendKeys("Here is the Quote");
                        driver.findElement(By.xpath("//button[@class='btn btn-success modal-default-button']")).click();
                }


                for (WebElement web_element : driver.findElements(By.xpath("//ul[@class='sidenav__tabs']/li"))) {
                        String text = web_element.getText();
                        if(text.equals("myAuthor")) {
                                web_element.click();
                        }
                        Assert.assertTrue("Expect to have only one quote but there are more", driver.findElements(By.xpath("//section[@class='rightsection']//li")).size() == 1);
                }
                driver.close();
        }
}