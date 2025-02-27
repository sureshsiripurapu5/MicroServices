package com.microservices.user.service;

import com.microservices.user.VO.Department;
import com.microservices.user.VO.ResponseTemplateVO;
import com.microservices.user.entity.User;
import com.microservices.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private RestTemplate restTemplate;
    public User saveUser(User user) {
        log.info("Inside saveUser method of UserService");
        return userRepository.save(user);
    }

    public ResponseTemplateVO getUserWithDepartment(Long userId) {

        log.info("Inside getUserWithDepartment methof of UserService");
        ResponseTemplateVO vo=new ResponseTemplateVO();
        User user=userRepository.findByUserId(userId);

        Department department=
                   restTemplate.getForObject("http://localhost:9001/departments/" + user.getDepartmentId()
                                    ,Department.class);

        vo.setUser(user);
        vo.setDepartment(department);

        return vo;
    }
}
