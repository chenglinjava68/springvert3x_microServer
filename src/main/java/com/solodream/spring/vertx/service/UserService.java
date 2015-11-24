package com.solodream.spring.vertx.service;

import com.solodream.spring.vertx.domain.User;
import com.solodream.spring.vertx.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    //	private static Logger log = LoggerFactory.getLogger(StudyService.class);

    @Autowired
    private UserMapper userMapper;


    public void say() {
        System.out.println(">>>>>>>>>>>>>>>>>>fuck you<<<<<<<<<<<<<<<<<<<<<");
        User user = userMapper.findById("1");
        System.out.println("<<<<<<<<<<<<<<<<<<" + user.getName() + ">>>>>>>>>>>>>>>>>>>");
    }
//	@Async
//	public Future<Void> execute() {
//		return new AsyncResult<Void>(null);
//	}
//
//
//	@Async
//	public Future<User> findOne( String id ) {
//		return new AsyncResult<User>(userMapper.findById(id));
//	}
//
//
//	@Async
//	public Future<Void> addUser( String name ) {
//		userMapper.insert(StringUtils.defaultString(name, "testname"), 10);
//		return new AsyncResult<Void>(null);
//	}

}
