package com.prajwal.jpql.service;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class EmployeeServiceTest {


    @Test
    public void testSomething(){
        List list = spy(ArrayList.class);

        list.add(1);
        list.add(2);

        assertEquals(2,list.size());
    }

    @Test
    public void testMockito(){

    }
}