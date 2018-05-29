/*wTenderDashboardLivePA
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

/**
 *
 * @author Nitish Ranjan Bhowmik
 */
public class wTenderDashboardLivePA 
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
            
            jse.executeScript("window.scrollBy(0,1000)");
            
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
            
            
            
            submitButton(driver, "//a[contains(@href,'Notice.jsp')]", wait); //notice tab
            
            submitButton(driver, "//a[contains(text(),'Add Currency')]", wait); //add currency link
            
            selectDropdown(driver, wait, "//*[@id='ddlCurrency']", 3);  // American Dollar
            
            submitButton(driver, "//*[@id='btnAddCurrency']", wait); //add currency 
            
            submitButton(driver, "//a[contains(text(),'Go Back To Dashboard')]", wait);
            
            submitButton(driver, "//a[contains(text(),'Add Currency Rate')]", wait);
            
            driver.findElement(By.xpath("//*[@id='currencyRate_12']")).clear();
            driver.findElement(By.xpath("//*[@id='currencyRate_12']")).sendKeys("85.0000");
            //JavascriptExecutor jse = (JavascriptExecutor) driver;
            jse.executeScript("$('#currencyRate_12').trigger('blur')");
            
            submitButton(driver, "//*[@id='btnsubmit']", wait);
            
            driver.switchTo().alert().accept();
            
            
            submitButton(driver, "//a[contains(@href,'AddPckLotEstCost.jsp')]", wait);
            
            //Official Cost Estimate
            driver.findElement(By.xpath("//*[@id='taka_0']")).clear();
            driver.findElement(By.xpath("//*[@id='taka_0']")).sendKeys("10");
            jse.executeScript("$('#taka_0').trigger('blur')");
            
            submitButton(driver, "//*[@id='submit']", wait);
            
            
          
            //Tender Committee Details
            
            submitButton(driver, "//*[@id='tcTab']", wait);
            
            submitButton(driver, "//a[contains(@href,'TenderCommFormation.jsp')]", wait);
            
            
            driver.findElement(By.xpath("//*[@id='txtcommitteeName']")).sendKeys("TC");
            
            clickTenderPopUp(driver, "//*[@id='addmem']");
            
           
            Boolean tcMemberDone = false;
            String hopaName = "";
            String procurementRole="";
            List<WebElement>rows = driver.findElements(By.xpath("//*[@id='pe1']/table/tbody/tr"));
            //allRows = table.findElements(By.tagName("tr")); 
            
            String beforeXpath = "//*[@id='pe1']/table/tbody/tr[";
            String afterXpath = "]/td[4]";
            String roleNameXpath ="]/td[2]";
            
            String clickLink = "";
            //*[@id='pe1']/table/tbody/tr[2]/td[4]
            //*[@id='pe1']/table/tbody/tr
            int memeberCount = 0;
            int tcMemberCount = 0;
            for(int i=2;i<=rows.size();i++)
            {
                procurementRole = driver.findElement(By.xpath(beforeXpath+i+afterXpath)).getText();
                
                if(memeberCount < 5)
                {   
                    if(procurementRole.equalsIgnoreCase("HOPA") || procurementRole.equalsIgnoreCase("TC"))       
                    {
                        if(procurementRole.equalsIgnoreCase("HOPA"))
                        {
                            hopaName = driver.findElement(By.xpath(beforeXpath+i+roleNameXpath)).getText();
                        }
                        if(procurementRole.equalsIgnoreCase("TC"))
                        {
                            tcMemberCount++;
                        }
                            
                        String s = beforeXpath+i+"]/td/label/input";
                        System.out.println(procurementRole);

                        clickLink = beforeXpath+i+"]/td/label/input";
                        memeberCount++;

                        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(clickLink)));     
                        driver.findElement(By.xpath(clickLink)).click();

                    }
                }
                else
                {
                    break;
                }
                
            }
            if(memeberCount < 5 )
            {
                for(int i=2;i<=rows.size();i++)
                {
                    procurementRole = driver.findElement(By.xpath(beforeXpath+i+afterXpath)).getText();

                    if(memeberCount < 5)
                    {   
                        if(procurementRole.equalsIgnoreCase("TOC"))       
                        {
                            String s = beforeXpath+i+"]/td/label/input";
                            System.out.println(procurementRole);

                            clickLink = beforeXpath+i+"]/td/label/input";
                            memeberCount++;

                            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(clickLink)));     
                            driver.findElement(By.xpath(clickLink)).click();

                        }
                    }
                    else if(memeberCount == 5)
                    {
                        tcMemberDone = true;
                        break;
                    }
                
                }
            }
            
            if(tcMemberDone != true)
            {
                for(int i=2;i<=rows.size();i++)
                {
                    procurementRole = driver.findElement(By.xpath(beforeXpath+i+afterXpath)).getText();

                    if(memeberCount < 5)
                    {   
                        if(procurementRole.equalsIgnoreCase("TEC"))       
                        {
                            String s = beforeXpath+i+"]/td/label/input";
                            System.out.println(procurementRole);

                            clickLink = beforeXpath+i+"]/td/label/input";
                            memeberCount++;

                            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(clickLink)));     
                            driver.findElement(By.xpath(clickLink)).click();

                        }
                    }
                    else
                    {
                        break;
                    }
                
                }
            }
            
            
            
            submitButton(driver, "//button[1]", wait);
            
            //*[@id="members"]/tbody/tr[3]/td[1]
            
            rows = driver.findElements(By.xpath("//*[@id='members']/tbody/tr"));
            
            beforeXpath = "//*[@id=\"members\"]/tbody/tr[";
            afterXpath = "]/td[2]";
            roleNameXpath ="]/td[1]";
            for(int i=1;i<=rows.size();i++)
            {
                procurementRole = driver.findElement(By.xpath(beforeXpath+i+roleNameXpath)).getText();

                if(procurementRole.equalsIgnoreCase(hopaName))       
                {
                    //String s = beforeXpath+i+"]/td/label/input";
                    System.out.println(procurementRole);

                    clickLink = "//*[@id='cmbMemRole"+(i-1)+"']";
                    
                    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(clickLink))); 
                    selectDropdown(driver, wait, clickLink, 0);  
                    
                }
                else if(i == 2)
                {
                    System.out.println(procurementRole);

                    clickLink = "//*[@id='cmbMemRole"+(i-1)+"']";
                    
                    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(clickLink))); 
                    selectDropdown(driver, wait, clickLink, 1); 
                }
                else
                {
                    
                    System.out.println(procurementRole);

                    clickLink = "//*[@id='cmbMemRole"+(i-1)+"']";
                    
                    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(clickLink))); 
                    selectDropdown(driver, wait, clickLink, 2);  
                }
            }
            
            submitButton(driver, "//*[@id='btnSubmit']", wait);
            
            //Notify TC Memebers
            submitButton(driver, "//a[contains(text(),'Notify Committee Members')]", wait);
            driver.findElement(By.xpath("//*[@id='txtaRemarks']")).sendKeys("TC");
            submitButton(driver, "//*[@id='btnPublish']", wait);
            
            
            //Tender Committee Process
            submitButton(driver, "//a[contains(@href,'OpenComm.jsp')]", wait);
            submitButton(driver, "//a[contains(@href,'OpenCommFormation.jsp')]", wait);
            driver.findElement(By.xpath("//*[@id='txtcommitteeName']")).sendKeys("TOC");
            submitButton(driver, "//*[@id='addmem']", wait);
            
            
            
            Boolean tocMemberDone = false;
            
            rows = driver.findElements(By.xpath("//*[@id='pe1']/table/tbody/tr"));
            //allRows = table.findElements(By.tagName("tr")); 
            
            beforeXpath = "//*[@id='pe1']/table/tbody/tr[";
            afterXpath = "]/td[4]";
            roleNameXpath ="]/td[2]";
            
            clickLink = "";
            int tocMemberCount = 0;
            memeberCount = 0;
            for(int i=2;i<=rows.size();i++)
            {
                procurementRole = driver.findElement(By.xpath(beforeXpath+i+afterXpath)).getText();
                
                if(memeberCount < 2)
                {   
                    if(procurementRole.equalsIgnoreCase("HOPA") || procurementRole.equalsIgnoreCase("TOC"))       
                    {
                        if(procurementRole.equalsIgnoreCase("HOPA"))
                        {
                            hopaName = driver.findElement(By.xpath(beforeXpath+i+roleNameXpath)).getText();
                        }
                        if(procurementRole.equalsIgnoreCase("TOC"))
                        {
                            tocMemberCount++;
                        }
                            
                        String s = beforeXpath+i+"]/td/label/input";
                        System.out.println(procurementRole);

                        clickLink = beforeXpath+i+"]/td/label/input";
                        memeberCount++;

                        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(clickLink)));     
                        driver.findElement(By.xpath(clickLink)).click();

                    }
                }
                else
                {
                    break;
                }
                
            }
            
            submitButton(driver, "//button[1]", wait);
            
            
            
            rows = driver.findElements(By.xpath("//*[@id='members']/tbody/tr"));
            
            beforeXpath = "//*[@id='members']/tbody/tr[";
            afterXpath = "]/td[2]";
            roleNameXpath ="]/td[1]";
            for(int i=1;i<=rows.size();i++)
            {
                procurementRole = driver.findElement(By.xpath(beforeXpath+i+roleNameXpath)).getText();

                if(procurementRole.equalsIgnoreCase(hopaName))       
                {
                    //String s = beforeXpath+i+"]/td/label/input";
                    System.out.println(procurementRole);

                    clickLink = "//*[@id='cmbMemRole"+(i-1)+"']";
                    
                    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(clickLink))); 
                    selectDropdown(driver, wait, clickLink, 0);  
                    
                }
                else
                {
                    System.out.println(procurementRole);

                    clickLink = "//*[@id='cmbMemRole"+(i-1)+"']";
                    
                    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(clickLink))); 
                    selectDropdown(driver, wait, clickLink, 2); 
                }
            }
            
            submitButton(driver, "//*[@id='btnSubmit']", wait);
            
            //Process file in Workflow
            submitButton(driver, "//a[contains(@href,'CreateWorkflow.jsp')]", wait);
            submitButton(driver, "//a[contains(@href,'FileProcessing.jsp')]", wait);
            
            String editorFramePath = "//iframe[contains(@class, 'cke_wysiwyg_frame cke_reset')]";
            chkEditor(driver, wait, editorFramePath);
            
            String dropDownPath = "//*[@id=\"txtAction\"]";
            
            selectDropdown(driver, wait, dropDownPath, 1);
            submitButton(driver, "//*[@id='tbnAdd']", wait);
            
            //Evaluation Process
            
            
            submitButton(driver, "//a[contains(@href,'EvalComm.jsp')]", wait);
            submitButton(driver, "//a[contains(@href,'CommFormation.jsp')]", wait);
            driver.findElement(By.xpath("//*[@id='txtcommitteeName']")).sendKeys("TEC");
            submitButton(driver, "//*[@id='addmem']", wait);
            
            
            
            Boolean tecMemberDone = false;
            tocMemberCount = 0;
            rows = driver.findElements(By.xpath("//*[@id='pe1']/table/tbody/tr"));
            //allRows = table.findElements(By.tagName("tr")); 
            
            beforeXpath = "//*[@id='pe1']/table/tbody/tr[";
            afterXpath = "]/td[4]";
            roleNameXpath ="]/td[2]";
            String paName = "";
            
            clickLink = "";
            memeberCount = 0;
            
            for(int i=2;i<=rows.size();i++)
            {
                procurementRole = driver.findElement(By.xpath(beforeXpath+i+afterXpath)).getText();
                
                if(memeberCount < 3)
                {   
                    if(procurementRole.equalsIgnoreCase("PA") || procurementRole.equalsIgnoreCase("TEC"))       
                    {
                        if(procurementRole.equalsIgnoreCase("PA"))
                        {
                            paName = driver.findElement(By.xpath(beforeXpath+i+roleNameXpath)).getText();
                        }
                        if(procurementRole.equalsIgnoreCase("TEC"))
                        {
                            tocMemberCount++;
                        }
                            
                        String s = beforeXpath+i+"]/td/label/input";
                        System.out.println(procurementRole);

                        clickLink = beforeXpath+i+"]/td/label/input";
                        memeberCount++;

                        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(clickLink)));     
                        driver.findElement(By.xpath(clickLink)).click();

                    }
                }
                else
                {
                    break;
                }
                
            }
            
            
            submitButton(driver, "//button[1]", wait);
            
            
            
            rows = driver.findElements(By.xpath("//*[@id='members']/tbody/tr"));
            
            beforeXpath = "//*[@id='members']/tbody/tr[";
            afterXpath = "]/td[2]";
            roleNameXpath ="]/td[1]";
            for(int i=1;i<=rows.size();i++)
            {
                procurementRole = driver.findElement(By.xpath(beforeXpath+i+roleNameXpath)).getText();

                if(procurementRole.equalsIgnoreCase(paName))       
                {
                    //String s = beforeXpath+i+"]/td/label/input";
                    System.out.println(procurementRole);

                    clickLink = "//*[@id='cmbMemRole"+(i-1)+"']";
                    
                    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(clickLink))); 
                    selectDropdown(driver, wait, clickLink, 0);  
                    
                }
                else if (i == 2)
                {
                    System.out.println(procurementRole);

                    clickLink = "//*[@id='cmbMemRole"+(i-1)+"']";
                    
                    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(clickLink))); 
                    selectDropdown(driver, wait, clickLink, 1); 
                }
                else if(i == 3)
                {
                    System.out.println(procurementRole);

                    clickLink = "//*[@id='cmbMemRole"+(i-1)+"']";
                    
                    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(clickLink))); 
                    selectDropdown(driver, wait, clickLink, 2); 
                }
            }
            
            
            submitButton(driver, "//*[@id='btnSubmit']", wait);
            
            //Process file in Workflow
            //submitButton(driver, "//a[contains(@href,'CommFormation.jsp')]", wait);
            submitButton(driver, "//a[contains(@href,'CreateWorkflow.jsp')]", wait);
            submitButton(driver, "//a[contains(@href,'FileProcessing.jsp')]", wait);
            
            
            editorFramePath = "//iframe[contains(@class, 'cke_wysiwyg_frame cke_reset')]";
            chkEditor(driver, wait, editorFramePath);
            
            dropDownPath = "//*[@id=\"txtAction\"]";
            
            selectDropdown(driver, wait, dropDownPath, 1);
            submitButton(driver, "//*[@id='tbnAdd']", wait);
            
            //Whole Workflow
            
            submitButton(driver, "//a[contains(@href,'TenderDocPrep.jsp')]", wait);
            submitButton(driver, "//a[contains(@href,'CreateGrandSummary.jsp')]", wait);
            //*[@id="saveoredit"]
            submitButton(driver, "//*[@id='saveoredit']", wait);
            
            //a[contains(@href,'TenderDocPrep.jsp')]
            submitButton(driver, "//a[contains(@href,'TenderDocPrep.jsp')]", wait);
            
            submitButton(driver, "//a[contains(@href,'Notice.jsp')]", wait);
            
            submitButton(driver, "//a[contains(@href,'PEEncHash.jsp')]", wait);
            
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        
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