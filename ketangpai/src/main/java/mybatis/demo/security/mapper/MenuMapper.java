package mybatis.demo.security.mapper;

import org.apache.ibatis.annotations.Mapper;

import mybatis.demo.security.model.Menu;

import java.util.List;

@Mapper
public interface MenuMapper {
    List<Menu> getAllMenus();
}
