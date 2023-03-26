package com.pdf.selenium.tests;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.Set;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class PdfNewTab {
	
	 WebDriver driver;
			
		
		@BeforeTest
		public void setup() throws InterruptedException {
			ChromeOptions option = new ChromeOptions();
			option.addArguments("--remote-allow-origins=*");
			//option.setHeadless(true);
			//option.addArguments("--incognito");
			 driver = new ChromeDriver(option);
			 driver.get("https://www.hdfcbank.com/personal/resources/rates");
			 Thread.sleep(18000);
			 }
		
		@Test
		public void pdfInsameBrowserTest() throws InterruptedException, IOException
		{
			driver.findElement(By.xpath("//h3[contains(text(),'Fixed Deposit Interest Rate Greater Than Or Equal To 5 Cr')]")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("//p[contains(text(),'For rates greater than equal to Rs. 5 Cr please ')]/a")).click();
			Thread.sleep(3000);
			Set<String >handle = driver.getWindowHandles();
			 Iterator<String> it = handle.iterator();
			 String parentwindowID = it.next();
			 String childwindowID = it.next();
			 driver.switchTo().window(childwindowID);
			 
			//driver.findElement(By.id("ecSubmitAgreeButton")).click();
			//driver.switchTo().alert().accept();
			//Thread.sleep(3000);
			//driver.findElement(By.linkText("trillions of PDFs")).click();
			//Thread.sleep(3000);
			String newurl = driver.getCurrentUrl();
			Thread.sleep(3000);
			URL pdfUrl = new URL(newurl);
			URLConnection urlconn = pdfUrl.openConnection();
			urlconn.addRequestProperty("User-Agent", "Chrome");
			InputStream ip = urlconn.getInputStream();
			BufferedInputStream bf = new BufferedInputStream(ip);
			
			//get page count of pdf 
			PDDocument pddoc = PDDocument.load(bf);
			 int pageCount = pddoc.getNumberOfPages();
			 System.out.println(pageCount);
			 
			//Meta data of pdf
			 System.out.println("Meta data of pdf");		 
			 System.out.println(pddoc.getDocumentInformation().getAuthor());
			 System.out.println(pddoc.getDocumentInformation().getSubject());
			 System.out.println(pddoc.getDocumentInformation().getCreationDate());
			 
			 System.out.println(pddoc.isEncrypted());
			 System.out.println(pddoc.getDocumentId());
			 
			//read full pdf text:
				PDFTextStripper pdfStripper = new PDFTextStripper();
				String pdfFullText = pdfStripper.getText(pddoc);
				System.out.println(pdfFullText);
				Assert.assertTrue(pdfFullText.contains("DOMESTIC/NRE/NRO TERM DEPOSITS INTEREST RATES"));
			 
			 pddoc.close();
			 driver.close();
			 driver.switchTo().window(parentwindowID);
			 System.out.println("In parent window "+ driver.getTitle());
		
			
			
			
			
		}
		
		
		@AfterTest
		public void tearDown() {
			
			driver.quit();
		}
		

}
