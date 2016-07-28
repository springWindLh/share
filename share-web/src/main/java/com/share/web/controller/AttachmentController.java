package com.share.web.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.share.support.util.Files;
import com.share.web.entity.Attachment;
import com.share.web.entity.School;
import com.share.web.service.IAttachmentService;

@Controller
@Scope("prototype")
@RequestMapping("/attachment")
public class AttachmentController extends BaseController {
	@Autowired
	private IAttachmentService attachmentService;

	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public ModelAndView upload(MultipartFile file) throws FileNotFoundException {
		String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		if (!suffix.equalsIgnoreCase(".JPEG") && !suffix.equalsIgnoreCase(".JPG") && !suffix.equalsIgnoreCase(".GIF")
				&& !suffix.equalsIgnoreCase(".PNG")) {
			return modelAndView(false, "文件格式错误，请选择正确的图片格式文件！");
		}
		Attachment attachment = new Attachment();
		String path = getRequest().getSession().getServletContext().getRealPath("/sources/img/attachment");
		String attachmentName = Files.setFileName(file.getOriginalFilename());
		File newFile = new File(path, attachmentName);
		FileOutputStream outputStream = new FileOutputStream(newFile);
		try {
			Files.write(file.getBytes(), outputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				outputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		attachment.setCaption(file.getOriginalFilename());
		attachment.setCreateTime(currentTime);
		attachment.setPath(attachmentName);
		attachment.setSize((int) file.getSize() / 1024);
		attachmentService.save(attachment);
		return modelAndView(attachment);
	}

}
