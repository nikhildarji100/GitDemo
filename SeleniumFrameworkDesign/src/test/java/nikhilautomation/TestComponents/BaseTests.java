package nikhilautomation.TestComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


import nikhilautomation.pageobjects.LandingPage;

public class BaseTests {
	public WebDriver driver ;
	public LandingPage landingPage ;
	public WebDriver initializeDriver() throws IOException {
		
		Properties properties = new Properties();
		FileInputStream  fis = new  FileInputStream(System.getProperty("user.dir")+"//src//main//java//nikhilautomation//resources//GlobalData.properties");
		properties.load(fis);
		String browserName =  System.getProperty("browser")!=null ? System.getProperty("browser") : properties.getProperty("browser");
//		
		properties.getProperty("browser");
		
		if (browserName.contains("chrome")) {
			ChromeOptions options = new ChromeOptions();
			if(browserName.contains("headless"))
				options.addArguments("headless");
			//WebDriverManager.chromedriver().setup();
	        driver=new ChromeDriver(options);
			driver.manage().window().setSize(new Dimension (1440, 900));
		}
		else if(browserName.contains("edge")) {
			//System.setProperty("webdriver.edge.driver", "path");
			driver=new EdgeDriver();
		}
		
		else if(browserName.contains("firefox")) {
			System.out.println("**********************************Firefox driver is Started******************************************");
//			Firefox.
		}
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		
		return driver ;
	}
	
	@BeforeMethod(alwaysRun =  true)
	public LandingPage launchApplication() throws IOException {
		driver = initializeDriver();
		landingPage = new LandingPage(driver);
		landingPage.goTo();
		return landingPage ; 
	}
	
	@AfterMethod(alwaysRun =  true)
	public void tearDown() {
		driver.close();
	}
	
	public List<HashMap<String, String>> getJSonDataToMap(String filePath) throws IOException
	{
//		read json to string 
		String jsonContent = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);
//		String to Hash Map using Jackson Databind 
		 ObjectMapper mapper =   new ObjectMapper();
		 List<HashMap<String, String>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>(){});
		 return data ;
	}
	
	public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		File file = new File(System.getProperty("user.dir") + "//reports//"+testCaseName+".png");
		FileHandler.copy(source, file);
		return System.getProperty("user.dir") + "//reports//"+testCaseName+".png";
	}
	
	
	
	
}
