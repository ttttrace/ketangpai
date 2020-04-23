package mybatis.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mybatis.demo.entity.Homework;
import mybatis.demo.entity.HomeworkStudent;
import mybatis.demo.service.HomeworkService;

@RestController
@RequestMapping("/homework")
public class HomeworkController {
	@Autowired
	private HomeworkService homeworkService;
	
	@GetMapping("getWork")
	public Homework getWork(int id) {
		return homeworkService.getWork(id);
	}
	@GetMapping("getHomeworks")
	public List<Homework> getHomeworks(String courseId) {
		List<Homework> works=homeworkService.getHomeworks(courseId);
		for(int i=0;i<works.size();++i) {
			works.get(i).setUnRead(homeworkService.unRead(works.get(i).getId()));
			works.get(i).setUnSubmit(homeworkService.unSubmit(works.get(i).getId()));
			works.get(i).setRead(homeworkService.read(works.get(i).getId()));
		}
		return works;
	}
	
	@GetMapping("getWorkStu")
	public  HomeworkStudent getWorkStu(int workId,int id) {
		return homeworkService.getWorkStu(workId, id);
	}
	
	@GetMapping("getHomeworksStudent")
	public List<Homework> getHomeworksStudent(String courseId,int id) {
		List<Homework> works=homeworkService.getHomeworks(courseId);
		for(int i=0;i<works.size();++i) {
			works.get(i).setUnSubmit(homeworkService.isSubmit(works.get(i).getId(),id));
		}
		return works;
	}
	
	@PostMapping("addHomework")
	public void addHomework(@RequestBody(required = false) Homework homework) {
		homeworkService.addHomework(homework);
		homeworkService.createTablework(homework.getId(), homework.getCourseId());
	}
	
	@PostMapping("deleteWork")
	public void deleteWork(int id) {
		homeworkService.deleteHomework(id);
		homeworkService.deleteWorkStu(id);
	}
	
	@PostMapping("addHomeworkStu")
	public void addHomeworkStu(int workId,String file,String date,int id) {
		homeworkService.addHomeworkStu(workId, file, date, id);
	}
}
