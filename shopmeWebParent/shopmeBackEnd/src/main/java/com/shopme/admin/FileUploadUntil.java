package com.shopme.admin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


import org.springframework.web.multipart.MultipartFile;

import ch.qos.logback.classic.Logger;

public class FileUploadUntil 
{
		
	public static void saveFile(String uploadDir , String Filename , MultipartFile multipartFile) throws IOException
	{
		
		Path uploadpath  =  Paths.get(uploadDir);
		
		if(!Files.exists(uploadpath))
		{
			Files.createDirectories(uploadpath);
		}
		
		try(InputStream inputStream =  multipartFile.getInputStream()) 
		{
			
			Path filepath = uploadpath.resolve(Filename);
			Files.copy(inputStream, filepath , StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new IOException("could not save file : "+Filename+e);
		}
				
		
	}
	
	public static void CleanDirectory(String directory)
	{
		Path Dir  =  Paths.get(directory);
		
		try 
		{
				Files.list(Dir).forEach(file -> {
					
					if(!Files.isDirectory(file)) {
						try {
						Files.delete(file);
						}
						catch(IOException e) {
							System.out.println("could not delete file "+file);
						}
					}
				});
				
		} catch (IOException ex) 
		{
			System.out.println("Could not list directory : "+directory);
		}
	}

	public static void removeDir(String categoryDir) {
		
		CleanDirectory(categoryDir);
		
		try {
			Files.delete(Paths.get(categoryDir));
		} catch (IOException ex) {
			
			System.out.println(ex.getMessage());
			

		}
		
	}
	
	
}
