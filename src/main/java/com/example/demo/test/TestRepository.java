package com.example.demo.test;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestRepository {

    String test();
}
