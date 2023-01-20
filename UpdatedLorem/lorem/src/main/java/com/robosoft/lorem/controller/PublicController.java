package com.robosoft.lorem.controller;

import com.robosoft.lorem.model.*;
import com.robosoft.lorem.response.BrandList;
import com.robosoft.lorem.routeResponse.Location;
import com.robosoft.lorem.service.DAOService;
import com.robosoft.lorem.service.EmailService;
import com.robosoft.lorem.service.SmsService;
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

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

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

    @Autowired
    EmailService emailService;
    @Autowired
    SmsService smsService;
    @Autowired
    DAOService daoService;





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

    @GetMapping("/getReviews")
    public ResponseEntity<?> getReviews(@RequestBody Restaurant restaurant)
    {
        try
        {
            Map<Integer,Object> reviewPageResponseList= userService.viewReviews(restaurant);
            if(reviewPageResponseList.size()==0)
            {
                return new ResponseEntity<>("No Reviews to this restaurant",HttpStatus.FORBIDDEN);
            }
            return new ResponseEntity<>(reviewPageResponseList, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>("No Reviews To this restaurant",HttpStatus.FORBIDDEN);
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
    @PutMapping("/emails2fa")
    public ResponseEntity<Object> send2faCodeInEmail(@RequestBody NewUser newuser) throws MessagingException {
        //2fa code generation
        Integer tfaCode =Integer.valueOf(new Random().nextInt(9999)+1000);

        boolean email_check= daoService.update2FAProperties(newuser.getEmailId(),tfaCode);

        if(email_check)
        {
            try
            {
                boolean emailSent= emailService.sendEmail(newuser.getEmailId(),tfaCode);
                if(emailSent)
                {
                    return new ResponseEntity<>("OTP sent to your email",HttpStatus.OK);
                }
            }
            catch (Exception e)
            {
                return  new ResponseEntity<>("OTP could not be sent",HttpStatus.FORBIDDEN);
            }
        }
        return  new ResponseEntity<>("Account already exists",HttpStatus.FORBIDDEN);
    }

    @PutMapping("/verifyEMail2fa")
    public ResponseEntity<Object> verifyEmail(@RequestBody NewUser newUser)
    {
        //front-end will provide the email id
        boolean isValid= daoService.checkEmailCode(newUser.getEmailId(),newUser.getEmailOtp());
        if(isValid)
        {
            return  new ResponseEntity<>("OTP verified",HttpStatus.OK);
        }
        return  new ResponseEntity<>("Invalid OTP",HttpStatus.FORBIDDEN);
    }


    @PutMapping("/verifyMobile2fa")
    public ResponseEntity<Object> verifySms(@RequestParam("userId") int userId,@RequestParam("mobileNo") String mobileNo,@RequestParam("otpNumber") int otpNumber)
    {
        boolean isValid= daoService.checkSmsCode(userId,mobileNo,otpNumber);
        if(isValid)
        {
            return  new ResponseEntity<>("OTP verified",HttpStatus.OK);
        }
        return  new ResponseEntity<>("Invalid OTP",HttpStatus.FORBIDDEN);
    }


    @PutMapping("/forgotPassword/email")
    public ResponseEntity<String> sendEmail2FaToResetPassword(@RequestBody NewUser newuser) throws MessagingException {
        Integer tfaCode =Integer.valueOf(new Random().nextInt(9999)+1000);

        boolean result= daoService.forgotPasswordEmail(newuser.getEmailId(), tfaCode);

        if(result)
        {
            try
            {
                boolean emailSent= emailService.sendEmail(newuser.getEmailId(),tfaCode);
                if(emailSent)
                {
                    return new ResponseEntity<>("OTP sent to your email",HttpStatus.OK);
                }
            }
            catch (Exception e)
            {
                return  new ResponseEntity<>("OTP could not be sent to your mail",HttpStatus.FORBIDDEN);
            }
        }
        //if no account found
        return  new ResponseEntity<>("No account with this email",HttpStatus.FORBIDDEN);
    }


    // since separate fields are required for updating passwords w.r.t email and number
    @PutMapping("/reset/password")
    public ResponseEntity<Object> resetPassword(@RequestBody User user)
    {
        //front end will check for otp verification and redirect to password resetting
        boolean resetPassword= daoService.resetPassword(user);
        if(resetPassword)
        {
            return  new ResponseEntity<>("Password reset successful",HttpStatus.OK);
        }
        return  new ResponseEntity<>("Could not reset password",HttpStatus.FORBIDDEN);
    }


    @PutMapping("/mobiles2fa")
    public ResponseEntity<String> resetPasswordThroughSms(@RequestBody User user) throws MessagingException {
        Integer tfaCode =Integer.valueOf(new Random().nextInt(9999)+1000);

        boolean result= daoService.forgotPasswordSms(user.getMobileNo(), tfaCode);

        if(result)
        {
            try
            {
                boolean smsSent= smsService.sendSms(user.getMobileNo(),tfaCode);
                if(smsSent)
                {
                    return new ResponseEntity<>("OTP sent to your mobile",HttpStatus.OK);
                }
            }
            catch (Exception e)
            {
                return  new ResponseEntity<>("OTP could not be sent to your number",HttpStatus.FORBIDDEN);
            }
        }
        //if no mobile number found
        return  new ResponseEntity<>("mobile number is not verified",HttpStatus.FORBIDDEN);
    }



    //extra api
    //@RequestMapping(value="/users/mobileNumber",method=RequestMethod.PUT)
    @PutMapping("/mobileNumber")
    public ResponseEntity<Object> updateMobileNumber(@RequestBody User user)
    {
        boolean updated= daoService.updatingNumber(user);
        if(updated)
        {
            return  new ResponseEntity<>("updated successfully",HttpStatus.OK);
        }
        return  new ResponseEntity<>("you Cannot update the same number",HttpStatus.FORBIDDEN);
    }
    //call the above 'verify' api to verify new number



    @GetMapping("/viewAllOffers")
    public ResponseEntity<?> viewAllOffers(@RequestParam int page)
    {

        Map<Integer,List<Offer>> offerList= userService.viewAllOffers(page);
        if(offerList==null)
        {
            return  new ResponseEntity<>("No offers ahead",HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(offerList, HttpStatus.OK);
    }

    @GetMapping("/viewBrandOffers")
    public ResponseEntity<?> viewBrandOffers(@RequestParam int brandID, @RequestParam int page)
    {
        Map<Integer,List<Offer>> offerList= userService.viewBrandOffers(brandID,page);
        if(offerList==null)
        {
            return  new ResponseEntity<>("No offers ahead for this brand",HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(offerList, HttpStatus.OK);
    }



    @GetMapping("/restaurant/best/offers")
    public ResponseEntity<?> getRestaurantOffers(@RequestParam int page, @RequestParam int restaurantId)
    {

        Map<Integer,List<Offer>> offers= userService.viewBestOfferOfRestaurant(page,restaurantId);

        if(offers==null)
        {
            return  new ResponseEntity<>("No offers ahead",HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(offers,HttpStatus.OK);
    }


    @GetMapping("/restaurant/all/offers")
    public ResponseEntity<?> getRestaurantAllOffers(@RequestParam int page, @RequestParam int restaurantId)
    {

        Map<Integer,List<Offer>> offers= userService.viewAllOffersOfRestaurant(page,restaurantId);

        if(offers==null)
        {
            return  new ResponseEntity<>("No offers ahead",HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(offers,HttpStatus.OK);
    }

    @GetMapping("/viewBestOffers")
    public ResponseEntity<?> viewBestOffers(@RequestParam int page)
    {
        Map<Integer,List<Offer>> offerList= userService.viewBestOffers(page);
        if(offerList==null)
        {
            return  new ResponseEntity<>("No offers ahead",HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(offerList, HttpStatus.OK);
    }



    @GetMapping("/view/gallery")
    public ResponseEntity<?> displayGallery(@RequestParam("restaurantId") int restaurantId, @RequestParam("page") int page)
    {
        Map<Integer,List<Menu>> gallery = userService.Gallery(restaurantId,page);

        if(gallery==null)
        {
            return new ResponseEntity<>("No photos here", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(gallery, HttpStatus.OK);
    }



    //nithin


    @GetMapping("/viewBrand/{brandId}")
    public ResponseEntity<BrandDesc> viewBrand(@PathVariable int brandId) throws IOException {
        BrandDesc brand = userService.viewBrand(brandId);
        if (brand == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.of(Optional.of(brand));

    }

    //    DISPLAY MENU ITEMS
    @GetMapping("/displayMenu/{restaurantId}/{pageNo}")
    public ResponseEntity<List<MenuItem>> displayMenu(@PathVariable int restaurantId,@PathVariable int pageNo) {

        List<MenuItem> menuItems = userService.DisplayMenu(restaurantId,pageNo);
        if (menuItems.size() <= 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.of(Optional.of(menuItems));
    }

    @GetMapping("/displayMenuItems/{restaurantId}/{pageNo}")
    public ResponseEntity<MenuItems> displayMenuItems(@PathVariable int restaurantId,@PathVariable int pageNo) {

        MenuItems menuItems = userService.DisplayMenuItems(restaurantId,pageNo);
        if (menuItems == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.of(Optional.of(menuItems));
    }


    //    SEARCH ITEM BY USING DISH NAME
    @GetMapping("/searchItem/{restaurantId}/{dishName}/{pageNo}")
    public ResponseEntity<List<MenuItem>> searchItem(@PathVariable int restaurantId, @PathVariable String dishName,@PathVariable int pageNo) {
        List<MenuItem> menuItems = userService.searchItem(restaurantId, dishName,pageNo);
        if (menuItems.size() <= 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.of(Optional.of(menuItems));
    }

    //    VIEW RESTAURANT
    @GetMapping("/viewRestaurant/{restaurantId}")
    public ResponseEntity<RestaurantDetails> viewRestaurant(@PathVariable int restaurantId, @RequestBody Location start) {
        RestaurantDetails restaurantDetails = userService.viewRestaurant(restaurantId, start);
        if (restaurantDetails == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.of(Optional.of(restaurantDetails));

    }

    //    OVERVIEW OF RESTAURANT
    @GetMapping("/overView/{restaurantId}")
    public ResponseEntity<OverviewDetails> overView(@PathVariable int restaurantId) {
        OverviewDetails overviewDeatails = userService.overview(restaurantId);
        if (overviewDeatails == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.of(Optional.of(overviewDeatails));
    }



    //       OPENING INFORMATION FOR CURRENT DATE
    @GetMapping("/opening/{restaurantId}")
    public ResponseEntity<OpeningDetails> opening(@PathVariable int restaurantId) {
        OpeningDetails openingDetails = userService.opening(restaurantId);
        if (openingDetails == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.of(Optional.of(openingDetails));
    }


    //    OPENING INFORMATION FOR NEXT 7 DAYS FROM CURRENT DATE
    @GetMapping("/openingsFor7Days/{restaurantId}")
    public ResponseEntity<List<OpeningDetails>> openingsFor7Days(@PathVariable int restaurantId) {
        List<OpeningDetails> openingDetails = userService.openingsFor7Days(restaurantId);
        if (openingDetails == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.of(Optional.of(openingDetails));
    }

    //SEARCH RESTAURANT BY USING DISH TYPE
    @GetMapping("/Searching/{restaurantId}/{dishType}/{pageNo}")
    public ResponseEntity<List<MenuDetails>> search(@PathVariable int restaurantId, @PathVariable String dishType,@PathVariable int pageNo) {
        List<MenuDetails> menuDetails = userService.menuDetails(restaurantId, dishType, "",pageNo);
        if (menuDetails.size() <= 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.of(Optional.of(menuDetails));
    }










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
