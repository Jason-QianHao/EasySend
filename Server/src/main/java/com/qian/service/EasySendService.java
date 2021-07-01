package com.qian.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.qian.entity.FileEntity;
import com.qian.mapper.FileMapping;
import com.qian.utils.Constants;
import com.qian.utils.TimeUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EasySendService {

	@Value("${fileBasePath}")
	private String fileBasePath;
	@Autowired
	private RedisService redisService;
	@Autowired
	private FileMapping fileMapping;
	private volatile int updateId = 0;
	private boolean isLarge = false;
	
	/*
	 * 参数：redis的key 功能：新添一个msg
	 */
	public void addMsg(String msg) {
		List<Object> tmp = (List<Object>) redisService.getList(Constants.MSG);
		redisService.removeStringKey(Constants.MSG);
		LinkedList<Object> list = new LinkedList(tmp);
		while (list.size() > 10) {
			redisService.removeKey(Constants.MSG, (String) list.pollFirst());
		}
		String value = TimeUtils.getCurrentTime() + "    " + msg;
		list.offerLast(value);
		redisService.redisSetList(Constants.MSG, list);
	}

	/*
	 * 参数：redis的key 功能：翻译key的列表list
	 */
	public List<String> getAllMsg() {
		List<String> tmp = (List<String>) redisService.getList(Constants.MSG);
		LinkedList<String> list = new LinkedList(tmp);
		return list;
	}

	/*
	 * 添加文件
	 */
	public synchronized void addFile(FileEntity fileEntity) {
		// 若数据库记录大于10，则从updateId开始覆盖数据
		if (isLarge) {
			fileMapping.removeFileById(updateId);
			updateId++;
		} else {
			updateId++;
			if (updateId == 10) {
				isLarge = true;
				updateId = 0;
			}
		}
		fileMapping.addFile(fileEntity);
	}

	/*
	 * 查询库中所有文件
	 */
	public List<FileEntity> getAllFiles() {
		return fileMapping.getFileList();
	}

	/*
	 * 存储文件到本地，并插入数据库信息
	 */
	@Transactional(rollbackFor = Exception.class)
	public void getData(@RequestParam(value = "file", required = false) List<MultipartFile> file) {
		try {
			String path = fileBasePath;
			for (MultipartFile f : file) {
				String name = f.getOriginalFilename();
				String url = path + name;
		        if (!file.isEmpty()) {
		            try {
		            	BufferedOutputStream out = new BufferedOutputStream(
			                    new FileOutputStream(new File(url)));
		                out.write(f.getBytes());
		                out.flush();
		            } catch (FileNotFoundException e) {
		                log.info("上传文件失败 FileNotFoundException：" + e.getMessage());
		            } catch (IOException e) {
		            	log.info("上传文件失败 IOException：" + e.getMessage());
		            }
		        } else {
		        	log.info("上传文件失败，文件为空");
		        }
				// 插入数据库
				FileEntity fileEntity = new FileEntity();
				fileEntity.setName(name);
				fileEntity.setUrl(url);
				addFile(fileEntity);
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.info("添加文件失败", e);
			throw new RuntimeException();
		}
	}

	/**
	 * 获取当前系统路径
	 */
	private String getUploadPath() {
		File path = null;
		try {
			path = new File(ResourceUtils.getURL("classpath:").getPath());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		if (!path.exists())
			path = new File("");
		File upload = new File(path.getAbsolutePath(), "static/files/");
		if (!upload.exists())
			upload.mkdirs();
		return upload.getAbsolutePath();
	}

	/*
	 * 传送文件
	 */
	public void downloadFile(HttpServletResponse resp, String filename) {
		try {
			// 获取服务器文件
			File file = new File(fileBasePath + filename);
	        if(!file.exists()){
	            log.info("下载文件不存在");
	        }
	        //解决下载文件时文件名乱码问题
	        byte[] fileNameBytes = filename.getBytes(StandardCharsets.UTF_8);
	        filename = new String(fileNameBytes, 0, fileNameBytes.length, StandardCharsets.ISO_8859_1);
	        resp.reset();
	        resp.setContentType("application/octet-stream");
	        resp.setCharacterEncoding("utf-8");
	        resp.setContentLength((int) file.length());
	        resp.setHeader("Content-Disposition", "attachment;filename=" + filename);
	        try{
	        	BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
	            byte[] buff = new byte[1024];
	            OutputStream os  = resp.getOutputStream();
	            int i = 0;
	            while ((i = bis.read(buff)) != -1) {
	                os.write(buff, 0, i);
	                os.flush();
	            }
	        } catch (IOException e) {
	            log.error("{}",e);
	            log.info("下载失败");
	        }
	        log.info("下载成功");
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
}
