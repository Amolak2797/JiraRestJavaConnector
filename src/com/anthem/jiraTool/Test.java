package com.anthem.jiraTool;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Test implements Runnable{

	String link;
	File out;
	Test(String link,File out){
		this.link=link;
		this.out=out;
	}

	@Override
	public void run() {

		try{
			System.out.println("Program started running");
			URL url=new URL(link);
			HttpURLConnection http=(HttpURLConnection) url.openConnection();
			
			System.out.println("Program running");
			double fileSize=http.getContentLengthLong();
			BufferedInputStream in=new BufferedInputStream(http.getInputStream());
			FileOutputStream fos=new FileOutputStream(out);
			BufferedOutputStream bout=new BufferedOutputStream(fos,1024);
			byte[] buffer=new byte[1024];
			double downloaded=0.0;
			int read=0;
			double percentDownloaded=0.0;
			while((read=in.read(buffer, 0, 1024))>=0){
				bout.write(buffer, 0,read);
				downloaded+=read;
				percentDownloaded=(downloaded*100)/fileSize;
				String percent=String.format("%.4f",percentDownloaded);
				System.out.println("Downloaded "+percent +"% of a file");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
