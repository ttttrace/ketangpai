package mybatis.demo.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import mybatis.demo.config.AppConfig;
@RestController
@RequestMapping("upload")
public class uploadController {
	@Autowired private AppConfig config;
	
	@RequestMapping("/upload")
	@ResponseBody
	public String uploadImg(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws SerialException, SQLException {
		if (!file.isEmpty()) {
			String saveFileName = getFileName(file);
			System.out.println(file.getName());
			File saveFile = new File(config.getUploadFolder() + saveFileName);
			System.out.println(saveFile.getAbsolutePath());
			if (!saveFile.getParentFile().exists()) {
				saveFile.getParentFile().mkdirs();
			}
			try {
				BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(saveFile));
				out.write(file.getBytes());
				out.flush();
				out.close();
				return "{\"url\":[\""+config.getPreviewPath() + saveFile.getName()+"\"]}";
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return "上传失败,";
			} catch (IOException e) {
				e.printStackTrace();
				return "上传失败,";
			}
		} else {
			return "上传失败，因为文件为空.";
		}
	}
	
	@PostMapping("removeFile")
	public String delFile(String path) {
    	String resultInfo = null;
		int lastIndexOf = path.lastIndexOf("/");
		String sb = path.substring(lastIndexOf + 1, path.length());
		sb = config.getUploadFolder() + sb;
		System.out.println(sb);
		File file = new File(sb);
		if (file.exists()) {
			if (file.delete()) {
				resultInfo =  "1-删除成功";
			} else {
				resultInfo =  "0-删除失败";
			}
		} else {
			resultInfo = "文件不存在！";
		}
		System.out.println(resultInfo);
		return resultInfo;
	}

	private String getFileName(MultipartFile file) {
		String name = file.getOriginalFilename();
		StringBuilder sb = new StringBuilder();
		Date date = new Date();
		sb.append("(");
		sb.append(date.getTime());
		sb.append(")");
		sb.append(name);
		return sb.toString();
	}
}
