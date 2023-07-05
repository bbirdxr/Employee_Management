package com.example.employee.mapper;

import com.example.entity.Position;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


import java.util.List;


/**
 * <p>
 * 职位表 Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2023-06-27
 */
@Mapper
public interface PositionMapper{
    @Select("select * from position where position_name like concat(#{name},'%') limit 6")
    List<Position> likeSelectByName(String name);

    @Select("select * from position where position_name=#{name}")
    Position selectByName(String name);

    @Select("select * from position where id=#{id}")
    Position selectById(Long id);

    @Insert("insert into position (position_name) values (#{name})")
    void addOne(String name);

    @Update("update position set position_name=#{newName} where position_name=#{name}")
    void updateByName(String name,String newName);

    @Update("update position set position_name=#{newName} where id=#{id}")
    void updateById(Long id,String newName);
}
