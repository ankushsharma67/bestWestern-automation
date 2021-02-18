package com.dbs.ecosystems.marketplace.pages.travel;

import java.util.*;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.dbs.ecosystems.utilities.ExcelUtility;


public class HotelSearch {
	public WebDriverWait wait = new WebDriverWait(driver, 120);
	String url="https://www.bestwestern.com";
	String title="Book Direct at Best Western Hotels & Resorts";
	String destination="";
	ExcelUtility excel=new ExcelUtility();
	
	public HotelSearch(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(how = How.XPATH, using = "//label[text()='DESTINATION']")
	public WebElement label_destination;
	
	@FindBy(how = How.XPATH, using = "//input[@placeholder='City, Address, Attraction, Airport']")
	public WebElement input_destination;
	@FindBy(how = How.XPATH, using = "(//input[@aria-describedby='date-picker-instructions'])[1]")
	public WebElement input_checkInDate;
	@FindBy(how = How.XPATH, using = "//div[@aria-label='Check-in date-picker calendar']")
	public WebElement input_checkInDateTable;
	@FindBy(how = How.XPATH, using = "(//input[@aria-describedby='date-picker-instructions'])[2]")
	public WebElement input_checkOutDate;
	@FindBy(how = How.XPATH, using = "//div[@aria-label='Check-out date-picker calendar']")
	public WebElement input_checkOutDateTable;
	public String a_selectDate = "//a[@aria-label='${date}']";
	@FindBy(how = How.XPATH, using = "//button[text()='Find My Hotel']")
	public WebElement button_findMyHotel;
	@FindBy(how = How.XPATH, using = "//ul[@id='google-suggestions']/li[1]")
	public WebElement list_hotelsugeestions;
	@FindBy(how = How.XPATH, using = "//div[@class='detail destination']//p[@class='value']")
	public WebElement div_destinationDetail;
	@FindBy(how = How.XPATH, using = "//div[@id='no-results']")
	public WebElement div_noResults;
	@FindBy(how = How.XPATH, using = "//button[text()='Change Search']")
	public WebElement button_changeSearch;
	@FindBy(how = How.XPATH, using = "//button[@id='btn-modify-stay-update']")
	public WebElement button_update;
	
	
	
	public void navigateToURLAndVerify() {
		try {
			// navigate to the url
			driver.manage().window().maximize();
			driver.get(url);
			
			// Clear the cookies
			driver.manage().deleteAllCookies();
			
			wait.until(ExpectedConditions.visibilityOf(label_destination));
			// Validate home page title
			if (driver.getTitle().equals(title) && label_destination.isDisplayed()) {
				System.out.println("URL Launched");
			} else {
				Assert.fail("URL did not launch properly");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void inputDestination(int row) {
		HashMap<String, String> actualMap = new HashMap<String, String>();
		actualMap=excel.readExcelData(System.getProperty("user.dir") + "", "searchData", row);
		destination=actualMap.get("destination");
		input_destination.sendKeys(actualMap.get("destination"));
		list_hotelsugeestions.click();
	}
	
	public void inputDateCheckIn(int row) {
		input_checkInDate.click();
		wait.until(ExpectedConditions.visibilityOf(input_checkInDateTable));
		HashMap<String, String> actualMap = new HashMap<String, String>();
		actualMap=excel.readExcelData(System.getProperty("user.dir") + "", "searchData", row);
		WebElement webElement=driver.findElement(By.xpath(a_selectDate.replace("${date}", actualMap.get("checkIn"))));
		webElement.click();
	}
	
	public void inputDateCheckOut(int row) {
		input_checkOutDate.click();
		wait.until(ExpectedConditions.visibilityOf(input_checkOutDateTable));
		HashMap<String, String> actualMap = new HashMap<String, String>();
		actualMap=excel.readExcelData(System.getProperty("user.dir") + "", "searchData", row);
		WebElement webElement=driver.findElement(By.xpath(a_selectDate.replace("${date}", actualMap.get("checkOut"))));
		webElement.click();
	}
	
	public void verifyAndClickFindMyHotelButton() {
		if (!(button_findMyHotel.getText().equalsIgnoreCase("Find My Hotel"))) {
			Assert.fail("Button text not as Expected");
		}
		
		button_findMyHotel.click();
	}
	
	public void verifyHotelDestinationInSearchPage() {
		if(!(div_destinationDetail.getText().contains(destination))){
			Assert.fail("Destination not matching as entered");
		}
	}
	
	public void verifyHotelCardsAppearing(){
		if(div_noResults.isDisplayed()){
			System.out.println("No Results displayed");
		}else{
			System.out.println("Results displayed");
		}
	}
	
	public void clickChangeSearch(){
		button_changeSearch.click();
	}
	
	public void enterDetinationAndDatesForChangeAndUpdate(){
		inputDestination(2);
		inputDateCheckIn(2);
		inputDateCheckOut(2);
		button_update.click();
		verifyHotelDestinationInSearchPage();
	}
}
