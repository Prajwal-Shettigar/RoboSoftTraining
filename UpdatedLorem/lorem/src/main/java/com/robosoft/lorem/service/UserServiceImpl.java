package com.robosoft.lorem.service;
import com.robosoft.lorem.entity.Addon;
import com.robosoft.lorem.entity.OpeningInfo;
import com.robosoft.lorem.model.*;
import com.robosoft.lorem.response.*;
import com.robosoft.lorem.routeResponse.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.*;

@Service
public class UserServiceImpl implements UserService,UserDetailsService
{

    private static final String REGISTER_WITH_MOBILE="INSERT INTO USER(firstName,lastName,emailId,mobileNo,password) VALUES(?,?,?,?,?)";
    private static final String REGISTER="INSERT INTO USER(firstName,lastName,emailId,password) VALUES(?,?,?,?)";
    private static final String REGISTRATION_CHECK="SELECT otpVerified from newUser where emailId=?";
    private static final String INSERT_MOBILE_NUMBER="INSERT INTO mobileOtp(mobileNo) Values(?)";

    private static final String GET_USER_BY_EMAIL_ID="SELECT * FROM offer WHERE offerId=?";
    private static final String GET_OFFERS="select offerId,discount,description,photo from offer order by discount desc limit ?,?";
    private static final String GET_OFFERS_OF_RESTAURANT="select offerId,discount,description,photo from offer where brandId is null order by discount desc limit ?,?";
    private static final String ALL_OFFERS="select offerId,discount,description,photo from offer order by discount desc limit ?,?";
    private static final String VIEW_DETAILS_OF_AN_OFFER="select * from offer where offerId=?";
    private static final String GET_BRAND_OFFERS ="select offerId,discount,description,photo from offer where brandId=? limit ?,?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    LocationService locationService;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;


    @Autowired
    AdminServiceImpl adminServiceImpl;

    int lowerLimit = 0;
    int upperLimit = 1;
    @Override
    public UserDetails loadUserByUsername(String emailId) throws UsernameNotFoundException
    {
        String email = jdbcTemplate.queryForObject("select emailId from user where emailId=?", String.class, new Object[]{emailId});
        String password = jdbcTemplate.queryForObject("select password from user where emailId=?", String.class, new Object[]{emailId});
        return new User(email, password, new ArrayList<>());
    }

    public String getUserNameFromToken()
    {
        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails)
        {
            username = ((UserDetails) principal).getUsername();
        }
        else
        {
            username = principal.toString();
        }
        return username;
    }

    @Override
    public boolean addToFavourite(FavTable favTable)
    {
        try
        {
            String email = getUserNameFromToken();
            int id = jdbcTemplate.queryForObject("select userId from user where emailId=?", Integer.class, new Object[]{email});
            jdbcTemplate.update("insert into favTable values(?,?)", id, favTable.getBrandId());
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Map<Integer, List<BrandList>> viewPopularBrands()
    {

        Map<Integer, List<BrandList>> popular = new HashMap<>();
        try
        {
            int brandNo = jdbcTemplate.queryForObject("select brandId from favTable group by brandId order by count(brandId) desc limit ?,?", Integer.class, new Object[]{lowerLimit, upperLimit});
            List<BrandList> brands = jdbcTemplate.query("select brandName, description, logo, profilePic, brandOrigin from brand where brandId=?", new BeanPropertyRowMapper<>(BrandList.class), brandNo);
            lowerLimit = lowerLimit + 1;
            popular.put(brands.size(), brands);
            return popular;
        }
        catch (EmptyResultDataAccessException e)
        {
            e.printStackTrace();
            lowerLimit = lowerLimit - 1;
            int brandNo = jdbcTemplate.queryForObject("select brandId from favTable group by brandId limit ?,?", Integer.class, new Object[]{lowerLimit, upperLimit});
            List<BrandList> brands = jdbcTemplate.query("select brandName, description, logo, profilePic, brandOrigin from brand where brandId=?", new BeanPropertyRowMapper<>(BrandList.class), brandNo);
            popular.put(brands.size(), brands);
            return popular;
        }
    }

    @Override
    public Map<Integer, List<BrandList>> viewAllBrands()
    {
        try
        {
            Map<Integer, List<BrandList>> theThings = new HashMap<>();
            List<BrandList> brandLists = jdbcTemplate.query("select brandName, description, logo, profilePic, brandOrigin from brand", new BeanPropertyRowMapper<>(BrandList.class));
            theThings.put(brandLists.size(), brandLists);
            return theThings;
        }
        catch (Exception e)
        {
            e.printStackTrace();
           return null;
        }
    }

    @Override
    public String addReview(ReviewInfo reviewInfo)
    {
        String email = getUserNameFromToken();
        int id = jdbcTemplate.queryForObject("select userId from user where emailId=?", Integer.class, new Object[]{email});
        try
        {
            int userId = jdbcTemplate.queryForObject("select userId from orders where userId=? group by userId", Integer.class, new Object[]{id});
            int restaurantId = jdbcTemplate.queryForObject("select restaurantId from orders where userId=? group by restaurantId", Integer.class, new Object[]{userId});
            if (restaurantId == reviewInfo.getRestaurantId() || id==userId)
            {
                String query = "insert into review (userId, restaurantId, description, localDate, foodRating, serviceRating) values(?,?,?,?,?,?)";
                jdbcTemplate.update(query, reviewInfo.getUserId(), reviewInfo.getRestaurantId(), reviewInfo.getDescription(), LocalDate.now(), reviewInfo.getFoodRating(), reviewInfo.getServiceRating());
                int reviewId = jdbcTemplate.queryForObject("select max(reviewId) from review where userId=?", Integer.class, new Object[]{reviewInfo.getUserId()});
                ReviewInfo reviewInfo1 = jdbcTemplate.queryForObject("select foodRating, serviceRating from review where reviewId=?", new BeanPropertyRowMapper<>(ReviewInfo.class), reviewId);
                jdbcTemplate.update("update review set averageRating=? where reviewId=?", (reviewInfo1.getFoodRating() + reviewInfo1.getServiceRating()) / 2, reviewId);
                try
                {
                    if (reviewInfo.getPhotoLinks()!=null)
                    {
                        for(int i=0;i<reviewInfo.getPhotoLinks().size();i++)
                        {
                            jdbcTemplate.update("insert into photo (photoPic, reviewId) values(?,?)",reviewInfo.getPhotoLinks().get(i),reviewId);
                        }
                    }

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    return "Review Added Without photo";
                }
            }
            else
            {
                return "You Cant give Review to this Restaurant";
            }
            return "Review Added With Photo";
        }
        catch (Exception e)
        {
            e.printStackTrace();
            try
            {

                jdbcTemplate.update("insert into review(userId, description, serviceRating, orderId, localDate) values(?,?,?,?,?)", id, reviewInfo.getDescription(), reviewInfo.getServiceRating(), reviewInfo.getOrderId(), LocalDate.now());
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
                jdbcTemplate.update("insert into review (userId, serviceRating, orderId, LocalDate) values(?,?,?,?)", id, reviewInfo.getServiceRating(), reviewInfo.getOrderId(), LocalDate.now());
            }
        }
        return "Review added";

    }

    @Override
    public Map<Integer, Object> viewReviews(Restaurant restaurant)
    {
        Map<Integer, Object> reviews = new HashMap<>();
        try
        {
            String query = "select user.userId, user.firstName, user.lastName, user.profilePic, review.reviewId, review.description, review.averageRating, review.likeCount, review.localDate from user inner join review on user.userId=review.userId where review.restaurantId=?";
            List<ReviewPageResponse> reviewPageResponses = new ArrayList<ReviewPageResponse>();
            jdbcTemplate.query(query, (rs, rowNum) ->
            {
                ReviewPageResponse reviewPageResponse = new ReviewPageResponse();
                reviewPageResponse.setUserId(rs.getInt("user.userId"));
                reviewPageResponse.setFirstName(rs.getString("user.firstName"));
                reviewPageResponse.setLastName(rs.getString("user.lastName"));
                reviewPageResponse.setProfilePic(rs.getString("user.profilePic"));
                reviewPageResponse.setReviewId(rs.getInt("review.reviewId"));
                reviewPageResponse.setDescription(rs.getString("review.description"));
                reviewPageResponse.setAverageRating(rs.getInt("review.averageRating"));
                reviewPageResponse.setLikeCount(rs.getInt("review.likeCount"));
                reviewPageResponse.setDate(rs.getDate("review.localDate"));
                reviewPageResponse.setPhoto(getReviewPhotos(rs.getInt("review.reviewId")));
                reviewPageResponse.setReviewCount(giveReviewCount(rs.getInt("user.userId")));
                reviewPageResponse.setRatingCount(giveRatingCount(rs.getInt("user.userId")));
                reviewPageResponses.add(reviewPageResponse);
                reviews.put(reviewPageResponses.size(), reviewPageResponse);
                return reviewPageResponse;
            }, restaurant.getRestaurantId());
            return reviews;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }

    }

    public List<String> getReviewPhotos(int reviewId)
    {
        return jdbcTemplate.queryForList("select photoPic from photo where reviewId=" + reviewId, String.class);
    }

    public int giveReviewCount(int userId)
    {
        return jdbcTemplate.queryForObject("select count(userId) from review where userId=?", Integer.class, new Object[]{userId});
    }

    public int giveRatingCount(int userId)
    {
        int totalFoodRating = jdbcTemplate.queryForObject("select sum(foodRating) from review where userId=?", Integer.class, new Object[]{userId});
        int totalServiceRating = jdbcTemplate.queryForObject("select sum(serviceRating) from review where userId=?", Integer.class, new Object[]{userId});
        int totalRating = totalFoodRating + totalServiceRating;
        return totalRating;
    }

    @Override
    public OrderDetails getOrderDetails(Orders orders)
    {

            String query = "select orders.orderId, orders.cartId, cart.scheduledDate, cart.scheduledTime, cart.restaurantId, restaurant.restaurantName, address.addressDesc from cart inner join orders on orders.cartId=cart.cartId inner join restaurant on orders.restaurantId=restaurant.restaurantId inner join address on orders.addressId=address.addressId where orders.userId=? and orders.orderId=?";
            return jdbcTemplate.queryForObject(query, (rs, rowNum) ->
            {
                OrderDetails orderDetails = new OrderDetails();
                orderDetails.setOrderId(rs.getInt("orders.orderId"));
                orderDetails.setCartId(rs.getInt("orders.cartId"));
                orderDetails.setScheduleDate(rs.getString("cart.scheduledDate"));
                orderDetails.setScheduleTime(rs.getString("cart.scheduledTime"));
                orderDetails.setRestaurantId(rs.getInt("cart.restaurantId"));
                orderDetails.setRestaurantName(rs.getString("restaurant.restaurantName"));
                orderDetails.setDeliveryAddress(rs.getString("address.addressDesc"));
                orderDetails.setDishInfoList(giveDishDetails(rs.getInt("cart.restaurantId"), rs.getInt("orders.cartId")));
                orderDetails.setAmountDetails(provideAmountDetails(rs.getInt("orders.orderId")));
                return orderDetails;
            }, orders.getUserId(), orders.getOrderId());
        }


    public List<DishInfo> giveDishDetails(int restaurantId, int cartId)
    {
        String query = "select item.count, item.addOnCount, item.dishId, menu.price , dish.dishName, dish.veg from item inner join menu on item.dishId=menu.dishId inner join dish on menu.dishId=dish.dishId where menu.restaurantId=? and item.cartId=?";
        List<DishInfo> dishInfo1 = jdbcTemplate.query(query, (rs, rowNum) ->
        {
            DishInfo dishInfo = new DishInfo();
            dishInfo.setDishId(rs.getInt("item.dishId"));
            dishInfo.setCount(rs.getInt("item.count"));
            dishInfo.setAddOnCount(rs.getInt("item.addOnCount"));
            dishInfo.setPrice(rs.getInt("menu.price"));
            dishInfo.setDishName(rs.getString("dish.dishName"));
            dishInfo.setVeg(rs.getBoolean("dish.veg"));
            dishInfo.setAddonInfoList(giveAddOnInfoForDish(restaurantId, dishInfo));
            return dishInfo;
        }, restaurantId, cartId);
        return dishInfo1;
    }

    public List<AddonInfo> giveAddOnInfoForDish(int restaurantId, DishInfo dishInfo)
    {
        String addOn_Query = "select addOn.addOn, addOn.price from addOn inner join addOnMapping on addOn.addOnId=addOnMapping.addOnId where addOnMapping.dishId=? and addOnMapping.restaurantId=?";
        if (dishInfo.getAddOnCount() != 0)
        {
            List<AddonInfo> addonInfoList = jdbcTemplate.query(addOn_Query, (rs, rowNum) ->
            {
                AddonInfo addonInfo = new AddonInfo();
                addonInfo.setAddOn(rs.getString("addOn.addOn"));
                addonInfo.setPrice(rs.getInt("addOn.price"));
                return addonInfo;
            }, dishInfo.getDishId(), restaurantId);
            return addonInfoList;
        }
        else
        {
            return null;
        }
    }

    public AmountDetails provideAmountDetails(int orderId)
    {
        AmountDetails amountDetails = new AmountDetails();
        try
        {
            String query = "select cardNo from payment where orderId=?";
            jdbcTemplate.queryForObject(query, String.class, new Object[]{orderId});
            jdbcTemplate.queryForObject("select amount, taxAmount, discount, grandTotal from payment where orderId=?", (rs, rowNum) ->
            {
                amountDetails.setTotalAmount(rs.getFloat("amount"));
                amountDetails.setTaxAmount(rs.getFloat("taxAmount"));
                amountDetails.setDiscount(rs.getFloat("discount"));
                amountDetails.setAmountPaid(rs.getFloat("grandTotal"));
                amountDetails.setPaymentType("Credit/Debit card");
                return amountDetails;
            }, orderId);
            return amountDetails;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            jdbcTemplate.queryForObject("select amount, taxAmount, discount, grandTotal from payment where orderId=?", (rs, rowNum) ->
            {
                amountDetails.setTotalAmount(rs.getFloat("amount"));
                amountDetails.setTaxAmount(rs.getFloat("taxAmount"));
                amountDetails.setDiscount(rs.getFloat("discount"));
                amountDetails.setAmountPaid(rs.getFloat("grandTotal"));
                amountDetails.setPaymentType("Cash");
                return amountDetails;
            }, orderId);
            return amountDetails;
        }
    }

    @Override
    public boolean addCard(Card card)
    {
        try
        {
            String hash=passwordEncoder.encode(card.getCvv());
            jdbcTemplate.update("insert into card (cardNo, cardName, expiryDate, cvv, userId) values (?,?,?,?,?)",card.getCardNo(),card.getCardName(),card.getExpiryDate(),hash,card.getUserId());
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public Map<Integer,List<Card>> viewCards(com.robosoft.lorem.model.User user)
    {
        try
        {
            Map<Integer, List<Card>> cardInfo = new HashMap<>();
            List<Card> cards = jdbcTemplate.query("select cardNo, cardName, expiryDate from card where userId=?", new BeanPropertyRowMapper<>(Card.class), user.getUserId());
            cardInfo.put(cards.size(),cards);
            return cardInfo;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean editCard(Card card)
    {
        try
        {
            String expiry = jdbcTemplate.queryForObject("select expiryDate from card where cardNo=?", String.class, new Object[]{card.getCardNo()});
            expiry = expiry.substring(3);
            int date = LocalDate.now().getYear();
            String year = Integer.toString(date).substring(2);
            int cardYear = Integer.parseInt(expiry);
            int currentYear = Integer.parseInt(year);
            if (cardYear <= currentYear)
            {
                jdbcTemplate.update("update card set expiryDate=? where cardNo=?", card.getExpiryDate(), card.getCardNo());
                return true;
            }
            else
            {
                return false;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String makeCardPrimary(Card card)
    {
        jdbcTemplate.update("update card set cardType=1 where cardNo= ? and userId=?",card.getCardNo(),card.getUserId());
        jdbcTemplate.update("update card set cardType=0 where cardNo!=? and userId=?",card.getCardNo(),card.getUserId());
        return card.getCardNo()+"selected as primary";
    }

    @Override
    public boolean deleteCard(Card card)
    {
        try
        {
            jdbcTemplate.update("update card set cardDeleted=1 where cardNo=? and userId=?", card.getCardNo(), card.getUserId());
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String  makePayment(Payment payment)
    {
        String email = getUserNameFromToken();
        int id = jdbcTemplate.queryForObject("select userId from user where emailId=?", Integer.class, new Object[]{email});
        try
        {
            String cvv = jdbcTemplate.queryForObject("select cvv from card where cardNo=?", String.class, new Object[]{payment.getCardNo()});
            if (passwordEncoder.matches(payment.getCvv(), cvv))
            {
                jdbcTemplate.update("insert into payment (userId, orderId, amount, promoCode, cardNo, taxAmount, discount, grandTotal) values(?,?,?,?,?,?,?,?)", id, payment.getOrderId(), payment.getAmount(), payment.getPromoCode(), payment.getCardNo(), payment.getTaxAmount(), payment.getDiscount(), payment.getGrandTotal());
                jdbcTemplate.update("update payment set paymentStatus=? where orderId=?","Paid",payment.getOrderId());
                payment.setOrderStatus("orderPlaced");
                jdbcTemplate.update("update orders set orderStatus=? where orderId=?", payment.getOrderStatus(), payment.getOrderId());
                int cartId=jdbcTemplate.queryForObject("select cartId from orders where orderId=?",Integer.class, new Object[]{payment.getOrderId()});
                jdbcTemplate.update("update cart set cartDeleted=1 where cartId=?",cartId);
                return payment.getOrderStatus();
            }
            else
            {
                return "Payment failed";
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            if(payment.getCardNo()==null)
            {
                jdbcTemplate.update("insert into payment (userId, orderId, amount, promoCode, taxAmount, discount, grandTotal) values(?,?,?,?,?,?,?)", id, payment.getOrderId(), payment.getAmount(), payment.getPromoCode(), payment.getTaxAmount(), payment.getDiscount(), payment.getGrandTotal());
                jdbcTemplate.update("update payment set paymentStatus=? where orderId=?", "Not Paid", payment.getOrderId());
                payment.setOrderStatus("orderPlaced");
                jdbcTemplate.update("update orders set orderStatus=? where orderId=?", payment.getOrderStatus(), payment.getOrderId());
                int cartId = jdbcTemplate.queryForObject("select cartId from orders where orderId=?", Integer.class, new Object[]{payment.getOrderId()});
                jdbcTemplate.update("update cart set cartDeleted=1 where cartId=?", cartId);
                return payment.getOrderStatus();
            }
            else
            {
                return "payment failed";
            }
        }
    }

    @Override
    public boolean giveFeedback(FeedBack feedBack)
    {
        try
        {
            String email = getUserNameFromToken();
            int id = jdbcTemplate.queryForObject("select userId from user where emailId=?", Integer.class, new Object[]{email});
            jdbcTemplate.update("insert into feedback (userId, role, feedBackDescription, userName, entityName, contactEmail, contactNo, city, area, category) values (?,?,?,?,?,?,?,?,?,?)", id, feedBack.getRole(), feedBack.getMessage(), feedBack.getName(), feedBack.getEntityName(), feedBack.getContactEmailId(), feedBack.getContactMobileNumber(), feedBack.getEntityCity(), feedBack.getEntityArea(), feedBack.getCategoryType());
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }


    @Value("${page.data.count}")
    private int perPageDataCount;

    @Value("${nearby.distance}")
    private int nearbyDistance;

    String query;


    @Override
    public RestaurantSearchResult searchRestaurant(SearchFilter searchFilter) {


        RestaurantSearchResult restaurantSearchResult = new RestaurantSearchResult();

        List<?> list = this.getOffsetUsingCustomLimit(searchFilter.getPageNumber(),searchFilter.getLimit());

        int limit = (int)list.get(0);
        long offset = (long)list.get(1);


        if(searchFilter.getRestaurantOrFoodType()==null)
            searchFilter.setRestaurantOrFoodType("");

        if(searchFilter.getDate()==null)
            searchFilter.setDate(Date.valueOf(LocalDate.now()));

        if(searchFilter.getAddress()==null)
            searchFilter.setAddress("");


        String selectFields = "SELECT DISTINCT(" +
                "r.restaurantId)," +
                "r.restaurantName," +
                "r.overAllRating," +
                "r.minimumCost," +
                "r.addressId," +
                "r.profilePic," +
                "r.workingHours," +
                "r.cardAccepted," +
                "r.Description," +
                "r.restaurantType," +
                "r.brandId," +
                "r.userId," +
                "a.longitude," +
                "a.lattitude," +
                "o.openingTime," +
                "o.closingTime," +
                "opened," +
                "r.averageCost,"+
                "a.addressDesc,"+
                "r.averageDeliveryTime";


        query = " FROM restaurant r " +
                "inner join menu m " +
                "on r.restaurantId=m.restaurantId " +
                "inner join address a " +
                "on r.addressId=a.addressId " +
                "inner join openinginfo o " +
                "on r.restaurantId=o.restaurantId " +
                "where (r.restaurantName like '%"+searchFilter.getRestaurantOrFoodType()+"%' " +
                "or " +
                "m.foodType like '%"+searchFilter.getRestaurantOrFoodType()+"%') " +
                "and " +
                "o.dateOf='"+searchFilter.getDate()+"' " +
                "and a.addressDesc like '%"+searchFilter.getAddress()+"%' ";


        this.applyFilter(searchFilter);


        if(searchFilter.getPageNumber()==0)
            searchFilter.setPageNumber(1);
        if(searchFilter.getPageNumber()==1){
            String countQuery = "SELECT count(distinct r.restaurantId)";
            long count=0;
            System.out.println(countQuery+query);
            try{
                count= jdbcTemplate.queryForObject(countQuery+query, Long.class);
            }catch (EmptyResultDataAccessException emptyResultDataAccessException){
                restaurantSearchResult.setTotalRocordsCount((Long.valueOf(0)));
                return restaurantSearchResult;

            }

            restaurantSearchResult.setTotalRocordsCount(count);


        }




        query = selectFields+query+"limit "+offset+","+limit;

        System.out.println(query);

        List<RestaurantSearchModel> restaurants =  jdbcTemplate.query(query,(rs,noOfROws)->{

            RestaurantSearchModel restaurantSearchModel = new RestaurantSearchModel();
            restaurantSearchModel.setRestaurantId(rs.getInt(1));
            restaurantSearchModel.setRestaurantName(rs.getString(2));
            restaurantSearchModel.setOverAllRating(rs.getDouble(3));
            restaurantSearchModel.setMinimumCost(rs.getDouble(4));
            restaurantSearchModel.setAddressId(rs.getInt(5));
            restaurantSearchModel.setProfilePic(rs.getString(6));
            restaurantSearchModel.setWorkingHours(rs.getString(7));
            restaurantSearchModel.setCardAccepted(rs.getBoolean(8));
            restaurantSearchModel.setDescription(rs.getString(9));
            restaurantSearchModel.setRestaurantType(rs.getString(10));
            restaurantSearchModel.setBrandId(rs.getInt(11));
            restaurantSearchModel.setUserId(rs.getInt(12));

            Location restaurantLocation = new Location(rs.getDouble(13), rs.getDouble(14));

            restaurantSearchModel.setLocation(restaurantLocation);
            restaurantSearchModel.setOpeningTime(rs.getString(15));
            restaurantSearchModel.setClosingTime(rs.getString(16));
            restaurantSearchModel.setOpened(rs.getBoolean(17));
            restaurantSearchModel.setAvgMealCost(rs.getDouble(18));
            long duration =0;
            if(searchFilter.getLocation()!=null)
                duration = locationService.getDuration(searchFilter.getLocation(),restaurantLocation);
            restaurantSearchModel.setDeliveryTime(duration);
            restaurantSearchModel.setAddressDesc(rs.getString(19));
            restaurantSearchModel.setAverageDeliveryTime(rs.getDouble(20));

            return restaurantSearchModel;
        });

        restaurantSearchResult.setPerPageRecordsCount(restaurants.size());

        restaurantSearchResult.setPageResults(restaurants);

        return restaurantSearchResult;
    }

    public void applyFilter(SearchFilter searchFilter){
        if(searchFilter.isOpenNow())
            query = query+"and o.opened=true ";

        if(searchFilter.getMaxAvgMealCost()>0)
            query=query+"and r.averageCost<="+searchFilter.getMaxAvgMealCost()+" ";

        if(searchFilter.getMaxMinOrderCost()>0)
            query = query+"and r.minimumCost<="+searchFilter.getMaxMinOrderCost()+" ";

        if(searchFilter.getCuisineType()!=null)
            query= query+" and r.restaurantType like '%"+searchFilter.getCuisineType()+"%' ";

        if(searchFilter.getDeliveryTime()!=0)
            query=query+" and r.averageDeliveryTime<="+searchFilter.getDeliveryTime()+" ";

        if(searchFilter.getBrandId()>0)
            query=query+" and r.brandId="+searchFilter.getBrandId()+" ";

        //havershine formulae
        //( 6371 * acos( cos( radians(fromLat) ) * cos( radians( lat ) )
        //* cos( radians( lng ) - radians(fromLang) ) + sin( radians(fromLat) ) * sin(radians(lat)) ) )
        if(searchFilter.getLocation()!=null)
            query=query+" and ( 6371 * acos( cos( radians("+searchFilter.getLocation().getLatitude()+") ) * cos( radians( a.lattitude ) ) * cos( radians( a.longitude ) - radians("+searchFilter.getLocation().getLongitude()+") ) + sin( radians("+searchFilter.getLocation().getLatitude()+") ) * sin( radians( a.lattitude ) ) ) )<"+nearbyDistance;

        if(!searchFilter.isDescRating())
            query=query+" order by r.overAllRating asc ";
        else
            query=query+" order by r.overAllRating desc ";
    }


    public double getAverageMealCostForRestaurant(int restaurantId){
        return jdbcTemplate.queryForObject("select avg(price) from menu where restaurantId="+restaurantId, Double.class);
    }

    public long getOffset(int pageNumber){
        if(pageNumber<1)
            pageNumber=1;

        return (long)perPageDataCount*(pageNumber-1);
    }

    public List<?> getOffsetUsingCustomLimit(int pageNumber,int limit){

        List list = new ArrayList();

        if(pageNumber<1)
            pageNumber=1;

        if(limit<1)
            limit=perPageDataCount;

        list.add(limit);
        list.add( (long)limit*(pageNumber-1));


        return list;

    }



    @Override
    public NearByBrandsSearchResult getNearbyBrands(Location location, int pageNumber,int limit){
        List<?> list = this.getOffsetUsingCustomLimit(pageNumber,limit);

        limit = (int)list.get(0);
        long offset = (long)list.get(1);

        String startQuery="select count(distinct r.brandId) from brand b inner join restaurant r on b.brandId=r.brandId inner join address a on r.addressId=a.addressId where ";

        query="( 6371 * acos( cos( radians("+location.getLatitude()+") ) * cos( radians( a.lattitude ) ) * cos( radians( a.longitude ) - radians("+location.getLongitude()+") ) + sin( radians("+location.getLatitude()+") ) * sin( radians( a.lattitude ) ) ) )<"+nearbyDistance;


        NearByBrandsSearchResult nearByBrandsSearchResult = new NearByBrandsSearchResult();

        long count =0;

        if(pageNumber<=1 && pageNumber>=0){
            System.out.println(startQuery+query);
            try {
                count = jdbcTemplate.queryForObject(startQuery + query, Long.class);
                nearByBrandsSearchResult.setTotalResultsCount(count);
            }catch (EmptyResultDataAccessException emptyResultDataAccessException){
                nearByBrandsSearchResult.setTotalResultsCount(count);
                return nearByBrandsSearchResult;
            }
        }



        startQuery="select distinct b.brandId,b.brandName,b.description,b.logo,b.profilePic,b.brandOrigin from brand b inner join restaurant r on b.brandId=r.brandId inner join address a on r.addressId=a.addressId where ";
        query = startQuery+query+" limit "+offset+","+limit;

        System.out.println(query);
        List<BrandSearchModel> nearByBrands =  jdbcTemplate.query(query,(rs, noOfRows)->{
            BrandSearchModel brandSearchModel = new BrandSearchModel();
            brandSearchModel.setBrandId(rs.getInt(1));
            brandSearchModel.setBrandName(rs.getString(2));
            brandSearchModel.setDescription(rs.getString(3));
            brandSearchModel.setLogo(rs.getString(4));
            brandSearchModel.setProfilePic(rs.getString(5));
            brandSearchModel.setBrandOrigin(rs.getString(6));

            return brandSearchModel;
        });

        nearByBrandsSearchResult.setPageResultsCount(nearByBrands.size());
        nearByBrandsSearchResult.setNearByBrands(nearByBrands);

        return nearByBrandsSearchResult;
    }

    @Override
    public CartModel saveOrUpdateCart(CartModel cartModel) {
        //check if cart is an existing cart then delete its items

        int cartId;


        //if update operation
        if(cartModel.getCartId()!=null){
            this.deleteCartItems(cartModel.getCartId());
            cartId = this.updateCart(cartModel);

            if(cartId==-1)
                return null;
        }
        //if it's a new cart then create it in the database and get the id
        else {
            cartId = this.createCart(cartModel);
        }
        //add items of cart into item table
        query = "insert into item(dishId,cartId,addOnCount,count,customizable) values(?,?,?,?,?)";
        for(ItemModel item:cartModel.getItemsIncart()){
            this.addItemIntoCart(item,cartId,query);
        }

        cartModel.setCartId(cartId);

        return cartModel;

    }

    //add item into item table
    public boolean addItemIntoCart(ItemModel itemModel,int cartId,String query){
        jdbcTemplate.update(query,(preparedStatement)->{
            preparedStatement.setInt(1,itemModel.getDishId());
            preparedStatement.setInt(2,cartId);
            preparedStatement.setInt(3,itemModel.getAddOnCount());
            preparedStatement.setInt(4,itemModel.getItemCount());
            preparedStatement.setString(5,itemModel.getCustomizationInfo());
        });

        return true;
    }



    //delete all items from cart
    public boolean deleteCartItems(int cartId){
        query = "delete from item where cartId="+cartId;

        jdbcTemplate.update(query);

        return true;

    }

    //create a cart in the db using userId and fetch cart id
    public int createCart(CartModel cartModel){
        query = "insert into cart(userId,cookingInstructions,scheduledDate,scheduledTime,totalAmount,restaurantId) values(?,?,?,?,?,?)";

        jdbcTemplate.update(query,(preparedStatement)->{
            preparedStatement.setInt(1,cartModel.getUserId());
            preparedStatement.setString(2, cartModel.getCookingInstruction());
            preparedStatement.setDate(3,cartModel.getScheduleDate());
            preparedStatement.setTime(4,cartModel.getScheduleTime());
            preparedStatement.setDouble(5,cartModel.getToPay());
            preparedStatement.setInt(6,cartModel.getRestaurantId());
        });
        query = "select max(cartId) from cart where userId="+cartModel.getUserId();

        return jdbcTemplate.queryForObject(query,Integer.class);
    }


    //update a cart in the db
    public int updateCart(CartModel cartModel){
        query = "update cart set cookingInstructions=?,totalAmount=? where cartId=? and userId=? and cartDeleted=false";

        int updateCount = jdbcTemplate.update(query,(preparedStatement)->{
            preparedStatement.setString(1,cartModel.getCookingInstruction());
            preparedStatement.setDouble(2,cartModel.getToPay());
            preparedStatement.setInt(3,cartModel.getCartId());
            preparedStatement.setInt(4,cartModel.getUserId());
        });

        if(updateCount<1)
            return -1;

        return cartModel.getCartId();
    }


    //like or unlike a review
    @Override
    public boolean likeAreview(int userId, int reviewId) {
        query = "insert into likes values("+userId+","+reviewId+")";

        try{
            jdbcTemplate.update(query);
        }catch(DuplicateKeyException exception){
            query = "delete from likes where userID="+userId+" and reviewId="+reviewId;

            jdbcTemplate.update(query);

            query = "update review set likeCount=likeCount-1 where reviewId="+reviewId;

            return false;
        }

        query = "update review set likeCount=likeCount+1 where reviewId="+reviewId;

        jdbcTemplate.update(query);

        return true;
    }


    //get user profile using userId
    @Override
    public UserProfile getUserProfile(int userId) {
        query = "select userId,firstName,lastName,emailId,mobileNo,profilePic,creditScore from user where userId="+userId;

        UserProfile userProfile;

        try {
            userProfile = jdbcTemplate.queryForObject(query, (rs, noOfRows) -> {
                UserProfile returningUserProfile = new UserProfile();


                returningUserProfile.setUserId(rs.getInt(1));
                returningUserProfile.setFirstName(rs.getString(2));
                returningUserProfile.setLastName(rs.getString(3));
                returningUserProfile.setEmail(rs.getString(4));
                returningUserProfile.setMobileNumber(rs.getString(5));
                returningUserProfile.setProfilePicURL(rs.getString(6));
                returningUserProfile.setCreditScore(rs.getInt(7));


                return returningUserProfile;
            });
        }catch(DataAccessException dataAccessException){
            return null;
        }


        if(userProfile.getMobileNumber()!=null){
            query = "select otpVerified from mobileotp where mobileNo="+userProfile.getMobileNumber();

            userProfile.setMobileVerified(jdbcTemplate.queryForObject(query, Boolean.class));
        }

        return userProfile;

    }



    //get orders  of a user using userId and order status

    @Override
    public OrderResponseModel getMyOrdersByStatus(String orderStatus, int userId,int pageNumber,int limit) {

        OrderResponseModel orderResponseModel = new OrderResponseModel();


        int orderIndex = this.getStatusIndex(orderStatus);
        List<?> list = this.getOffsetUsingCustomLimit(pageNumber,limit);

        limit = (int)list.get(0);
        long offset = (long)list.get(1);


        //for any other status
        if(orderIndex==9)
            return null;

        String startingQuery = "select orderId,orderStatus,cartId,restaurantId";
        query = " from orders where ";
        //for active
        if(orderIndex==6)
            query = query+"orderStatus<="+orderIndex+" and orderStatus is not null and userId="+userId;

            //for cancelled or past
        else
            query = query+"orderStatus="+orderIndex+" and userId="+userId;

        if(pageNumber==1){
            int count = jdbcTemplate.queryForObject("select count(orderId)"+query, Integer.class);
            orderResponseModel.setTotalRecordsCount(count);

            if(count==0)
                return orderResponseModel;
        }

        query=startingQuery+query+" limit "+offset+","+limit;

        List<OrderModel> orders = this.getOrdersUsingQuery(query);

        orderResponseModel.setOrders(orders);
        orderResponseModel.setTotalRecordsInPage(orders.size());

        return orderResponseModel;
    }


    //get order status index using order status
    public int getStatusIndex(String status){
        switch (status.toUpperCase()){
            case "ACTIVE":{return 6;}
            case "PAST":{return 7;}
            case "CANCELLED":{return 8;}
            default : {return 9;}
        }
    }

    //fetch list of orders using a query
    public List<OrderModel> getOrdersUsingQuery(String query){
        return jdbcTemplate.query(query,(resultSet,noOfRows)->{
            OrderModel orderModel = new OrderModel();

            //get order id and status
            orderModel.setOrderId(resultSet.getInt(1));
            orderModel.setOrderStatus(resultSet.getString(2));
            orderModel.setItemsCount(jdbcTemplate.queryForObject("select count(cartId) from item where cartId="+resultSet.getInt(3), Integer.class));

            //get restaurant name and restaurant address
            RestaurantSearchModel restaurantSearchModel = jdbcTemplate.queryForObject("select r.restaurantName,a.addressDesc from restaurant r inner join address a on r.addressId=a.addressId where r.restaurantId="+resultSet.getInt(4),(rs,no)->{
                RestaurantSearchModel returningRestaurantSearchModel = new RestaurantSearchModel();

                returningRestaurantSearchModel.setRestaurantName(rs.getString(1));
                returningRestaurantSearchModel.setAddressDesc(rs.getString(2));
                return returningRestaurantSearchModel;
            });

            orderModel.setRestaurantName(restaurantSearchModel.getRestaurantName());
            orderModel.setRestaurantAddress(restaurantSearchModel.getAddressDesc());


            try {
                //get grand total
                Double amount = jdbcTemplate.queryForObject("select grandTotal from payment where orderId=" + orderModel.getOrderId(), Double.class);
                orderModel.setAmount(amount);
            }catch (Exception exception) {
                try {
                    orderModel.setAmount(jdbcTemplate.queryForObject("select amount from payment where orderId=" + orderModel.getOrderId(), Integer.class));
                }catch(Exception e){
                    orderModel.setAmount(0);
                }
            }

            return orderModel;
        });
    }


    //get a carts list
    @Override
    public CartsResponseModel getMyCarts(int userId, int pageNumber,int limit) {
        List<?> list = this.getOffsetUsingCustomLimit(pageNumber,limit);

        limit = (int)list.get(0);
        long offset = (long)list.get(1);


        CartsResponseModel cartsResponseModel = new CartsResponseModel();


        query = " from cart where userId="+userId+" and cartDeleted=false";

        if(pageNumber==1){
            long count = jdbcTemplate.queryForObject("select count(cartId)"+query, Long.class);
            cartsResponseModel.setTotalResultCount(count);

            if(count==0)
                return cartsResponseModel;
        }

        query = "select cartId,restaurantId,totalAmount"+query+" limit "+offset+","+limit;


        List<CartModel> carts = jdbcTemplate.query(query,(resultSet,noOfRows)->{
            CartModel cartModel = new CartModel();

            //fetch cartId,toPay and restaurantId
            cartModel.setCartId(resultSet.getInt(1));
            int restaurantId = resultSet.getInt(2);
            cartModel.setToPay(resultSet.getDouble(3));

            //fetch count of items in cart using cart id
            cartModel.setCountOfItems(jdbcTemplate.queryForObject("select count(cartId) from item where cartId="+cartModel.getCartId(), Integer.class));

            //fetch restaurant name and address
            RestaurantSearchModel restaurantSearchModel = jdbcTemplate.queryForObject("select r.restaurantName,a.addressDesc from restaurant r inner join address a on r.addressId=a.addressId where r.restaurantId="+restaurantId,(rs,no)->{
                RestaurantSearchModel returnedRestaurantSearchModel = new RestaurantSearchModel();

                returnedRestaurantSearchModel.setRestaurantName(rs.getString(1));
                returnedRestaurantSearchModel.setAddressDesc(rs.getString(2));

                return returnedRestaurantSearchModel;
            });

            //set restaurant name and address
            cartModel.setRestaurantName(restaurantSearchModel.getRestaurantName());
            cartModel.setRestaurantAddress(restaurantSearchModel.getAddressDesc());

            return cartModel;
        });

        cartsResponseModel.setCarts(carts);
        cartsResponseModel.setPerPageCount(carts.size());

        return cartsResponseModel;
    }


    @Override
    public boolean removeCart(int userId, int cartId) {
        query = "delete from cart where cartId=? and userId=? and cartDeleted=false";

        int deleted = jdbcTemplate.update(query,(preparedStatement)->{
            preparedStatement.setInt(1,cartId);
            preparedStatement.setInt(2,userId);
        });

        if(deleted>0)
            return true;


        return false;
    }


    //get cart by id
    @Override
    public CartModel getCartById(CartModel cartModel){
        //fetch restaurant id ,cart id ,total amount,cooking instruction,restaurant name ,address desc and long lat using restaurant id from the cart table using cart id and user id

        int userId = cartModel.getUserId();
        int cartId = cartModel.getCartId();
        Location userLocation = cartModel.getLocation();
        query = "select c.restaurantId,c.cartId,c.totalAmount,c.cookingInstructions,r.restaurantName,a.addressDesc,a.longitude,a.lattitude from cart c inner join restaurant r on c.restaurantId=r.restaurantId inner join address a on r.addressId=a.addressId where c.cartId="+cartId+" and c.userId="+userId+" and c.cartDeleted=false";

        try {

            cartModel = jdbcTemplate.queryForObject(query, (resultSet, noOfRows) -> {
                CartModel returningCartModel = new CartModel();
                returningCartModel.setRestaurantId(resultSet.getInt(1));
                returningCartModel.setCartId(resultSet.getInt(2));
                returningCartModel.setToPay(resultSet.getDouble(3));
                returningCartModel.setCookingInstruction(resultSet.getString(4));
                returningCartModel.setRestaurantName(resultSet.getString(5));
                returningCartModel.setRestaurantAddress(resultSet.getString(6));
                Location location = new Location(resultSet.getDouble(7), resultSet.getDouble(8));
                returningCartModel.setLocation(location);
                returningCartModel.setDeliveryDuration(locationService.getDuration(userLocation, location));

                return returningCartModel;
            });

        }catch(DataAccessException dataAccessException){
            return null;
        }

        cartModel.setCartId(cartId);
        cartModel.setUserId(userId);



        //fetch item details from item table
        query = "select i.dishId,i.count,i.addOnCount,i.customizable,c.restaurantId from item i inner join cart c on i.cartId=c.cartId where i.cartId="+cartModel.getCartId();
        List<ItemModel> items = jdbcTemplate.query(query,(resultSet,noOfRows)->{
            ItemModel itemModel = new ItemModel();
            itemModel.setDishId(resultSet.getInt(1));
            itemModel.setItemCount(resultSet.getInt(2));
            itemModel.setAddOnCount(resultSet.getInt(3));
            itemModel.setCustomizationInfo(resultSet.getString(4));

            int restaurantId = resultSet.getInt(5);

            //fetch details of the menu item
            ItemModel dishDetails = this.getDishDetails(itemModel.getDishId(), restaurantId);
            itemModel.setDishName(dishDetails.getDishName());
            itemModel.setVeg(dishDetails.isVeg());
            itemModel.setPrice(dishDetails.getPrice());
            itemModel.setCustomizable(dishDetails.isCustomizable());


            //fetch addons for an item
            itemModel.setAddOns(this.getAddons(itemModel.getDishId(),restaurantId));

            return itemModel;
        });

        cartModel.setItemsIncart(items);

        return cartModel;
    }


    //get addons using dish and restaurant id
    public List<Addon> getAddons(int dishId, int restaurantId) {
        return jdbcTemplate.query("select addon.addOnId,addon,price from addon inner join addonmapping on addon.addOnId=addonmapping.addOnId where dishId=" + dishId + " and restaurantId=" + restaurantId, (rs, nos) -> {
            Addon addon = new Addon();
            addon.setAddOnId(rs.getInt(1));
            addon.setAddon(rs.getString(2));
            addon.setPrice(rs.getFloat(3));

            return addon;
        });
    }

    //get dish details using dish and restaurant id
    public ItemModel getDishDetails(int dishId,int restaurantId){
        return jdbcTemplate.queryForObject("select d.dishName,d.veg,m.price,m.customizable from dish d inner join menu m on d.dishId=m.dishId where m.dishId="+dishId+" and m.restaurantId="+restaurantId,(rs,no)->{
            ItemModel itemModel = new ItemModel();
            itemModel.setDishName(rs.getString(1));
            itemModel.setVeg(rs.getBoolean(2));
            itemModel.setPrice(rs.getDouble(3));
            itemModel.setCustomizable(rs.getBoolean(4));

            return itemModel;
        });
    }


    long offset = 0;
    int limit = 3;

    public long getOffsets(int pageNumber) {
        return (long) limit * (pageNumber - 1);
    }

    public List<Integer> deliveryRatings(int restaurantId) {
        query = "select serviceRating from review where restaurantId=" + restaurantId + " limit 5";
        return jdbcTemplate.queryForList(query, int.class);

    }

    @Override
    public RestaurantDetails viewRestaurant(int restaurantId, Location start) {
        query = "select restaurantName,profilePic,restaurantType,overAllRating,minimumCost,workingHours,longitude,lattitude,restaurantId from restaurant rs inner join address a on rs.addressId=a.addressId where rs.restaurantId=" + restaurantId;
        return jdbcTemplate.queryForObject(query, (resultSet, no) ->
        {
            RestaurantDetails restaurantDetails = new RestaurantDetails();


            restaurantDetails.setRestaurantName(resultSet.getString(1));
            restaurantDetails.setProfilePicLink(resultSet.getString(2));
            restaurantDetails.setRestaurantType(resultSet.getString(3));
            restaurantDetails.setOverAllRating(resultSet.getInt(4));
            restaurantDetails.setMinimumCost(resultSet.getDouble(5));
            restaurantDetails.setWorkingHours(resultSet.getString(6));
            restaurantDetails.setDeliveryRating(deliveryRatings(restaurantId));
            restaurantDetails.setDuration(locationService.getDuration(start, new Location(resultSet.getDouble(7), resultSet.getDouble(8))));
            restaurantDetails.setRestaurantId(restaurantId);
            return restaurantDetails;
        });

    }

    @Override
    public List<MenuDetails> menuDetails(int restaurantId, String dishType, String dishName, int pageNo) {
        offset = this.getOffsets(pageNo);

        query = "select dishName,price,customizable,description,dishPhoto,veg,menu.dishId from menu inner join dish on menu.dishId = dish.dishId where restaurantId=" + restaurantId + " and dishType like '%" + dishType + "%' and dishName like '%" + dishName + "%'";
        List<MenuDetails> menuDetails = new ArrayList<MenuDetails>();
        jdbcTemplate.query(query, (resultSet, no) ->
        {
            MenuDetails menu = new MenuDetails();
            menu.setDishName(resultSet.getString(1));
            menu.setPrice(resultSet.getFloat(2));
            menu.setCustomizable(resultSet.getBoolean(3));
            menu.setDescription(resultSet.getString(4));
            menu.setDishPhoto(resultSet.getString(5));
            menu.setVeg(resultSet.getBoolean(6));
            menu.setDishId(resultSet.getInt(7));
            menu.setAddonList(this.getAddons(menu.getDishId(),restaurantId));
            menuDetails.add(menu);
            return menu;
        });

        System.out.println(menuDetails);
        return menuDetails;
    }

    public List<String> dishTypes(int restaurantId) {
        String selectQuery = "select distinct dishType from dish inner join menu on dish.dishId=menu.dishId where restaurantId=" + restaurantId;
        List<String> dishTypes = jdbcTemplate.queryForList(selectQuery, String.class);
        return dishTypes;
    }

    @Override
    public List<MenuItem> DisplayMenu(int restaurantId, int pageNO) {
        List<MenuItem> menuItems = new ArrayList<MenuItem>();
        List<String> dishTypes = dishTypes(restaurantId);
        for (String dishType : dishTypes) {
            MenuItem menuItem = new MenuItem(dishType);
            List<MenuDetails> menuDetails = menuDetails(restaurantId, dishType, "", pageNO);
            menuItem.setMenuDetailsList(menuDetails);
            menuItem.setCount(menuDetails.size());
            menuItems.add(menuItem);
        }
        return menuItems;
    }

    @Override
    public MenuItems DisplayMenuItems(int restaurantId, int pageNo) {
        MenuItems menuItems = new MenuItems();
        menuItems.setRestaurantId(restaurantId);
        menuItems.setMenuItem(DisplayMenu(restaurantId,pageNo));
        return menuItems;
    }

    @Override
    public List<MenuItem> searchItem(int restaurantId, String dishName, int pageNo) {
        List<MenuItem> menuItems = new ArrayList<MenuItem>();
        List<String> dishTypes = dishTypes(restaurantId);
        for (String dishType : dishTypes) {
            MenuItem menuItem = new MenuItem(dishType);
            List<MenuDetails> menuDetails = menuDetails(restaurantId, dishType, dishName, pageNo);
            menuItem.setMenuDetailsList(menuDetails);
            menuItem.setCount(menuDetails.size());
            menuItems.add(menuItem);
        }
        return menuItems;
    }


    @Override
    public OverviewDetails overview(int restaurantId) {
        query = " select restaurantId,Description,restaurantType,averageCost,cardAccepted,mobileNo,addressDesc from restaurant inner join address on address.addressId=restaurant.addressId inner join user on restaurant.userId=user.userId where restaurantId=" + restaurantId;
        String openQuery = "select opened,dateOf,openingTime,closingTime,reason from openingInfo where restaurantId=" + restaurantId + " and dateOf='" + LocalDate.now() + "'";
        OverviewDetails overviewDetails = jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<>(OverviewDetails.class));

        List<OpeningDetails> openingDetails = jdbcTemplate.query(openQuery, new BeanPropertyRowMapper<>(OpeningDetails.class));
        if (overviewDetails != null) {
            overviewDetails.setOpeningDetails(openingDetails);
        }

        return overviewDetails;
    }


    public boolean isOwner(int restaurantId, int userId) {
        query = "select userId from restaurant where restaurantId=" + restaurantId;
        int returnedUserId = jdbcTemplate.queryForObject(query, Integer.class);
        return userId == returnedUserId;
    }


    @Override
    public boolean addOpeningInfo(OpeningInfo openingInfo, int restaurantId, int userId) throws IOException {
        try {
            if (isOwner(restaurantId,this.getUserIdFromEmail())) {
                query = "insert into openingInfo(restaurantId,opened,dateOf,openingTime,closingTime,reason) values(?,?,?,?,?,?)";
                jdbcTemplate.update(query,
                        (preparedStatement -> {
                            preparedStatement.setInt(1, restaurantId);
                            preparedStatement.setBoolean(2, openingInfo.isOpened());
                            preparedStatement.setDate(3, openingInfo.getDateOf());
                            preparedStatement.setString(4, null);
                            preparedStatement.setString(5, null);
                            if (openingInfo.isOpened() == true) {
                                preparedStatement.setString(4, openingInfo.getOpeningTime());
                                preparedStatement.setString(5, openingInfo.getClosingTime());
                            }
                            if (openingInfo.getReason() != null) {
                                preparedStatement.setString(6, openingInfo.getReason());
                            }
                        }));
                return true;
            }
            return false;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public OpeningDetails opening(int restaurantId) {
        String openQuery = "select opened,dateOf,openingTime,closingTime,reason from openingInfo where restaurantId=" + restaurantId + " and dateOf='" + LocalDate.now() + "'";
        return jdbcTemplate.queryForObject(openQuery, (resultSet, no) ->
        {
            OpeningDetails openingDetails = new OpeningDetails();

            openingDetails.setOpened(resultSet.getBoolean(1));
            openingDetails.setDateOf(resultSet.getDate(2));
            openingDetails.setOpeningTime(resultSet.getString(3));
            openingDetails.setClosingTime(resultSet.getString(4));
            openingDetails.setReason(resultSet.getString(5));


            return openingDetails;
        });
    }


    @Override
    public List<OpeningDetails> openingsFor7Days(int restaurantId) {
        String opensQuery = "select opened,dateOf,openingTime,closingTime,reason from openingInfo where restaurantId=" + restaurantId + " and (dateOf>='" + LocalDate.now() + "' and dateOf<'" + LocalDate.now().plusDays(7) + "')";
        return jdbcTemplate.query(opensQuery, (resultSet, no) ->
        {
            OpeningDetails openingDetails = new OpeningDetails();


            openingDetails.setOpened(resultSet.getBoolean(1));
            openingDetails.setDateOf(resultSet.getDate(2));
            openingDetails.setOpeningTime(resultSet.getString(3));
            openingDetails.setClosingTime(resultSet.getString(4));
            openingDetails.setReason(resultSet.getString(5));


            return openingDetails;
        });

    }


    @Override
    public boolean addAddress(Address address) throws IOException {
        try {
//            userId have to take when user logged in
            query = "insert into address(userId,primaryAddress,addressType,city,area,addressDesc,lattitude,longitude) values(?,?,?,?,?,?,?,?)";
            jdbcTemplate.update(query,this.getUserIdFromEmail(), address.isPrimaryAddress(), address.getAddressType(), address.getCity(), address.getArea(), address.getAddressDescription(), address.getLattitude(), address.getLongitude());
            return true;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public boolean editAddress(Address address, int addressId, int userId) {
        try {
            if (isUser(addressId, userId)) {
                if (address.getAddressType() != null) {
                    jdbcTemplate.update("update address set addressType='" + address.getAddressType() + "' where addressId=" + addressId);
                    return true;
                }
                if (address.getCity() != null) {
                    jdbcTemplate.update("update address set city='" + address.getCity() + "' where addressId=" + addressId);
                    return true;
                }
                if (address.getArea() != null) {
                    jdbcTemplate.update("update address set area='" + address.getArea() + "' where addressId=" + addressId);
                    return true;
                }
                if (address.getAddressDescription() != null) {
                    jdbcTemplate.update("update address set addressDesc='" + address.getAddressDescription() + "' where addressId=" + addressId);
                    return true;
                }
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }


    @Override
    public boolean deleteAddress(int addressId, int userId) {
        if (isUser(addressId, userId)) {
            query = "update address set addressDeleted = true  where addressId=" + addressId;
            jdbcTemplate.update(query);
            return true;
        }
        return false;
    }

    public boolean isUser(int addressId, int userId) {
        query = "select userId from address where addressId=" + addressId +" and addressDeleted=false";
        try {
            int returnedUserId = jdbcTemplate.queryForObject(query, Integer.class);
            return userId == returnedUserId;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public boolean setPrimaryAddress(int addressId, int userId) {
        if (isUser(addressId, userId)) {
            jdbcTemplate.update("update address set primaryAddress =" + false + " where primaryAddress=" + true + " and userId=" + userId);
            jdbcTemplate.update("update address set primaryAddress=" + true + " where addressId=" + addressId);
            return true;
        }
        return false;

    }


    @Override
    public List<AddressDetails> displayAddress(int userId) {
        query = "select addressId,addressType,primaryAddress,addressDesc from address where userId=" + userId +" and addressDeleted = false";
        try {
            List<AddressDetails> addressDetails = new ArrayList<AddressDetails>();
            jdbcTemplate.query(query, (resultSet, no) ->
            {
                AddressDetails address = new AddressDetails();
                address.setAddressId(resultSet.getInt(1));
                address.setAddressType(resultSet.getString(2));
                address.setPrimaryAddress(resultSet.getBoolean(3));
                address.setAddressDesc(resultSet.getString(4));
                addressDetails.add(address);
                return address;
            });
            return addressDetails;

        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public AddressDesc displayAddresses(int userId) {
        AddressDesc addressDesc = new AddressDesc();
        List<AddressDetails> details = displayAddress(userId);
        addressDesc.setAddressDetailsList(details);
        addressDesc.setCount(details.size());
        if (details.size() <= 0) {
            addressDesc.setCount(0);
            addressDesc.setAddressDetailsList(null);

        }
        return addressDesc;
    }

    public String getUserName(int userId) {
        return jdbcTemplate.queryForObject("select firstName from user where userId=" + userId, String.class);
    }

    public String getMobileNumber(int userId) {
        return jdbcTemplate.queryForObject("select mobileNo from user where userId=" + userId, String.class);
    }


    public Boolean checkUserId(int cartId, int userId) {
        try {
            jdbcTemplate.queryForObject("select userId from cart where cartId=" + cartId + " and userId=" + userId, Integer.class);
            return true;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean checkAddress(int addressId,int userId){
        try{
            jdbcTemplate.queryForObject("select addressId from address where addressId="+addressId+" and userId="+userId, Integer.class);
            return true;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean checkRestaurantId(int restaurantId,int userId,int cartId){
        try{
            jdbcTemplate.queryForObject("select restaurantId from cart where restaurantId="+restaurantId+" and userId="+userId+" and cartId="+cartId, Integer.class);
            return true;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int selectRestaurantId(int orderId)
    {
        query="select restaurantId from orders where orderId="+orderId;
        try {
            return jdbcTemplate.queryForObject(query, Integer.class);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public Boolean updateOrderStatus(Orders orders) {
        try {
            System.out.println("checking ownership...");
            if(!isOwner(selectRestaurantId(orders.getOrderId()),this.getUserIdFromEmail()))
            {
                System.out.println("not an owner..");
                return false;
            }
            int orderStatusIndex = this.getOrderStatusIndex(orders.getOrderStatus());
            query = "update orders set orderStatus=" + orderStatusIndex + " where orderId=" + orders.getOrderId() + " and orderStatus<"+orderStatusIndex;
            if(jdbcTemplate.update(query)>0){
                System.out.println("successfull order status change..");
                return true;
            }
            System.out.println("updation failed..");
            return false;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getOrderStatusIndex(String orderStatus){
        switch (orderStatus.toUpperCase()){
            case "ORDER_PLACED":{return 1;}
            case "ORDER_ACCEPTED":{return 2;}
            case "ORDER_IN_KITCHEN":{return 3;}
            case "ORDER_OUT_FOR_DELIVERY":{return 4;}
            case "ORDER_WAITING_FOR_DELIVERY":{return 5;}
            case "ORDER_READY_FOR_PICKUP":{return 6;}
            case "ORDER_DELIVERED":{return 7;}
            case "ORDER_CANCELLED":{return 8;}
            default:{return 9;}
        }
    }



    //    Api when user click on choosePayment button

    @Override
    public OrderInfo chooseAddress(Orders orders, int userId,Location start) throws IOException {
        query = "insert into orders(userId,restaurantId,orderType,addressId,contactName,contactNo,deliveryInstructions,cartId) values (?,?,?,?,?,?,?,?)";
        OrderInfo orderInfo = new OrderInfo();
        if (checkUserId(orders.getCartId(), userId)) {
            try {
                jdbcTemplate.update(query, (preparedStatement) -> {
                    preparedStatement.setInt(1, userId);
                    if(checkRestaurantId(orders.getRestaurantId(),userId,orders.getCartId())) {
                        preparedStatement.setInt(2, orders.getRestaurantId());
                    }
                    preparedStatement.setString(3, orders.getOrderType());
                    if(checkAddress(orders.getAddressId(),userId)) {
                        preparedStatement.setInt(4, orders.getAddressId());
                    }
                    preparedStatement.setString(5, getUserName(userId));
                    if (orders.getContactName() != null) {
                        preparedStatement.setString(5, orders.getContactName());
                    }
                    preparedStatement.setString(6, getMobileNumber(userId));
                    if (orders.getContactNo() != null) {
                        preparedStatement.setString(6, orders.getContactNo());
                    }
                    preparedStatement.setString(7, null);
                    if (orders.getDeliveryInstructions() != null) {
                        preparedStatement.setString(7, orders.getDeliveryInstructions());
                    }
                    preparedStatement.setInt(8, orders.getCartId());
                    orders.setUserId(userId);

                    String cardQuery = "select cardNo, cardName, expiryDate from card where userId=?";
                    List<Card> viewCard = jdbcTemplate.query(cardQuery, new BeanPropertyRowMapper<>(Card.class), userId);
                    orderInfo.setCardList(viewCard);

                    orderInfo.setCartId(orders.getCartId());

                    String addressDesc = jdbcTemplate.queryForObject("select addressDesc from address where addressId=" + orders.getAddressId(), String.class);
                    orderInfo.setAddressDesc(addressDesc);

                    Date scheduledDate = jdbcTemplate.queryForObject("select scheduledDate from cart where cartId=" + orders.getCartId(), Date.class);
                    orderInfo.setScheduleDate(scheduledDate);

                    Time scheduledTime = jdbcTemplate.queryForObject("select scheduledTime from cart where cartId=" + orders.getCartId(), Time.class);
                    orderInfo.setScheduleTime(scheduledTime);

                    int creditScore = jdbcTemplate.queryForObject("select creditScore from user where userId=" + userId, Integer.class);
                    orderInfo.setCreditScore(creditScore);


                    RestaurantInfo restaurantQuery = jdbcTemplate.queryForObject("select restaurantName,addressDesc from restaurant inner join address on restaurant.addressId=address.addressId where restaurant.restaurantId=" + orders.getRestaurantId(), (resultSet, no) -> {
                        RestaurantInfo restaurantInfo = new RestaurantInfo();
                        restaurantInfo.setRestaurantName(resultSet.getString(1));
                        restaurantInfo.setAddressDesc(resultSet.getString(2));
                        return restaurantInfo;
                    });
                    orderInfo.setRestaurantInfo(restaurantQuery);

                });

                List avgTimeAndDuration=jdbcTemplate.queryForObject("select averageDeliveryTime,longitude,lattitude from restaurant inner join address on restaurant.addressId=address.addressId where restaurantId="+orders.getRestaurantId(),(rs,no)->
                {
                    List returningList=new ArrayList<>();
                    returningList.add(rs.getDouble(1));
                    returningList.add(new Location(rs.getDouble(2), rs.getDouble(3)));
                    return returningList;
                });
                Long deliveryDuration=locationService.getDuration(start,(Location)avgTimeAndDuration.get(1));


                int count=jdbcTemplate.queryForObject("select count(orderId) from orders where restaurantId="+orders.getRestaurantId(), Integer.class);

                orderInfo.setAverageDeliveryTime(this.calculateAvgDelTime((Double)avgTimeAndDuration.get(0),deliveryDuration,count,true));

                jdbcTemplate.update("update restaurant set averageDeliveryTime="+orderInfo.getAverageDeliveryTime()+" where restaurantId="+orders.getRestaurantId());

                return orderInfo;
            } catch (DataAccessException e) {
                e.printStackTrace();
                return null;
            }
        }

        return null;
    }

    public double calculateAvgDelTime(Double avgDelTime,Long duration,int ordersCount,boolean increment)
    {
        if(increment)
        {
            return (avgDelTime+duration)/(ordersCount);
        }
        return (avgDelTime-duration)/(ordersCount);
    }



    @Override
    public boolean cancelOrder(Orders orders) throws IOException {
        query="update orders set orderStatus="+8+" where orderId="+orders.getOrderId();
        jdbcTemplate.update(query);
        return true;
    }


    @Override
    public BrandDesc viewBrand(int brandId) throws IOException {
        query = "select brand.brandId,brandName,brand.description,logo,brandOrigin,avg(averageDeliveryTime),min(minimumCost) from brand inner join restaurant on restaurant.brandId=brand.brandId where brand.brandId=" + brandId +" group by brandId";
        return jdbcTemplate.queryForObject(query, (resultSet, no) ->
        {
            BrandDesc brandDescription = new BrandDesc();


            brandDescription.setBrandId(brandId);
            brandDescription.setBrandName(resultSet.getString(2));
            brandDescription.setDescription(resultSet.getString(3));
            brandDescription.setLogo(resultSet.getString(4));
            brandDescription.setBrandOrigin(resultSet.getString(5));
            brandDescription.setAverageDeliveryTime(resultSet.getDouble(6));
            brandDescription.setMinimumCost(resultSet.getDouble(7));
            return brandDescription;
        });
    }

    @Override
    public boolean createAccount(com.robosoft.lorem.model.User user)
    {
        boolean otpVerified=jdbcTemplate.queryForObject(REGISTRATION_CHECK,new Object[]{user.getEmailId()},Boolean.class);

        if(otpVerified)
        {

            if(user.getMobileNo()!=null)
            {
                jdbcTemplate.update(INSERT_MOBILE_NUMBER,user.getMobileNo());

                String  hashcode= passwordEncoder.encode(user.getPassword());
                jdbcTemplate.update(REGISTER_WITH_MOBILE,user.getFirstName(),user.getLastName(),user.getEmailId(),user.getMobileNo(),hashcode);
            }

            else
            {
                String  hashcode= passwordEncoder.encode(user.getPassword());
                jdbcTemplate.update(REGISTER,user.getFirstName(),user.getLastName(),user.getEmailId(),hashcode);
            }
            // checking for referred registration
            if(user.getReferralCode()!=0)
            {
                int score=jdbcTemplate.queryForObject("select creditScore from user where userid=?",new Object[]{user.getReferralCode()},Integer.class);
                score=score+1;
                jdbcTemplate.update("update user set creditScore=? where userId=?",score,user.getReferralCode());
            }

            //User userReturn= jdbcTemplate.queryForObject(GET_USER_BY_EMAIL_ID, new BeanPropertyRowMapper<>(User.class),user.getEmailId());
            return true;
        }
        return false;
    }


    @Override
    public boolean editProfile(UserEditFields userEditFields)
    {
        if(userEditFields.getMobileNo()!=null)
        {
            //check old number
            String phone = jdbcTemplate.queryForObject("select mobileNo from user where userId=?",new Object[]{userEditFields.getUserId()},String.class);

            if(phone!=null && phone.equals(userEditFields.getMobileNo()))
            {
                jdbcTemplate.update("update user set firstName=?, lastName=?, profilePic=? where userId=?",userEditFields.getFirstName(),userEditFields.getLastName(),userEditFields.getProfileUrl(),userEditFields.getUserId());
                return true;
            }

            //insert mobile number to mobileOtp table
            jdbcTemplate.update("insert into mobileOtp(mobileNo) values(?)",userEditFields.getMobileNo());

            // update user table
            jdbcTemplate.update("update user set firstName=?, lastName=?, mobileNo=?, profilePic=? where userId=?",userEditFields.getFirstName(),userEditFields.getLastName(),userEditFields.getMobileNo(),userEditFields.getProfileUrl(),userEditFields.getUserId());

            if(phone!=null)
            {
                //delete old number from mobileOtp
                jdbcTemplate.update("delete from mobileOtp where mobileNo=?",phone);
            }
            return true;
        }
        else
        {
            jdbcTemplate.update("update user set firstName=?, lastName=?, profilePic=? where userId=?",userEditFields.getFirstName(),userEditFields.getLastName(),userEditFields.getProfilePic().getOriginalFilename(),userEditFields.getUserId());
            return true;
        }
    }

    @Override
    public int getUserIdFromEmail()
    {
        String email = getUserNameFromToken();
        int userId = jdbcTemplate.queryForObject("select userId from user where emailId=?",new Object[]{email},Integer.class);
        return userId;
    }

    @Override
    public int referAFriend()
    {
        int userId=getUserIdFromEmail();
        return userId;
    }

    // if required
    @Override
    public Map<String,String> onClickShareApp(String userId)
    {
        String code=userId;
        String refer_link="localhost:8080/users/emails2fa";
        Map map = new HashMap<String,String>();
        map.put(code,refer_link);
        return map;
    }

    @Override
    public Map<Integer,List<Menu>> Gallery(int restaurantId, int page)
    {
        int limit=24;
        Map map = new HashMap<Integer,List>();
        offset=this.getOffset(page);
        List<Menu> photos=jdbcTemplate.query("select dishPhoto from menu where restaurantId=? limit ?,?",(rs, rowNum) -> {
            return new Menu(rs.getString("dishPhoto"));
        },restaurantId,offset,limit);

        if(photos.size()!=0)
        {
            map.put(photos.size(),photos);
            return map;
        }

        return null;

    }


    @Override
    public Map<Integer,List<Offer>> viewBestOffers(int page )
    {
        //offset = limit*(pg-1) --> 3*(1-1)
        offset=this.getOffset(page);
        Map map = new HashMap<Integer,List>();

        List <Offer> users= jdbcTemplate.query(GET_OFFERS, new BeanPropertyRowMapper<>(Offer.class),offset,limit);

        if(users.size()!=0)
        {
            map.put(users.size(), users);
            return map;
        }
        return null;
    }

    @Override
    public Map<Integer,List<Offer>> viewAllOffers(int page)
    {
        Map map = new HashMap<Integer,List>();
        offset=this.getOffset(page);
        List <Offer> allOffers= jdbcTemplate.query(ALL_OFFERS,new BeanPropertyRowMapper<>(Offer.class),offset,limit);

        if(allOffers.size()!=0)
        {
            map.put(allOffers.size(),allOffers);
            return map;
        }
        return null;
    }

    // get offers by brand Id
    @Override
    public Map<Integer,List<Offer>> viewBrandOffers(int brandId, int page)
    {
        Map map = new HashMap<Integer,List>();
        offset=this.getOffset(page);
        List<Offer> offerList=jdbcTemplate.query(GET_BRAND_OFFERS,new BeanPropertyRowMapper<>(Offer.class),brandId,offset,limit);
        if(offerList.size()!=0)
        {
            map.put(offerList.size(),offerList);
            return map;
        }
        return null;
    }

    @Override
    public Map<Integer,List<Offer>> viewBestOfferOfRestaurant(int page, int restaurantId)
    {
        //offset = limit*(pg-1) --> 3*(1-1)
        offset=this.getOffset(page);
        Map map = new HashMap<Integer,List>();

        try
        {
            int brandId=jdbcTemplate.queryForObject("select brandId from restaurant where restaurantId=?",Integer.class, new Object[]{restaurantId});
            if(brandId!=0)
            {
                List <Offer> users= jdbcTemplate.query("select offerId,discount,description,photo from offer where brandId=? order by discount desc limit ?,?", new BeanPropertyRowMapper<>(Offer.class),brandId,offset,limit);

                if(users.size()!=0)
                {
                    map.put(users.size(), users);
                    return map;
                }
                return null;
            }
        }
        catch (Exception e)
        {
            List <Offer> users= jdbcTemplate.query(GET_OFFERS_OF_RESTAURANT, new BeanPropertyRowMapper<>(Offer.class),offset,limit);

            if(users.size()!=0)
            {
                map.put(users.size(), users);
                return map;
            }
            return null;
        }
        return null;
    }


    @Override
    public Map<Integer,List<Offer>> viewAllOffersOfRestaurant(int page, int restaurantId)
    {
        //offset = limit*(pg-1) --> 3*(1-1)
        offset=this.getOffset(page);
        Map map = new HashMap<Integer,List>();

        try
        {
            int brandId=jdbcTemplate.queryForObject("select brandId from restaurant where restaurantId=?",Integer.class, new Object[]{restaurantId});
            if(brandId!=0)
            {
                List <Offer> users= jdbcTemplate.query("select offerId,discount,description,photo from offer where brandId=?  limit ?,?", new BeanPropertyRowMapper<>(Offer.class),brandId,offset,limit);

                if(users.size()!=0)
                {
                    map.put(users.size(), users);
                    return map;
                }
                return null;
            }
        }
        catch (Exception e)
        {
            List <Offer> users= jdbcTemplate.query("select offerId,discount,description,photo from offer where brandId is null limit ?,?", new BeanPropertyRowMapper<>(Offer.class),offset,limit);

            if(users.size()!=0)
            {
                map.put(users.size(), users);
                return map;
            }
            return null;
        }
        return null;
    }



    @Override
    public ResponseEntity<?> viewOfferDetails(String offerId)
    {
        try
        {
            OfferAllFields offerAllFields= new OfferAllFields();

            // offer details
            Offer offer_obj=jdbcTemplate.queryForObject(VIEW_DETAILS_OF_AN_OFFER,(rs, rowNum) ->
            {
                return new Offer(rs.getString("offerId"),rs.getFloat("discount"),rs.getInt("maxCashBack"),rs.getDate("validUpto"),rs.getInt("validPerMonth"),rs.getString("photo"),rs.getString("description"),rs.getInt("brandId"),rs.getBoolean("codEnabled"),rs.getFloat("superCashPercent"),rs.getInt("maxSuperCash"));
            },offerId);

            offerAllFields.setOffer(offer_obj);

            int user_id=getUserIdFromEmail();

            // user address
            String delivery_to= jdbcTemplate.queryForObject("select addressDesc from address where userId=? and primaryAddress=true",String.class, new Object[]{user_id});
            offerAllFields.setAddressDesc(delivery_to);

            try
            {
                // to check if offer is branded or not and search correspondingly
                int brandId=jdbcTemplate.queryForObject("select brandId from offer where offerId=?",Integer.class, new Object[]{offerId});

                if(brandId!=0)
                {
                    // to get nearby outlets
                    List<RestaurantAddress> offer_applicable_outlets=new ArrayList<RestaurantAddress>();
                    jdbcTemplate.query("select restaurant.restaurantName, address.addressDesc from restaurant inner join address on restaurant.addressId=address.addressId where brandId=? and address.addressDesc like '%"+delivery_to+"%'",(rs, rowNum) ->
                    {
                        RestaurantAddress restaurantAddress = new RestaurantAddress();
                        restaurantAddress.setRestaurantName(rs.getString("restaurant.restaurantName"));
                        restaurantAddress.setRestAddress(rs.getString("address.addressDesc"));

                        offer_applicable_outlets.add(restaurantAddress);
                        return restaurantAddress;
                    });

                    offerAllFields.setRestaurantAddress(offer_applicable_outlets);
                    return new ResponseEntity<>(offerAllFields, HttpStatus.OK);
                }
            }
            catch (Exception e)
            {

                // general offer restaurants
                List<RestaurantAddress> offer_applicable_outlets=new ArrayList<RestaurantAddress>();
                jdbcTemplate.query("select restaurant.restaurantName, address.addressDesc from restaurant inner join address on restaurant.addressId=address.addressId where address.addressDesc like '%"+delivery_to+"%'",(rs, rowNum) ->
                {
                    RestaurantAddress restaurantAddress = new RestaurantAddress();
                    restaurantAddress.setRestaurantName(rs.getString("restaurant.restaurantName"));
                    restaurantAddress.setRestAddress(rs.getString("address.addressDesc"));

                    offer_applicable_outlets.add(restaurantAddress);
                    return restaurantAddress;
                });

                offerAllFields.setRestaurantAddress(offer_applicable_outlets);

                return new ResponseEntity<>(offerAllFields, HttpStatus.OK);
            }
        }
        catch (Exception e)
        {
            // if offer ID is invalid or if doesn't exist
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @Override
    public ResponseEntity<?> redeem(int claimedCreditScore)
    {
        int userId=getUserIdFromEmail();

        // get user credit score
        int users_credit=jdbcTemplate.queryForObject("select creditScore from user where userId=?",Integer.class, new Object[]{userId});

        if(users_credit==0)
        {
            return  new ResponseEntity<>("user has 0 credit score",HttpStatus.FORBIDDEN);
        }

        // calculate remaining credit score
        int remaining= users_credit-claimedCreditScore;

        // update user's credit score
        jdbcTemplate.update("update user set creditScore=? where userId=?",remaining,userId);
        return  new ResponseEntity<>("Redeemed super coins successfully", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> applyOffer(String offerId)
    {

        int userId= getUserIdFromEmail();

        //check for offer validity
        LocalDate offer_date=jdbcTemplate.queryForObject("select validUpto from offer where offerId=?", LocalDate.class, new Object[]{offerId});

        LocalDate today_date= java.time.LocalDate.now();

        if(today_date.isBefore(offer_date))
        {
            // check for number of times user has used that offer
            int valid=jdbcTemplate.queryForObject("select count(promoCode) from payment where userId=? and promoCode=?",Integer.class,userId,offerId);

            //check for valid per month
            int validPerMonth=jdbcTemplate.queryForObject("select validPerMonth from offer where offerId=?",Integer.class, new Object[]{offerId});

            if(valid<validPerMonth)
            {
                Offer offer_obj=jdbcTemplate.queryForObject("select * from offer where offerId=?",(rs, rowNum) -> {
                    return new Offer(rs.getString("offerId"),rs.getFloat("discount"),rs.getInt("maxCashBack"),rs.getDate("validUpto"),rs.getInt("validPerMonth"),rs.getString("photo"),rs.getString("description"),rs.getInt("brandId"),rs.getBoolean("codEnabled"),rs.getFloat("superCashPercent"),rs.getInt("maxSuperCash"));
                },offerId);

                System.out.println(offer_obj);
                return  new ResponseEntity<>(offer_obj, HttpStatus.OK);
            }
            else
            {
                //if the offer is already used
                return  new ResponseEntity<>("offer is already used",HttpStatus.FORBIDDEN);
            }
        }
        else
        {
            //if the offer is expired
            return  new ResponseEntity<>("offer is expired",HttpStatus.FORBIDDEN);
        }

    }

}


