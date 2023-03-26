package com.pdf.selenium.tests;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.testng.annotations.Test;

public class PDFImagesTest {
	
	@Test
	public void pdfImageTest() throws Exception {

		
		PDFReaderHelper reader = new PDFReaderHelper();

		
		File fileA = new File("./newhdfc.png");
		File fileB = new File("./actual_image_hdfc_logo-modified.png");

		
		reader.imageCompare(fileA, fileB);
		
	}

}
