package com.github.thushear.redis.repo;

import com.github.thushear.redis.cache.Student;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * Created by kongming on 2017/4/17.
 */
@EnableRedisRepositories(basePackages = "com.github.thushear.redis.repo")
@Component("studentRepository")
public interface StudentRepository extends CrudRepository<Student,String> {

}
