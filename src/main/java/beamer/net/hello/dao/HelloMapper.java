package beamer.net.hello.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import beamer.net.hello.model.Name;

public interface HelloMapper {
	
	@Select("select id, name from hello")
    public List<Name> getNames();
	
	@Select("select id, name from hello where id = #{id}")
	public Name getName(Long id);
	
	@Insert("insert into hello (name) values (#{name})")
	public int createName(String name);
	
	@Delete("delete from hello where name = #{name}")
	public int deleteName(String name);
}
