package mybatis.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mybatis.demo.entity.HomeworkStudent;
import mybatis.demo.service.TeacherService;

@RestController
@RequestMapping("/teacher")
public class TeacherController {
	@Autowired
	private TeacherService service;
	
	@GetMapping("getAllStudents")
	public  List<HomeworkStudent> getAllStudents(int id){
		return service.getAllStudents(id);
	}
	
	@PostMapping("updateScore")
	public void updateScore(int id,String score,int isRead,String schoolId) {
		service.updateScore(id, score, isRead,schoolId);
	}
	
	@GetMapping("search")
	public List<HomeworkStudent> search(int id,String key){
		return service.search(id, key);
	}
}
