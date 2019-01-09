package com.xiaoxiao.server.dao;

import com.xiaoxiao.server.pojo.Hobby;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HobbyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Hobby record);

    int insertSelective(Hobby record);

    Hobby selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Hobby record);

    int updateByPrimaryKey(Hobby record);
}