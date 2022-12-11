package session5;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest {
	WebDriver driver;
	By UN_FIELD = By.xpath("//*[@id=\"username\"]");
	By PW_FIELD = By.xpath("//*[@id=\"password\"]");
	By LOGINBTN_FIELD = By.xpath("/html/body/div/div/div/form/div[3]/button");
	By DB_TEXT = By.xpath("//*[@id=\"page-wrapper\"]/div[2]/div/h2");
	By Costumer = By.xpath("//*[@id=\"side-menu\"]/li[3]/a/span[1]");
	By ADD_costumer = By.xpath("//*[@id=\"side-menu\"]/li[3]/ul/li[1]/a");
	By DropDown = By.xpath("//*[@id=\"cid\"]");
	By FULLNAME = By.xpath("//*[@id=\"account\"]");
	By ADDCONTACT = By.xpath("//*[@id=\"page-wrapper\"]/div[3]/div[1]/div/div/div/div[1]/h5");
	By HEADER_FIELD = By.xpath("//*[@id=\"page-wrapper\"]/div[2]/div/h2"); 
	By EMAIL_FIELD = By.xpath("//*[@id=\"email\"]");
	By CUSTOMER_PW_FIELD = By.xpath("//*[@id=\"password\"]");
	// Test Data
	String un = "demo@techfios.com";
	String pw = "abc123";
	String DBTxt = "Dashboard";
	String browser="";
	String url;
	// ===================================================================================
	@BeforeClass
	public void readConfig () throws Exception {
		String path = "src\\main\\java\\config\\config.properties";
//		File f = new File (path);
//		FileReader fr= new FileReader(f);
//		BufferedReader br = new BufferedReader( fr );
		try {
			InputStream input = new FileInputStream(path);
			Properties prop = new Properties();
			prop.load(input);
			browser = prop.getProperty("Browser");
			System.out.println(browser);
			url=prop.getProperty("url");
		} catch (Exception e) {
			
		}
		
	}

	@BeforeMethod
	public void init() {
		
		if (browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", "driver\\chromedriver.exe");
			driver=new ChromeDriver();
		}
		else if (browser.equalsIgnoreCase("edge")) {
			System.setProperty("webdriver.edge.driver", "driver\\msedgedriver.exe");
			driver=new EdgeDriver();
		}
		
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.get(url);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@Test(priority = 1)
	public void loginTest() throws InterruptedException {
		driver.findElement(UN_FIELD).sendKeys(un);
		driver.findElement(PW_FIELD).sendKeys(pw);
		driver.findElement(LOGINBTN_FIELD).click();
		Assert.assertEquals(driver.findElement(HEADER_FIELD).getText(), DBTxt, "Wrong Page");
		

	}
	
	@Test(priority=2)
	public void addCustomer () throws Exception {
		Random rnd = new Random();
		int genNum=rnd.nextInt(9999);
		driver.findElement(UN_FIELD).sendKeys(un);
		driver.findElement(PW_FIELD).sendKeys(pw);
		driver.findElement(LOGINBTN_FIELD).click();
		Assert.assertEquals(driver.findElement(HEADER_FIELD).getText(), DBTxt, "Wrong Page");
		driver.findElement(Costumer).click();
		driver.findElement(ADD_costumer).click();
//		Thread.sleep(3000);
		driver.findElement(FULLNAME).sendKeys("TechFios07"+genNum);
		
		
		
		
		
		selectFromDropDown(driver.findElement(DropDown),"Techfios");
		selectFromDropDown(driver.findElement(By.xpath("//select[@name='country']")),"Algeria");
		selectFromDropDown(driver.findElement(By.xpath("//*[@id=\"tags\"]")),"Ann");
//		Select sel = new Select(driver.findElement(DropDown));
//		sel.selectByVisibleText("Techfios");
		
//		sel = new Select(driver.findElement(By.xpath("//select[@name='country']")));
//		sel.selectByVisibleText("Algeria");
		driver.findElement(EMAIL_FIELD).sendKeys("tech"+genNum+"@fios.com");
		//phone
		driver.findElement(By.xpath("//*[@id=\"phone\"]")).sendKeys("444555"+genNum);
		//Address
		driver.findElement(By.xpath("//*[@id=\"address\"]")).sendKeys(genNum+" address blvd");
		//City
		driver.findElement(By.xpath("//*[@id=\"city\"]")).sendKeys("mycity");
		//State
		driver.findElement(By.xpath("//*[@id=\"state\"]")).sendKeys("Texas");
		//Zip
		driver.findElement(By.xpath("//*[@id=\"zip\"]")).sendKeys("76111");
		//Tags
//		sel = new Select(driver.findElement(By.xpath("//*[@id=\"tags\"]")));
//		sel.selectByVisibleText("Ann");

		driver.findElement(CUSTOMER_PW_FIELD).sendKeys("abc12345678");
		driver.findElement(By.xpath("//*[@id=\"cpassword\"]")).sendKeys("abc12345678");
		driver.findElement(By.xpath("//*[@id=\"submit\"]")).click();
		
		
		Thread.sleep(3000);
		
		
	}

	private void selectFromDropDown(WebElement element, String visibleText) {
		Select sel = new Select(element);
		sel.selectByVisibleText(visibleText);
		
	}

	@Test(priority = 3)
	public void nedLoginTest() {
		driver.findElement(UN_FIELD).sendKeys(un);
		driver.findElement(PW_FIELD).sendKeys("abc1234");
		driver.findElement(LOGINBTN_FIELD).click();
		Assert.assertEquals(driver.findElement(HEADER_FIELD).getText(), DBTxt, "Wrong Page");
	}

	@AfterMethod
	public void teardown() {
		driver.close();

	}
}
