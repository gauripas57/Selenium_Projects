package com.pdf.selenium.tests;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class PdfInBrowser {

	 WebDriver driver;
	// String url = "https://www.inkit.com/blog/pdf-the-best-digital-document-management";
		
	
	@BeforeTest
	public void setup() {
		ChromeOptions option = new ChromeOptions();
		option.addArguments("--remote-allow-origins=*");
		//option.setHeadless(true);
		//option.addArguments("--incognito");
		 driver = new ChromeDriver(option);
		 driver.get("https://www.inkit.com/blog/pdf-the-best-digital-document-management");
	}
	
	@Test
	public void pdfInsameBrowserTest() throws InterruptedException, IOException
	{
		Thread.sleep(3000);
		driver.findElement(By.id("ecSubmitAgreeButton")).click();
		//driver.switchTo().alert().accept();
		Thread.sleep(3000);
		driver.findElement(By.linkText("trillions of PDFs")).click();
		Thread.sleep(3000);
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
		 System.out.println(pddoc.getVersion());
		 System.out.println(pddoc.getCurrentAccessPermission().canPrint());
		 System.out.println(pddoc.getCurrentAccessPermission().isOwnerPermission());
		 System.out.println(pddoc.getCurrentAccessPermission().isReadOnly());
		 
		 System.out.println(pddoc.getDocumentInformation().getAuthor());
		 System.out.println(pddoc.getDocumentInformation().getSubject());
		 System.out.println(pddoc.getDocumentInformation().getCreationDate());
		 
		 System.out.println(pddoc.isEncrypted());
		 System.out.println(pddoc.getDocumentId());
		 
		 pddoc.close();
	
		
		
		
		
	}
	
	
	@AfterTest
	public void tearDown() {
		
		driver.quit();
	}
	
	
	
}
