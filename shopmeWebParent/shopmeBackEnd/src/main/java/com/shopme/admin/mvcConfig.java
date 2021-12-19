package com.shopme.admin;

import java.nio.file.Path;
import java.nio.file.Paths;

import javax.sound.midi.Soundbank;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class mvcConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) 
	{
		
			String dirName  = "Upload_Dir";
			Path userPhotoDir  =  Paths.get(dirName);
			
			String userPhotopaths =  userPhotoDir.toFile().getAbsolutePath();
			
			registry.addResourceHandler("/"+dirName+"/**").addResourceLocations("file:/"+userPhotopaths+"/");
			
			
			
			String categoryImageDirName  = "../category_image";
			Path categoryImageDir =  Paths.get(categoryImageDirName);
			String categoryImagePaths = categoryImageDir.toFile().getAbsolutePath();
			registry.addResourceHandler("/category_image/**").addResourceLocations("file:/"+categoryImagePaths+"/");
			
			
			
			String brandImageDirName  = "../brand-logos";
			Path brandImageDir =  Paths.get(brandImageDirName);
			String brandImagePaths = brandImageDir.toFile().getAbsolutePath();			
			registry.addResourceHandler("/brand-logos/**").addResourceLocations("file:/"+brandImagePaths+"/");
			
			
		
	}

}
