package mybatis.demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mybatis.demo.entity.TS;
import mybatis.demo.security.model.User;
import mybatis.demo.security.service.UserService;
import net.sf.json.JSONObject;
@RestController
@RequestMapping("user")
public class UserController {
	@Autowired
	UserService userService;
	BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10);

	@PostMapping("register/user")
	public int registerUser(@RequestBody(required = false) Map<String, Object> map) {
		User user = (User)JSONObject.toBean(JSONObject.fromObject(map.get("user")),User.class );
		TS ts = (TS) JSONObject.toBean(JSONObject.fromObject(map.get("ts")),TS.class);
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userService.addNewUser(user);
		ts.setId(user.getId());
		userService.addTorS(ts);
		return user.getId();
	}

	@PostMapping("register/role")
	public void registerRole(int uid, int rid) {
		userService.addRole(uid, rid);
	}
	
	@GetMapping("userInfo")
	public TS getUser(int id) {
		return userService.getUser(id);
	}
	
	@GetMapping("userInfo/username")
	public String getUsername(int id) {
		return userService.getUsername(id);
	}

}
