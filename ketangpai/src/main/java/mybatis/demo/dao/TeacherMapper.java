package mybatis.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import mybatis.demo.entity.HomeworkStudent;

@Mapper
public interface TeacherMapper {
	List<HomeworkStudent> getAllStudents(int id);
	void updateScore(int id,String score,int isRead,String schoolId);
	List<HomeworkStudent> search(int id,String key);
}
