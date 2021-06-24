package com.qian.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.qian.service.EasySendService;
import com.qian.utils.Constants;

@Controller
public class EasyFileController {

	@Autowired
	private EasySendService easySendService;
	
	@RequestMapping("/easyFile")
	@ResponseBody
	public String easyFile(@RequestParam(value = "file", required = false) List<MultipartFile> file) {
		try {
			easySendService.getData(file);
			return "上传成功";
		} catch (Exception e) {
			// TODO: handle exception
			return "上传失败";
		}
	}
	
	@RequestMapping("/getFileList")
	public String getFileList(HttpServletRequest req) {
		req.setAttribute("listResource", easySendService.getAllFiles());
		return "file";
	}
	
	@RequestMapping("/transWeights")
	public void transWeights(HttpServletResponse response, HttpServletRequest req) {
		String url = req.getParameter("url");
		easySendService.downloadFile(response, url);
	}
}
