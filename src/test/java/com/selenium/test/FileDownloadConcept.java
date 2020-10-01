package com.selenium.test;

/*
 * 
 * @Author:- Shoaib Hannure
 * 
 */

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class FileDownloadConcept {

	WebDriver driver;
	File folder;

	@BeforeMethod
	public void setUp() {
		// 8889-8754-6325-7878 --Random UUID

		folder = new File(UUID.randomUUID().toString());
		folder.mkdir();
		
		// Chrome:-
		WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
		// Hash Map Object having key as String and value as any Object
		Map<String, Object> prefs = new HashMap<String, Object>();
		// with prefs store key & Value
		// profile.default_content_settings,popups key provided by chrome
		// Default content settings for Pop up is 0. Do not display any pop up
		// for Download, since Selenium cannot handle window pop up

		prefs.put("profile.default_content_settings.popups", 0);
		prefs.put("download.default_directory", folder.getAbsolutePath());

		options.setExperimentalOption("prefs", prefs);
		DesiredCapabilities cap = DesiredCapabilities.chrome();
		cap.setCapability(ChromeOptions.CAPABILITY, options);

		driver = new ChromeDriver(cap);
	}
	
	@Test
	public void downloadFileTest() throws InterruptedException{
		driver.get("http://the-internet.herokuapp.com/download");
		driver.findElement(By.linkText("some-file.txt")).click();
		 
		Thread.sleep(3000);
		File listOfFiles[]=folder.listFiles();
		//Make sure directory is not empty
		//Assert.assertEquals(listOfFiles.length, is(not(0)));
	Assert.assertTrue(listOfFiles.length>0);
	
	for(File file:listOfFiles){
		Assert.assertTrue(file.length()>0);
	}
	}

	@AfterMethod
	public void teardown() {
		driver.quit();
		
		//Delete a File & Folder after the download
		for (File file : folder.listFiles()) {
			//file.delete();
		}
		//folder.delete();
	}

}
