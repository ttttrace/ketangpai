package mybatis.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mybatis.demo.dao.HomeworkMapper;
import mybatis.demo.entity.Homework;
import mybatis.demo.entity.HomeworkStudent;

@Service
@Transactional
public class HomeworkService {
	@Autowired
	private HomeworkMapper homeworkMapper;
	public List<Homework> getHomeworks(String courseId){
		return homeworkMapper.getHomeworks(courseId);
	}
	public void addHomework(Homework homework) {
		homeworkMapper.addHomework(homework);
	}
	public void createTablework(int homeworkId,String courseId) {
		homeworkMapper.createTablework(homeworkId, courseId);
	}
	
	public void deleteHomework(int id) {
		homeworkMapper.deleteHomework(id);
	}
	
	public String unRead(int id) {
		return homeworkMapper.unRead(id);
	}
	
	public String read(int id) {
		return homeworkMapper.read(id);
	}
	
	public String unSubmit(int id) {
		return homeworkMapper.unSubmit(id);
	}
	
	public String isSubmit(int homeworkId,int id) {
		return homeworkMapper.isSubmit(homeworkId, id);
	}
	
	public void deleteWorkStu(int id) {
		homeworkMapper.deleteWorkStu(id);
	}
	
	public Homework getWork(int id) {
		return homeworkMapper.getWork(id);
	}
	
	public void addHomeworkStu(int workId,String file,String date,int id) {
		homeworkMapper.addHomeworkStu(workId, file, date, id);
	}
	
	public HomeworkStudent getWorkStu(int workId,int id) {
		return homeworkMapper.getWorkStu(workId, id);
	}
	public void addnewStu(int workId,int id,String schoolId,String name) {
		homeworkMapper.addnewStu(workId, id, schoolId, name);
	}
}
