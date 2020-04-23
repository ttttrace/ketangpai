package mybatis.demo.security.service;

import mybatis.demo.entity.TS;
import mybatis.demo.security.mapper.UserMapper;
import mybatis.demo.security.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserMapper userMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.loadUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("账户不存在!");
        }
        user.setRoles(userMapper.getUserRolesByUid(user.getId()));
        return user;
    }
    
    public int addNewUser(User user) {
    	return userMapper.addNewUser(user);
    }
    
    public void addRole(int uid,int rid) {
    	userMapper.addUserRole(uid, rid);
    }
    
    public void addTorS(TS ts) {
    	userMapper.addTorS(ts);
    }
    
    public TS getUser(int id) {
    	return userMapper.getUser(id);
    }
    public String getUsername(int id) {
    	return userMapper.getUsername(id);
    }
}
