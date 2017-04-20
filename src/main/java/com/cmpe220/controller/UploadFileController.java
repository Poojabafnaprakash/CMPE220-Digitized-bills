package com.cmpe220.controller;


import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
public class UploadFileController {
	private static String UPLOADED_FOLDER = "/Users/poojaprakashchand/Documents/Eclipse/CMPE220-Digitized-bills/src/main/resources/receiptImage/";
	
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	@ResponseBody
    public void singleFileUpload(@RequestParam("file") MultipartFile file) {
		try {
            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);            
            
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		
	}
	
}
