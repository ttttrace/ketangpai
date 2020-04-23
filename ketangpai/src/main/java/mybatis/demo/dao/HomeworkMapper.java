package mybatis.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import mybatis.demo.entity.Homework;
import mybatis.demo.entity.HomeworkStudent;

@Mapper
public interface HomeworkMapper {
	List<Homework> getHomeworks(String courseId);
	void addHomework(Homework homework);
	void createTablework(int homeworkId,String courseId);
	void deleteHomework(int id);
	String unRead(int id);
	String unSubmit(int id);
	String read(int id);
	String isSubmit(int homeworkId,int id);
	void deleteWorkStu(int id);
	Homework getWork(int id);
	void addHomeworkStu(int workId,String file,String date,int id);
	HomeworkStudent getWorkStu(int workId,int id);
	void addnewStu(int workId,int id,String schoolId,String name);
}
