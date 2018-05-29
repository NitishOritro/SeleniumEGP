/*
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

/**
 *
 * @author Nitish
 */
public class paTender 
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
            
            //driver.findElement(By.id("txtEmailId"));
                
            WebElement email = driver.findElement(By.id("txtEmailId"));
            email.clear();			
                
            WebElement password = driver.findElement(By.name("password"));
            password.clear();
                
            email.sendKeys("pauserrotdormowhs@gmail.com");					
            password.sendKeys("egp12345");
                
                
                
            WebElement login = driver.findElement(By.id("btnLogin"));
            login.submit();
                
            //
            
            String menuPath = "//*[@id='headTabApp']";
            String dropDownMenuPath = "//ul/li/a[contains(text(),'My APP')]";
            
            dropDownMenuLink(driver, wait, menuPath, dropDownMenuPath, builder);    
            
            String jqueryGridPath = "//*[@id='list']";
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(jqueryGridPath)));
            
                
            String aPPID = "176";    
            String linkAppID = "";
            
            String dashboardLink = "";
            
            String beforeXpath = "//*[@id='list']/tbody/tr[";
            String afterXpath = "]/td/a";
                
            String beforeAppIDXpath = "//*[@id='list']/tbody/tr[";
            String AfterAppIDXpath = "]/td[2]";
            
            WebElement table = driver.findElement(By.id("list")); 
            List<WebElement> allRows = table.findElements(By.tagName("tr")); 
            
            for(int i=2;i<allRows.size();i++)
            {
                linkAppID = driver.findElement(By.xpath(beforeAppIDXpath+i+AfterAppIDXpath)).getText();
                
                if(linkAppID.equalsIgnoreCase(aPPID))
                {
                    String s = beforeAppIDXpath+i+AfterAppIDXpath;
                    System.out.println(linkAppID);
                    
                    dashboardLink = beforeAppIDXpath+i+"]/td/a[contains(@href,'APPDashboard.jsp')]";
                    
                    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(dashboardLink)));     
                    driver.findElement(By.xpath(dashboardLink)).click();
                    break;
                    
                }
                //System.out.println(linkAppID);
            }    
            
            submitButton(driver, "//a[contains(@href,'APPWorkflowView.jsp')]", wait);
            
            //textarea[@id='txtremark']
            
            driver.findElement(By.xpath("//textarea[@id='txtremark']")).sendKeys("TEST_Package No");
            
            submitButton(driver, "//*[@id=\"btnsubmit\"]", wait);
            
            submitButton(driver, "//*[@id=\"resultTable\"]/tbody/tr[2]/td[8]/a[2]", wait);
            
            String confirmPath = "//*[@id='popup_ok']";
            
            clickTenderPopUp(driver, confirmPath);
            
            String url = driver.getCurrentUrl();
            
            String tenderId = grabUrlTenderId(url);
            
            
            //submitButton(driver, "//*[@id='integrityPackcnk']", wait);
            
            //submitButton(driver, "//*[@id='integrityPackcnk']", wait);
            
            driver.findElement(By.id("integrityPackcnk")).click();
            driver.findElement(By.id("chkbidSecDeclaration_0")).click();
            driver.findElement(By.xpath("//*[@id='txtinvitationRefNo']")).sendKeys("TestSelenium"+tenderId);
            
            String dateID = "txtpreQualCloseDate";
            
            String ClosingOpeningDate = getDate(driver,dateID,"CloseOpen");
            
            JavascriptExecutor jse = (JavascriptExecutor) driver;
            jse.executeScript("$('#txtpreQualCloseDate').trigger('blur')");
            
            String PublicationDateAndTime = getDate(driver,"txttenderpublicationDate","publication");
            jse.executeScript("$('#txtpreQualCloseDate').trigger('blur')");
            
            String LastDateAndTimeBidSecuritySubmission  = getDate(driver,"txtlastDateTenderSub","bidsecurity");
            jse.executeScript("$('#txtlastDateTenderSub').trigger('blur')");
            
            String TenderDocumentsellingdownloadinGDateTime  = getDate(driver,"txttenderLastSellDate","download");
            jse.executeScript("$('#txttenderLastSellDate').trigger('blur')");
            
            
            //if submit button jdi click hoy then cehck korbo
                      
            
            
            String getText = "";
            
            
            //if(dirver.findElement(By.id(“Id of Element”)).isDisplayed())
            //int elementID = driver.findElements(By.xpath("//*[@id='spantxtpreQualCloseDate']/div[@class='reqF_1")).size();
            
            
            
            String editorFramePath = "//*[@id='cke_1_contents']/iframe[contains(@class, 'cke_wysiwyg_frame cke_reset')]";
            chkEditor(driver, wait, editorFramePath, "Eligibility of Bidder/Consultant");
            
            
            editorFramePath = "//*[@id='cke_2_contents']/iframe[contains(@class, 'cke_wysiwyg_frame cke_reset')]";
            chkEditor(driver, wait, editorFramePath, "Selenium Webdriver Test Tender: Brief Description of Goods and Related Service :");
            
            driver.findElement(By.xpath("//*[@id='locationlot_0']")).sendKeys("PA Office");
            driver.findElement(By.xpath("//*[@id='tenderSecurityAmount_0']")).sendKeys("1000");
            
            
            
            String ContractStartDate   = getDate(driver,"startTimeLotNo_0",1);
            jse.executeScript("$('#startTimeLotNo_0').trigger('blur')");
            
            String ContractEndDate    = getDate(driver,"complTimeLotNo_0",2);
            jse.executeScript("$('#complTimeLotNo_0').trigger('blur')");
            
            
            submitButton(driver, "//*[@id='btnsubmit']", wait);
            
            By by = By.xpath("//*[@id='spantxtpreQualCloseDate']/div[@class='reqF_1']");
            
            Boolean elementID = FindElement(driver, by, 3);
            
            
            if(elementID == true)
            {
                getText = driver.findElement(By.xpath("//*[@id='spantxtpreQualCloseDate']/div[@class='reqF_1']")).getText();
            }
            else
            {
                by = By.xpath("//*[@id='demoClose']");
                
                elementID = FindElement(driver, by, 3);
                if(elementID == true)
                {
                    getText = driver.findElement(By.xpath("//*[@id='demoClose']")).getText();
                }
            }
            
            if(getText.equalsIgnoreCase("Closing and Opening Date can not be weekend!") || getText.equalsIgnoreCase("Weekend!"))
            {
                ClosingOpeningDate = getDate(driver, dateID, 43,"CloseOpen",ClosingOpeningDate);
               
                jse.executeScript("$('#txtpreQualCloseDate').trigger('blur')");
                submitButton(driver, "//*[@id='btnsubmit']", wait);
            }
            else
            {
                submitButton(driver, "//*[@id='btnsubmit']", wait);
            }
            
            
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        
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
    
    public static String getDate(WebDriver driver, String dateID, int yearInc)
    {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date dt = new Date();
        Calendar cl = Calendar.getInstance();
        cl.setTime(dt);;
        cl.add(Calendar.YEAR, yearInc);
        dt=cl.getTime();

        String date = df.format(dt);

        System.out.println("the date today is " + date);
        
        WebElement element = driver.findElement(By.id(dateID));
        
        selectDateByJs(driver,element,date);
        
        return date;
    }
    
    public static String getDate(WebDriver driver, String dateID, String parameterID)
    {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date dt = new Date();
        Calendar cl = Calendar.getInstance();
        cl.setTime(dt);;
        int dateInc = 0;
        if(parameterID.equalsIgnoreCase("publication"))
        {
            dateInc = 1;
        }
        else if(parameterID.equalsIgnoreCase("CloseOpen"))
        {
            dateInc = 40;
        }
        else if(parameterID.equalsIgnoreCase("bidsecurity"))
        {
            dateInc = 31;
        }
        else if(parameterID.equalsIgnoreCase("download"))
        {
            dateInc = 14;
        }
        
        
        cl.add(Calendar.DAY_OF_YEAR, dateInc);
        dt=cl.getTime();

        String date = df.format(dt);

        System.out.println(parameterID+" date is " + date);
        
        WebElement element = driver.findElement(By.id(dateID));
        
        selectDateByJs(driver,element,date);
        
        return date;
    }
    
    
    
    
    
    public static String getDate(WebDriver driver, String dateID, int dateIncreament, String ParameterID, String ClosingOpeningDate)
    {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date dt = new Date();
        Calendar cl = Calendar.getInstance();
        cl.setTime(dt);;
        cl.add(Calendar.DAY_OF_YEAR, dateIncreament);
        dt=cl.getTime();

        String date = df.format(dt);

        System.out.println("the date today is " + date);
        
        WebElement element = driver.findElement(By.id(dateID));
        
        selectDateByJs(driver,element,date);
        
        return date;
    }
    
    
    
    public static String grabUrlTenderId(String url)
    {
        
        int firstIndex = url.indexOf('=');
       
        String tenderId = "";
        
        for(int i=firstIndex+1;i<url.length();i++)
        {
            tenderId = tenderId + url.charAt(i);
        }
        System.out.println(tenderId);
        return tenderId;
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