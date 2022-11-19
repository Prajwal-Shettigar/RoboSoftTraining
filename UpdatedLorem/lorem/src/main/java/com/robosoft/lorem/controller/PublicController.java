package com.robosoft.lorem.controller;

import com.robosoft.lorem.model.*;
import com.robosoft.lorem.response.BrandList;
import com.robosoft.lorem.routeResponse.Location;
import com.robosoft.lorem.service.UserService;
import com.robosoft.lorem.utility.JWTUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Lorem")
public class PublicController {

    @Autowired
    private AuthenticationManager authenticationManager;

    //for using user details services
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    private UserService userService;





    //Abhishek

    @PostMapping("/authenticate")
    public JWTResponse authenticate(@RequestBody JWTRequest jwtRequest) throws Exception
    {

        try
        {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            jwtRequest.getEmailId(),
                            jwtRequest.getPassword()
                    )
            );
        }
        catch (BadCredentialsException e)
        {
            throw new Exception("INVALID_CREDENTIALS", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(jwtRequest.getEmailId());

        final String token = jwtUtility.generateToken(userDetails);

        return  new JWTResponse(token);
    }


    @GetMapping("/viewPopularBrands")
    public ResponseEntity<?> listPopularBrands()
    {
        try
        {
            Map<Integer, List<BrandList>> brandLists= userService.viewPopularBrands();
            if(brandLists.size()==0)
            {
                return new ResponseEntity<>("No Popular brands to show", HttpStatus.FORBIDDEN);
            }
            return new ResponseEntity<>(brandLists,HttpStatus.OK);
        }
        catch (Exception exception)
        {
            return new ResponseEntity<>("No Popular brands to show",HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/viewAllBrands")
    public ResponseEntity<?> viewAllPopularBrands()
    {
        try
        {
            Map<Integer, List<BrandList>> brandLists= userService.viewAllBrands();
            if(brandLists.size()==0)
            {
                return new ResponseEntity<>("No Popular brands to show",HttpStatus.FORBIDDEN);
            }
            return new ResponseEntity<>(brandLists,HttpStatus.OK);
        }
        catch (Exception exception)
        {
            return new ResponseEntity<>("No Popular brands to show",HttpStatus.FORBIDDEN);
        }
    }


    //Akrithi
    @PostMapping("/register")
    public ResponseEntity<?> Register(@RequestBody User user)
    {
        boolean reg_user = userService.createAccount(user);
        if(reg_user)
        {
            return new ResponseEntity<>("Hi "+user.getFirstName()+" Welcome to lorem", HttpStatus.OK);
        }
        return new ResponseEntity<>("Please verify your email", HttpStatus.FORBIDDEN);
    }



    //nithin


    //Prajwal
    //search with filter
    @GetMapping("/Search")
    public ResponseEntity<?> searchRestaurants(@RequestBody SearchFilter searchFilter) {
        RestaurantSearchResult restaurantSearchResult = userService.searchRestaurant(searchFilter);
        if (searchFilter.getPageNumber()==1){
            if(restaurantSearchResult.getTotalRocordsCount()==0)
                return new ResponseEntity<>("No Restaurants matching the search query found..",HttpStatus.NO_CONTENT);
        }

        if(restaurantSearchResult.getPerPageRecordsCount()==0)
            return new ResponseEntity<>("No Content in this page..",HttpStatus.NO_CONTENT);


        return new ResponseEntity<>(restaurantSearchResult,HttpStatus.OK);
    }


    //get nearby brands
    @GetMapping("/Brands/{pageNumber}/{limit}")
    public ResponseEntity<?> getNearByBrands(@RequestBody Location address, @PathVariable int pageNumber, @PathVariable int limit){
        NearByBrandsSearchResult nearByBrandsSearchResult = userService.getNearbyBrands(address,pageNumber,limit);

        if(pageNumber==1 || pageNumber==0){
            if(nearByBrandsSearchResult.getTotalResultsCount()==0)
                return new ResponseEntity<String>("No Brands Nearby..",HttpStatus.NO_CONTENT);
        }

        if(nearByBrandsSearchResult.getPageResultsCount()<1)
            return new ResponseEntity<String>("No Contents in the requested page..",HttpStatus.NO_CONTENT);

        return ResponseEntity.ok(nearByBrandsSearchResult);
    }
}
