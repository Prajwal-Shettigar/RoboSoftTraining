package com.prajwal.moviesystem.Service;

import com.prajwal.moviesystem.Model.Admin;
import com.prajwal.moviesystem.Model.Movie;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class AdminServiceTest {


    @Mock
    AdminService adminService;


    @Test
    public void testLogin(){
        Admin admin = new Admin();
        admin.setId(1);
        admin.setPassword("12345");

        when(adminService.adminLogin(admin)).thenReturn(true);

        assertTrue(adminService.adminLogin(admin));

    }

    @Test
    public void testAddMovie(){
        Movie movie = new Movie();
        movie.setId(1);
        movie.setName("prajwal");
        movie.setDescription("description");
        movie.setDuration(2);
        movie.setRating(3.5);

        when(adminService.addMovie(movie)).thenReturn(2);


        assertEquals(2,adminService.addMovie(movie));

        //test wheteher the method was called once succeeds as it is called in the assert statement
        verify(adminService,times(1)).addMovie(movie);

        //checks if the method was called atleast once
        verify(adminService,atLeast(1)).addMovie(movie);


        //checks if method is called at most once
        verify(adminService,atMostOnce()).addMovie(movie);
    }
}