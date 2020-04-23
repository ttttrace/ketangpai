package mybatis.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mybatis.demo.dao.TeacherMapper;
import mybatis.demo.entity.HomeworkStudent;

@Service
@Transactional
public class TeacherService {
	@Autowired TeacherMapper mapper;
	public List<HomeworkStudent> getAllStudents(int id){
		return mapper.getAllStudents(id);
	}
	public void updateScore(int id,String score,int isRead,String schoolId) {
		mapper.updateScore(id, score, isRead,schoolId);
	}
	public List<HomeworkStudent> search(int id,String key){
		return mapper.search(id, key);
	}
}
