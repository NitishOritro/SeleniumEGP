/*zTenderLive
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package selenium;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import static selenium.Selenium.selectDateByJs;
import static selenium.jqueryGrid.chkEditor;
import static selenium.jqueryGrid.selectDropdown;
import static selenium.jqueryGrid.submitButton;
import static selenium.paTender.clickTenderPopUp;
import static selenium.tenderDashboardPA.submitButton;
import static selenium.yTenderCommittee.FindElement;
import static selenium.yTenderCommittee.chkEditor;
import static selenium.yTenderCommittee.selectDropdown;
import static selenium.yTenderCommittee.submitButton;

/**
 *
 * @author Nitish Ranjan Bhowmik
 */
public class zTenderLive 
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
        
        Boolean rowCount = false;
        By by;
        
        
        try
        {
            driver.get("http://192.168.3.8:8080/");
            ((JavascriptExecutor) driver).executeScript("return window.stop");
            
            driver.manage().window().maximize();
            
            String currentUrl = driver.getCurrentUrl();
            
            WebElement email = driver.findElement(By.id("txtEmailId"));
            email.clear();			
                
            WebElement password = driver.findElement(By.name("password"));
            password.clear();
                
            email.sendKeys("pauserrotdormowhs@gmail.com");					
            password.sendKeys("egp12345");
                
                
            WebElement login = driver.findElement(By.id("btnLogin"));
            login.submit();
            
            JavascriptExecutor jse = (JavascriptExecutor) driver;
            
            String menuPath = "//*[@id='headTabTender']";
            String dropDownMenuPath = "//a[contains(text(),'My Tender')]";
            
            dropDownMenuLink(driver, wait, menuPath, dropDownMenuPath, builder);    
            
            
            submitButton(driver, "//a[contains(text(),'Under Preparation')]", wait);
           
            String tenderID = "1148,\nTestSelenium1148";    
            String linktenderID = "";
            
            String dashboardLink = "";
            
            //*[@id='resultTable']/tbody/tr[2]/td[7]/a[contains(@href,'TenderDashboard.jsp')]
            
            //*[@id='resultTable']/tbody/tr[4]
            
            //jse.executeScript("window.scrollBy(0,1000)");
            
            WebElement table = driver.findElement(By.id("resultTable")); 
            List<WebElement> allRows = table.findElements(By.tagName("tr")); 
            
            String beforeAppIDXpath = "//*[@id='resultTable']/tbody/tr[";
            String AfterAppIDXpath = "]/td[2]";
            
            for(int i=1;i<allRows.size();i++)
            {
                linktenderID = driver.findElement(By.xpath(beforeAppIDXpath+i+AfterAppIDXpath)).getText();
                
                if(linktenderID.equalsIgnoreCase(tenderID))
                {
                    String s = beforeAppIDXpath+i+AfterAppIDXpath;
                    System.out.println(linktenderID);
                    
                    dashboardLink = beforeAppIDXpath+i+"]/td[7]/a[contains(@href,'TenderDashboard.jsp')]";
                    
                    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(dashboardLink)));     
                    driver.findElement(By.xpath(dashboardLink)).click();
                    break;
                    
                }
                //System.out.println(linkAppID);
            }
            
            
            
            submitButton(driver, "//a[contains(@href,'OpenComm.jsp')]", wait);
            submitButton(driver, "//a[contains(text(),'Notify Committee Members')]", wait); //notice tab
            driver.findElement(By.xpath("//*[@id='txtaRemarks']")).sendKeys("Notify");
            submitButton(driver, "//*[@id='btnPublish']", wait);
            submitButton(driver, "//a[contains(@href,'EvalComm.jsp')]", wait);
            submitButton(driver, "//a[contains(text(),'Notify Committee Members')]", wait);
            driver.findElement(By.xpath("//*[@id='txtaRemarks']")).sendKeys("Notify");
            submitButton(driver, "//*[@id='btnPublish']", wait);
            submitButton(driver, "//a[contains(@href,'Notice.jsp')]", wait);
            submitButton(driver, "//a[contains(@href,'PEEncHash.jsp')]", wait);
            
            allRows = driver.findElements(By.xpath("//*[@id='frmEnc']/table/tbody/tr"));
            
            String beforeXpath = "//*[@id='frmEnc']/table/tbody/tr[";
            String afterXpath = "]/td/input";
            
            
            for(int i=1;i<=allRows.size();i++)
            {
                dashboardLink = beforeXpath+i+afterXpath;
                by = By.xpath(beforeXpath+i+afterXpath);
                rowCount = FindElement(driver, by, 1);
                if(rowCount == true)
                {
                    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(dashboardLink)));     
                    driver.findElement(By.xpath(dashboardLink)).click();
                }
            }
            submitButton(driver, "//*[@id='hdnsubmit']", wait);
            submitButton(driver, "//a[contains(@href,'CreateWorkflow.jsp')]", wait);
            submitButton(driver, "//a[contains(@href,'FileProcessing.jsp')]", wait);
            
            String editorFramePath = "//iframe[contains(@class, 'cke_wysiwyg_frame cke_reset')]";
            chkEditor(driver, wait, editorFramePath);

            String dropDownPath = "//*[@id='txtAction']";

            selectDropdown(driver, wait, dropDownPath, 1);
            String button = "//*[@id='tbnAdd']";
            submitButton(driver, button);
            
            
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        
    }
    
    public static void submitButton(WebDriver driver, String editorFramePath)
    {
        driver.findElement(By.xpath("//*[@id='tbnAdd']")).click();
        
    }
    
    
    public static void printUrl(WebDriver driver)
    {
        String currentUrl = driver.getCurrentUrl();
    
        System.out.println(currentUrl);
    }
    
    public static Boolean FindElement(WebDriver driver, By by, int timeoutInSeconds)
    {
        try
        {
            WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
            wait.until( ExpectedConditions.presenceOfElementLocated(by) ); //throws a timeout exception if element not present after waiting <timeoutInSeconds> seconds
            
            return true;
        }
        catch(Exception e)
        {
            System.out.println(e);
            return false;
        }
    }
    
    public static void clickTenderPopUp(WebDriver driver, String confirmPath)
    {
        try 
        {
            Thread.sleep(1000);
            //element = driver.findElement(By.xpath("//*[@id='popup_container']"));            
        
            WebElement element = driver.findElement(By.xpath(confirmPath));
            
            element.click();
        } 
        catch (InterruptedException ex) 
        {
            Logger.getLogger(paTender.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        
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
    
    
    
    public static void selectDateByJs(WebDriver driver,WebElement element, String dateVal)
    {
        JavascriptExecutor js =((JavascriptExecutor)driver);
        js.executeScript("arguments[0].setAttribute('value','"+dateVal+"');", element);
    }
    
    
    public static void chkEditor(WebDriver driver,WebDriverWait wait, String editorFramePath, String content)
    {
        //String editorFramePath = "//iframe[contains(@class, 'cke_wysiwyg_frame cke_reset')]";
                
        wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(editorFramePath)));
                
        WebElement editorFrame = driver.findElement(By.xpath(editorFramePath));

        driver.switchTo().frame(editorFrame);

        WebElement body = driver.findElement(By.tagName("body"));

        body.clear(); 
        body.sendKeys(content);
                
        driver.switchTo().defaultContent();
    }
    
    public static void selectDropdown(WebDriver driver, WebDriverWait wait,String dropDownPath, int selectIndex)
    {
        
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(dropDownPath)));
        Select dropDownValueSelect = new Select(driver.findElement(By.xpath(dropDownPath)));
        dropDownValueSelect.selectByIndex(selectIndex);
        
    }
    
    
    public static void submitButton(WebDriver driver, String linkPath, WebDriverWait wait)
    {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(linkPath)));
        driver.findElement(By.xpath(linkPath)).click();
        
    }
    
    public static void dropDownMenuLink(WebDriver driver, WebDriverWait wait,String menuPath, String dropDownMenuPath, Actions builder)
    {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(menuPath)));  // locating the main menu
        WebElement menu = driver.findElement(By.xpath(menuPath));
        builder.moveToElement(menu).build().perform();
                
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(dropDownMenuPath))); 
        WebElement menuOption = driver.findElement(By.xpath(dropDownMenuPath));
        builder.moveToElement(menuOption).click().build().perform();
        
    }
    
    
    
}