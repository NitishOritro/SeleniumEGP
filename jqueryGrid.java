/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package selenium;

import java.io.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import static selenium.Selenium.selectDateByJs;

/**
 *
 * @author Nitish Ranjan Bhowmik
 */


public class jqueryGrid 
{
    
    public static void main(String[] args) 
    {
        
        System.setProperty("webdriver.gecko.driver", "E:\\Selenium\\Gecodriver\\geckodriver.exe");
        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        
        capabilities.setCapability("marionette", true);
        
        Random rand = new Random(); 
        int ii = rand.nextInt(100000); 
        
        WebDriver driver = new FirefoxDriver();
        WebElement submi = null;
        Actions builder = new Actions(driver);
        
        WebDriverWait wait = new WebDriverWait(driver, 10);
        
        try
        {
            driver.get("http://192.168.3.8:8080/");
            ((JavascriptExecutor) driver).executeScript("return window.stop");
            
            driver.manage().window().maximize();
                //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            
            String currentUrl = driver.getCurrentUrl();
               
            WebElement email = driver.findElement(By.id("txtEmailId"));
            email.clear();			
                
            WebElement password = driver.findElement(By.name("password"));
            password.clear();
                
            email.sendKeys("hoparotdormowhs@gmail.com");					
            password.sendKeys("egp12345");
               
            WebElement login = driver.findElement(By.id("btnLogin"));
            login.submit();
                
            String jqueryGridPath = "//*[@id='list']";
               
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(jqueryGridPath)));
            
            String aPPID = "174 (APP ID)";    
            String linkAppID = "";
            
            String fileProcessingLink = "";
            
            String beforeXpath = "//*[@id='list']/tbody/tr[";
            String afterXpath = "]/td/a";
                
            String beforeAppIDXpath = "//*[@id='list']/tbody/tr[";
            String AfterAppIDXpath = "]/td[4]";
            
            WebElement table = driver.findElement(By.id("list")); 
            List<WebElement> allRows = table.findElements(By.tagName("tr")); 
            
            for(int i=1;i<=allRows.size();i++)
            {
                linkAppID = driver.findElement(By.xpath(beforeAppIDXpath+i+AfterAppIDXpath)).getText();
                
                if(linkAppID.equalsIgnoreCase(aPPID))
                {
                    String s = beforeAppIDXpath+i+AfterAppIDXpath;
                    System.out.println(linkAppID);
                    
                    fileProcessingLink = beforeAppIDXpath+i+"]/td/a[contains(@href,'FileProcessing.jsp')]";
                    
                    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(fileProcessingLink)));     
                    driver.findElement(By.xpath(fileProcessingLink)).click();
                    break;
                    
                }
                //System.out.println(linkAppID);
            }    
            
            //*[@id='list']/tbody/tr[4]/td/a[contains(@href,'FileProcessing.jsp')]
            String editorFramePath = "//iframe[contains(@class, 'cke_wysiwyg_frame cke_reset')]";
            chkEditor(driver, wait, editorFramePath);
            
            String dropDownPath = "//*[@id=\"txtAction\"]";
            
            selectDropdown(driver, wait, dropDownPath, 2);
            
            
            String button = "//*[@id='tbnAdd']";
            
            submitButton(driver, button);
            
            
            
            
                
            //driver.quit();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        
    }
    
    
    public static void selectDateByJs(WebDriver driver,WebElement element, String dateVal)
    {
        JavascriptExecutor js =((JavascriptExecutor)driver);
        js.executeScript("arguments[0].setAttribute('value','"+dateVal+"');", element);
    }
    
    
    public static void chkEditor(WebDriver driver,WebDriverWait wait, String editorFramePath)
    {
        //String editorFramePath = "//iframe[contains(@class, 'cke_wysiwyg_frame cke_reset')]";
                
        wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(editorFramePath)));
                
        WebElement editorFrame = driver.findElement(By.xpath(editorFramePath));

        driver.switchTo().frame(editorFrame);

        WebElement body = driver.findElement(By.tagName("body"));

        body.clear(); 
        body.sendKeys("some text");
                
        driver.switchTo().defaultContent();
    }
    
    public static void selectDropdown(WebDriver driver, WebDriverWait wait,String dropDownPath, int selectIndex)
    {
        
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(dropDownPath)));
        Select dropDownValueSelect = new Select(driver.findElement(By.xpath(dropDownPath)));
        dropDownValueSelect.selectByIndex(selectIndex);
        
    }
    
    
    public static void submitButton(WebDriver driver, String editorFramePath)
    {
        driver.findElement(By.xpath("//*[@id='tbnAdd']")).click();
        
    }
    
    
}