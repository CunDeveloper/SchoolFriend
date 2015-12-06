package com.uti.test;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.nju.model.Comment;
import com.nju.model.Content;
import com.nju.model.Praise;
import com.nju.model.ViewAUserContent;
import com.nju.service.UploadContentService;

public class UploadContentServiceTest {

	static Logger logger = Logger.getLogger(UploadContentServiceTest.class);
//	@Test
//	public void test() {
//		List<Content> contents = new UploadContentService().query(22);
//		for(Content content:contents){
//			 System.out.println(content.getId()+"=="+content.getContent()+"=="+content.getIs_contain_image()+"=="+content.getPraiceUserName()+"=="+content.getUser_id());
//			 for(String str:content.getImageList()){
//				 System.out.println(str);
//			 }
//			 for(Comment comment:content.getCommentList()){
//				 System.out.println(comment.getComment_content());
//			 }
//		}
//	}
	
//	@Test
//	public void testQueryOwnContent(){
//		List<Content> contents = new UploadContentService().queryOwnContent(12);
//		for(Content content:contents){
//			 System.out.println(content.getId()+"=="+content.getContent()+"=="+content.getIs_contain_image()+"=="+content.getUser_id());
//			 for(String str:content.getImageList()){
//				 System.out.println(str);
//			 }
//			 for(Praise praise:content.getPraiseList()){
//				 System.out.println(praise.getPriceUserName());
//			 }
//			 for(Comment comment:content.getCommentList()){
//				 System.out.println(comment.getComment_content());
//			 }
//		}
//	}
	
	@Test
	public void testQueryAnotherUserContent(){
		List<ViewAUserContent> contents = new UploadContentService().queryAnotherUserContent(120,121);
		for(ViewAUserContent content:contents){
			 System.out.println(content.getId()+"=="+content.getContent()+"=="+content.getIs_contain_image()+"=="+content.getUser_id());
			 for(String str:content.getImageList()){
				 System.out.println(str);
			 }
			 if(content.getVisitUserPraise()!=null){
				 System.out.println(content.getVisitUserPraise().getPriceUserName());
			 }
			 for(Comment comment:content.getCommentList()){
				 System.out.println(comment.getComment_content());
			 }
		}
	}

}
