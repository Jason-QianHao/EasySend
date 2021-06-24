package com.qian.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qian.service.EasySendService;
import com.qian.utils.Constants;

@Controller
public class EasyCopyController {
	
	@Autowired
	private EasySendService easySendService;
	
	@RequestMapping(value = "/easyCopy")
	@ResponseBody
	public String easyCopy(HttpServletRequest req) {
		// 获取用户移动端msg
		String msg = req.getParameter(Constants.MSG); // 这里后面需要通过用户唯一ID来获取msg
		// 将移动端msg插入Redis
		easySendService.addMsg(msg);
		return Constants.COPY_SUCCESS;
	}
	
	@RequestMapping("/getCopyList")
	public String getCopyList(HttpServletRequest req) {
		req.setAttribute("listResource", easySendService.getAllMsg());
		return "copy";
	}
	
}
