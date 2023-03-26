package com.pdf.selenium.tests;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class PDFreader {
	
	 WebDriver driver;
	 String url = "https://www.adobe.com/support/products/enterprise/knowledgecenter/media/c4611_sample_explain.pdf";
		
	
	@BeforeTest
	public void setup() {
		ChromeOptions option = new ChromeOptions();
		option.addArguments("--remote-allow-origins=*");
		//option.setHeadless(true);
		option.addArguments("--incognito");
		 driver = new ChromeDriver(option);
		 driver.get(url);
	}
	

	@Test
	public void pdfReaderPageCountTest() throws IOException {
		URL pdfurl = new URL(url);
		InputStream ip = pdfurl.openStream();
		BufferedInputStream bf = new BufferedInputStream(ip);
		PDDocument pdfdoc = PDDocument.load(bf);
		
		// page count operation
		int pageCount = pdfdoc.getNumberOfPages();
		System.out.println("pdf page count "+ pageCount);
		Assert.assertEquals(pageCount, 4);
		System.out.println("-------Show pdf content -----------");
		
		// page content/text operation
		PDFTextStripper stp = new PDFTextStripper();
		String pdfText = stp.getText(pdfdoc);
		System.out.println(pdfText);
		Assert.assertTrue(pdfText.contains("PDF BOOKMARK SAMPLE"));
		Assert.assertTrue(pdfText.contains("This sample consists of a simple form containing four distinct fields."));
		Assert.assertTrue(pdfText.contains("ap_bookmark.IFD"));
		Assert.assertTrue(pdfText.contains("Place ap_bookmark.mdf"));
		
		System.out.println("-------Show 3rd and 4th page pdf content -----------");
		//go to specific page & get the content/text operation
		stp.setStartPage(3);
		String pdfspecText = stp.getText(pdfdoc);
		System.out.println(pdfspecText);
	}
	
	@AfterTest
	public void tearDown() {
		
		driver.quit();
	}
	

}
