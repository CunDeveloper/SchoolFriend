package com.nju.service;

import java.io.File;
import java.io.IOException;

import org.apache.commons.fileupload.FileItem;

import com.nju.service.impl.FileUpload;

public class FileUploadContent implements FileUpload {

	private static FileUploadContent fileUpload = null;
	private FileUploadContent () {
	}
	public static FileUploadContent newInstance() {
		if(fileUpload == null) {
			fileUpload = new FileUploadContent();
		}
		return fileUpload;
	}
	
	@Override
	public void processUploadedFile(FileItem item,String path) {
		// TODO Auto-generated method stub
		if (item.getName() != null && !item.getName().equals("")) { 
            File tempFile = new File(item.getName());
            File dir = new File(path);
            if ( !dir.exists()) {
            	dir.mkdir();
            }
            File file = new File(dir,tempFile.getName());
            try {
				file.createNewFile();
	            item.write(file); 
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				 
			}   
        } else {
            
        }
	}

	@Override
	public void processFormField(FileItem item) {
		// TODO Auto-generated method stub
		if(item.isFormField()) {
			String name = item.getFieldName();
			String value = item.getString();
			System.out.println(name+"=="+value);
		}
	}

}
