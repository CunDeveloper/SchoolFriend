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
		if (item.getName() != null && !item.getName().equals("")) {// 判断是否选择了文件
            System.out.println("上传文件的大小:" + item.getSize());
            System.out.println("上传文件的类型:" + item.getContentType());
            // item.getName()返回上传文件在客户端的完整路径名称
            System.out.println("上传文件的名称:" + item.getName());
            // 此时文件暂存在服务器的内存当中
            File tempFile = new File(item.getName());// 构造临时对象
            File dir = new File(path);
            if ( !dir.exists()) {
            	dir.mkdir();
            }
            File file = new File(dir,tempFile.getName());
            try {
				file.createNewFile();
				 // 获取根目录对应的真实物理路径
	            item.write(file);// 保存文件在服务器的物理磁盘中
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
