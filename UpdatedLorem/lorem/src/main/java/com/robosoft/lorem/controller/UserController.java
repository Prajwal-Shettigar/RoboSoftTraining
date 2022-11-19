package com.robosoft.lorem.controller;
import com.cloudinary.utils.ObjectUtils;
import com.robosoft.lorem.entity.OpeningInfo;
import com.robosoft.lorem.model.*;
import com.robosoft.lorem.response.BrandList;
import com.robosoft.lorem.response.OrderDetails;
import com.robosoft.lorem.routeResponse.Location;
import com.robosoft.lorem.service.CloundinaryConfig;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/User")
public class UserController
{

    @Autowired
    private AuthenticationManager authenticationManager;

    //for using user services
    @Autowired
    private UserService userService;


    //for using user details services
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    CloundinaryConfig cloudinary;


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

    @PutMapping("/likeBrand")
    public ResponseEntity<String> addToFav(@RequestBody FavTable favTable)
    {
        try
        {
            boolean check= userService.addToFavourite(favTable);
            if(check==true)
            {
                return new ResponseEntity("Added to favorites",HttpStatus.OK);
            }
            else
            {
                return new ResponseEntity("Failed to add favorites",HttpStatus.BAD_GATEWAY);
            }
        }
        catch (Exception e)
        {
            return new ResponseEntity("Failed to add favorites",HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/viewPopularBrands")
    public ResponseEntity<?>listPopularBrands()
    {
        try
        {
            Map<Integer, List<BrandList>> brandLists= userService.viewPopularBrands();
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

    @PostMapping("/review")
    public ResponseEntity<String> addReview(@ModelAttribute ReviewInfo reviewInfo)
    {
        try
        {
            try
            {
                List<String> photoLinks = new ArrayList<String>();
                for (int i = 0; i < reviewInfo.getMultipartFileList().size(); i++)
                {
                    Map uploadResult = cloudinary.upload(reviewInfo.getMultipartFileList().get(i).getBytes(), ObjectUtils.asMap("resourcetype", "auto"));
                    photoLinks.add(uploadResult.get("url").toString());
                }
                reviewInfo.setPhotoLinks(photoLinks);
                userService.addReview(reviewInfo);
            }
            catch (Exception e)
            {
                userService.addReview(reviewInfo);
            }
            return new ResponseEntity(HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity("Failed to add review",HttpStatus.BAD_GATEWAY);
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

    @GetMapping("/getOrderDetails")
    public OrderDetails getOrderDetails(@RequestBody Orders orders)
    {
        return userService.getOrderDetails(orders);
    }

    @PostMapping("/addCard")
    public ResponseEntity<String> addCard(@RequestBody Card card)
    {
        try
        {
            boolean message= userService.addCard(card);
            if(message==true)
            {
                return new ResponseEntity("Card added successfully", HttpStatus.OK);
            }
            else
            {
                return new ResponseEntity("Failed to add card", HttpStatus.BAD_GATEWAY);
            }
        }
        catch (Exception e)
        {
            return new ResponseEntity("Failed to add card", HttpStatus.BAD_GATEWAY);
        }

    }

    @GetMapping("/viewCards")
    public ResponseEntity<?> viewCards(@RequestBody User user)
    {
        try
        {
            Map<Integer, List<Card>> cardLists= userService.viewCards(user);
            if(cardLists.size()==0)
            {
                return new ResponseEntity<>("No Cards Saved Please Add Some Cards",HttpStatus.FORBIDDEN);
            }
           return new ResponseEntity<>(cardLists,HttpStatus.ACCEPTED);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>("No Cards Saved Please Add Some Cards",HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/editCard")
    public ResponseEntity<String> editCard(@RequestBody Card card)
    {
        try
        {
            if(this.userService.editCard(card)==true)
            {
                return ResponseEntity.ok().body("Card Updated Successfully");
            }
            else
            {
                return ResponseEntity.ok().body("You cant edit this card");
            }
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
        }
    }

    @PutMapping("/makeCardPrimary")
    public ResponseEntity<String> makeCardPrimary(@RequestBody Card card)
    {
        try
        {
            this.userService.makeCardPrimary(card);
            return ResponseEntity.ok().body(card.getCardNo()+" Selected as primary");
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Something went wrong");
        }
    }

    @PutMapping("/deleteCard")
    public ResponseEntity<String> deleteCard(@RequestBody Card card)
    {
        try
        {
            this.userService.deleteCard(card);
            return ResponseEntity.ok().body(card.getCardNo()+" Removed successfully");
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Something went wrong");
        }
    }

    @PutMapping("/makePayment")
    public ResponseEntity<String> makePayment(@RequestBody Payment payment)
    {
        try
        {
            String message = userService.makePayment(payment);
            return new ResponseEntity(message, HttpStatus.OK);
        }
        catch (Exception e)
        {
            String message= userService.makePayment(payment);
            return new ResponseEntity(message,HttpStatus.BAD_GATEWAY);
        }
    }

    @PutMapping("/giveFeedback")
    public ResponseEntity<String> giveFeedback(@RequestBody FeedBack feedBack)
    {
      try
      {
          if(userService.giveFeedback(feedBack)==true)
          {
              return new ResponseEntity("Thank you for your feedback", HttpStatus.OK);
          }
          else
          {
              return new ResponseEntity("Something went wrong",HttpStatus.BAD_GATEWAY);
          }
      }
      catch (Exception e)
      {
          return new ResponseEntity("Something went wrong",HttpStatus.BAD_GATEWAY);
      }

    }

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
    @GetMapping("/Brands/{pageNumber}")
    public ResponseEntity<?> getNearByBrands(@RequestBody Location address, @PathVariable int pageNumber){
        NearByBrandsSearchResult nearByBrandsSearchResult = userService.getNearbyBrands(address,pageNumber);

        if(pageNumber==1 || pageNumber==0){
            if(nearByBrandsSearchResult.getTotalResultsCount()==0)
                return new ResponseEntity<String>("No Brands Nearby..",HttpStatus.NO_CONTENT);
        }

        if(nearByBrandsSearchResult.getPageResultsCount()<1)
            return new ResponseEntity<String>("No Contents in the requested page..",HttpStatus.NO_CONTENT);

        return ResponseEntity.ok(nearByBrandsSearchResult);
    }


    //create and update cart
    @PostMapping("/Cart")
    public ResponseEntity<?> createOrUpdateCart(@RequestBody CartModel cartModel){
        cartModel.setUserId(userService.getUserIdFromEmail());
        CartModel returnedCartModel =  userService.saveOrUpdateCart(cartModel);

        if(returnedCartModel==null)
            return new ResponseEntity<>("Cart Not Updaated..",HttpStatus.EXPECTATION_FAILED);

        return ResponseEntity.ok(returnedCartModel);

    }


    //like and unlike review
    @PostMapping("/Review/Like/{reviewId}")
    public ResponseEntity<?> likeAReview(@PathVariable int reviewId){
        if(userService.likeAreview(userService.getUserIdFromEmail(), reviewId))
            return new ResponseEntity<>("Liked Review Successfully...",HttpStatus.OK);

        return new ResponseEntity<>("UnLiked Review Successfully...",HttpStatus.EXPECTATION_FAILED);
    }


    //get My Profile
    @GetMapping("/Profile")
    public ResponseEntity<?> getUserProfile(){
        UserProfile userProfile = userService.getUserProfile(userService.getUserIdFromEmail());

        if(userProfile!=null)
            return new ResponseEntity<>(userProfile,HttpStatus.OK);

        return new ResponseEntity<>(userProfile,HttpStatus.NO_CONTENT);
    }


    //get my orders based on status
    @GetMapping("/Orders/{orderStatus}/{pageNumber}")
    public ResponseEntity<?> getMyOrdersByStatus(@PathVariable String orderStatus,@PathVariable int pageNumber){

        OrderResponseModel orderResponseModel = userService.getMyOrdersByStatus(orderStatus, userService.getUserIdFromEmail(), pageNumber);

        if(orderResponseModel==null)
            return new ResponseEntity<>("No Content in current page..",HttpStatus.NO_CONTENT);

        if(pageNumber==1){
            if(orderResponseModel.getTotalRecordsCount()==0)
                return new ResponseEntity<>("No Orders Found under this user id..",HttpStatus.NO_CONTENT);
        }

        if(orderResponseModel.getTotalRecordsInPage()==0)
            return new ResponseEntity<>("No Content in current page..",HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(orderResponseModel,HttpStatus.OK);
    }


    //get my carts
    @GetMapping("/Carts/{pageNumber}")
    public ResponseEntity<?> getMyCarts(@PathVariable int pageNumber){
        CartsResponseModel cartsResponseModel =  userService.getMyCarts(userService.getUserIdFromEmail(),pageNumber);

        if(pageNumber==1){
            if(cartsResponseModel.getTotalResultCount()==0)
                return new ResponseEntity<>("No Carts found under this user id..",HttpStatus.NO_CONTENT);
        }

        if(cartsResponseModel.getPerPageCount()==0)
            return new ResponseEntity<>("No Content in current page..",HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(cartsResponseModel,HttpStatus.OK);
    }


    //remove a cart
    @DeleteMapping("/Cart/{cartId}")
    public HttpStatus deleteMyCart(@PathVariable int cartId){
        if(userService.removeCart(userService.getUserIdFromEmail(), cartId))
            return HttpStatus.OK;

        return HttpStatus.EXPECTATION_FAILED;
    }


    //get cart by cart id and user id
    @GetMapping("/Cart")
    public ResponseEntity<?> getCartById(@RequestBody CartModel cartModel)
    {
        cartModel.setUserId(userService.getUserIdFromEmail());
        CartModel returningCartModel = userService.getCartById(cartModel);

        if(returningCartModel==null)
            return new ResponseEntity<String>("No Cart matching given details found..",HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(returningCartModel,HttpStatus.OK);
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

    //   ADDING OPENING INFORMATION
    @PostMapping("/addOpeningInfo/{restaurantId}")
    public ResponseEntity<String> addOpeningInfo(@ModelAttribute OpeningInfo openingInfo, @PathVariable int restaurantId) throws Exception {
        if (userService.addOpeningInfo(openingInfo, restaurantId, userService.getUserIdFromEmail())) {
            return ResponseEntity.status(HttpStatus.OK).body("successful");

        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Task failed");

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

    //adding new address
    @PostMapping("/addAddress")
    public ResponseEntity<String> addAddress(@RequestBody Address address) throws Exception {
        if (userService.addAddress(address)) {
            return ResponseEntity.status(HttpStatus.OK).body("Address added successfully");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Task failed");

    }

    //editing address by user
    @PostMapping("/editAddress/{addressId}")
    public ResponseEntity<String> editAddress(@RequestBody Address address, @PathVariable int addressId) throws Exception {
        if (userService.editAddress(address, addressId, userService.getUserIdFromEmail())) {
            return ResponseEntity.status(HttpStatus.OK).body("Address updated successfully");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Task failed");

    }

    //    deleting the address
    @DeleteMapping("/deleteAddress/{addressId}")
    public ResponseEntity<String> deleteAddress(@PathVariable int addressId) throws Exception {
        if (userService.deleteAddress(addressId, userService.getUserIdFromEmail())) {
            return ResponseEntity.status(HttpStatus.OK).body("Address deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("address deletion failed");

    }

    // setting primary address by user
    @PutMapping("/setPrimaryAddress/{addressId}")
    public ResponseEntity<String> setPrimaryAddress(@PathVariable int addressId) throws Exception {
        if (userService.setPrimaryAddress(addressId, userService.getUserIdFromEmail())) {
            return ResponseEntity.status(HttpStatus.OK).body("primary address set successfully");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("failed");

    }

    @GetMapping("/displayAddress")
    public ResponseEntity<List<AddressDetails>> displayAddress() {
        List<AddressDetails> addressList = userService.displayAddress(userService.getUserIdFromEmail());
        if (addressList.size() <= 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.of(Optional.of(addressList));
    }

    @GetMapping("/displayAddresses")
    public ResponseEntity<AddressDesc> displayAddresses()
    {
        AddressDesc addressDesc = userService.displayAddresses(userService.getUserIdFromEmail());
        if(addressDesc==null)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.of(Optional.of(addressDesc));
    }

    @PostMapping("/choosePayment")
    public ResponseEntity<?> chooseAddress(@RequestBody Orders orders) throws Exception {
        OrderInfo orderInfo = userService.chooseAddress(orders, userService.getUserIdFromEmail(), orders.getLocation());
        if(orderInfo==null)
        {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("failed");
        }
        return ResponseEntity.of(Optional.of(orderInfo));

    }

    @PutMapping("/cancelOrder")
    public ResponseEntity<?>cancelOrder(@RequestBody Orders orders) throws IOException {
        if(userService.cancelOrder(orders))
        {
            return ResponseEntity.status(HttpStatus.OK).body("order canceled");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("failed to cancel order");
    }

    @GetMapping("/viewBrand/{brandId}")
    public ResponseEntity<BrandDesc> viewBrand(@PathVariable int brandId) throws IOException {
        BrandDesc brand = userService.viewBrand(brandId);
        if (brand == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.of(Optional.of(brand));

    }


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

//    @GetMapping("/getpass")
//    public User getPass()
//    {
//        return userServiceImpl.getPass();
//    }

    @PutMapping("/editProfile")
    public ResponseEntity<String> editProfile(@ModelAttribute UserEditFields user) throws IOException {
        Map uploadResult2 = cloudinary.upload(user.getProfilePic().getBytes(), ObjectUtils.asMap("resourcetype", "auto"));
        String photo_url=uploadResult2.get("url").toString();
        user.setProfileUrl(photo_url);

        boolean reg_user = userService.editProfile(user);
        if(reg_user)
        {
            return new ResponseEntity<String >("updated successfully", HttpStatus.OK);
        }
        //e.printStackTrace();
        return new ResponseEntity<>("Could not update", HttpStatus.FORBIDDEN);
    }

    @GetMapping("/refer")
    public ResponseEntity<Integer> referAFriend()
    {
        try
        {
            int reg_user = userService.referAFriend();
            return new ResponseEntity<Integer>(reg_user, HttpStatus.OK);
        } catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // if required
    @GetMapping("/share/referAFriend")
    public ResponseEntity<Map<String,String>> shareReference(String userId)
    {
        try
        {
            Map<String,String> reg_user = userService.onClickShareApp(userId);
            return new ResponseEntity<Map<String,String> >(reg_user, HttpStatus.OK);
        } catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
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

    @GetMapping("/viewDetails")
    public ResponseEntity<?> viewDetails(@RequestParam String offerId)
    {
        ResponseEntity<?> offerObj= userService.viewOfferDetails(offerId);
        return offerObj;
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


    @PutMapping("/redeem")
    public ResponseEntity<?> redeemItNow(@RequestParam int claimedCreditScore)
    {
        ResponseEntity<?> claimed= userService.redeem(claimedCreditScore);
        return claimed;
    }

    @GetMapping("/apply")
    public ResponseEntity<?> applyOffer(@RequestParam String offerId)
    {
        ResponseEntity<?> applied= userService.applyOffer(offerId);
        return applied;
    }



}
