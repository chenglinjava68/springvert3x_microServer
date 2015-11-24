package com.solodream.spring.vertx.mapper;

import com.solodream.spring.vertx.domain.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {

	//@Select("select `id`, `name`, `age` from `user` where `id` = #{id}")
	User findById(@Param("id") String id);


	//@Insert("insert into user(`name`, `age`) values(#{name}, #{age})")
	void insert(@Param("name") String name, @Param("age") int age);
}
