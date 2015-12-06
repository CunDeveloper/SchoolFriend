package com.nju.service.impl;

import org.apache.commons.fileupload.FileItem;

public interface FileUpload {

	void processUploadedFile(FileItem item,String path);
	void processFormField(FileItem item);
}
