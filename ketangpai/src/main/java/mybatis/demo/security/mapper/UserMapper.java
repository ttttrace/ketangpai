package mybatis.demo.security.mapper;

import org.apache.ibatis.annotations.Mapper;

import mybatis.demo.entity.TS;
import mybatis.demo.security.model.Role;
import mybatis.demo.security.model.User;

import java.util.List;

@Mapper
public interface UserMapper {
    User loadUserByUsername(String username);
    List<Role> getUserRolesByUid(Integer id);
    int addNewUser(User user);
    void addUserRole(int uid,int rid);
    void addTorS(TS ts);
    TS getUser(int id);
   String getUsername(int id);
   
}
