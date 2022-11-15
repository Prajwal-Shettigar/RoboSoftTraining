package com.robosoft.lorem.service;

import com.robosoft.lorem.entity.Addon;
import com.robosoft.lorem.entity.Payment;
import com.robosoft.lorem.model.*;
import com.robosoft.lorem.routeResponse.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;


@Service
public class UserServiceImpl implements UserService{

    @Autowired
    LocationService locationService;

    @Autowired
    JdbcTemplate jdbcTemplate;



    @Value("${page.data.count}")
    private int perPageDataCount;

    String query;


    @Override
    public RestaurantSearchResult searchRestaurant(SearchFilter searchFilter) {


        RestaurantSearchResult restaurantSearchResult = new RestaurantSearchResult();

        long offset = this.getOffset(searchFilter.getPageNumber());


        if(searchFilter.getRestaurantOrFoodType()==null)
            searchFilter.setRestaurantOrFoodType("");

        if(searchFilter.getDate()==null)
            searchFilter.setDate(Date.valueOf(LocalDate.now()));

        if(searchFilter.getAddress()==null)
            searchFilter.setAddress("");


        String selectFields = "SELECT DISTINCT " +
                "r.restaurantId," +
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
                "r.averageDeliveryTime ";


        query = "FROM restaurant r " +
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
            searchFilter.setPageNumber(0);
        if(searchFilter.getPageNumber()==1){
            String countQuery = "SELECT count(distinct r.restaurantId) ";
            long count = jdbcTemplate.queryForObject(countQuery+query, Long.class);
            restaurantSearchResult.setTotalRocordsCount(count);

            if (count==0)
                return restaurantSearchResult;
        }




        query = selectFields+query+"limit "+offset+","+perPageDataCount;

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

        if(!searchFilter.isDescRating())
            query=query+" order by r.overAllRating asc ";
        else
            query=query+" order by r.overAllRating desc ";
    }


    public double getAverageMealCostForRestaurant(int restaurantId){
        return jdbcTemplate.queryForObject("select avg(price) from menu where restaurantId="+restaurantId, Double.class);
    }

    public long getOffset(int pageNumber){
        if(pageNumber==0)
            pageNumber=1;

        return (long)perPageDataCount*(pageNumber-1);
    }



    @Override
    public NearByBrandsSearchResult getNearbyBrands(String address, int limit){
        query="select distinct b.brandId,b.brandName,b.description,b.logo,b.profilePic,b.brandOrigin from brand b inner join restaurant r on b.brandId=r.brandId inner join address a on r.addressId=a.addressId where addressDesc like '%"+address+"%' limit "+limit;


        NearByBrandsSearchResult nearByBrandsSearchResult = new NearByBrandsSearchResult();


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

        nearByBrandsSearchResult.setResultsCount(nearByBrands.size());
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
        query = "update cart set cookingInstructions=?,totalAmount=? where cartId=? and userId=?";

        jdbcTemplate.update(query,(preparedStatement)->{
            preparedStatement.setString(1,cartModel.getCookingInstruction());
            preparedStatement.setDouble(2,cartModel.getToPay());
            preparedStatement.setInt(3,cartModel.getCartId());
            preparedStatement.setInt(4,cartModel.getUserId());
        });

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
    public OrderResponseModel getMyOrdersByStatus(String orderStatus, int userId,int pageNumber) {

        OrderResponseModel orderResponseModel = new OrderResponseModel();


        int orderIndex = this.getStatusIndex(orderStatus);

        long offset = this.getOffset(pageNumber);

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

        query=startingQuery+query+" limit "+offset+","+perPageDataCount;

        List<OrderModel> orders = this.getOrdersUsingQuery(query);

        orderResponseModel.setOrders(orders);
        orderResponseModel.setTotalRecordsInPage(orders.size());

        return orderResponseModel;
    }


    //get order status index using order status
    public int getStatusIndex(String status){
        switch (status.toUpperCase()){
            case "ACTIVE"->{return 6;}
            case "PAST"->{return 7;}
            case "CANCELLED"->{return 8;}
            default -> {return 9;}
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


            //get grand total
            Double amount = jdbcTemplate.queryForObject("select grandTotal from payment where orderId="+ orderModel.getOrderId(), Double.class);
            orderModel.setAmount(amount);


            return orderModel;
        });
    }


    //get a carts list

    @Override
    public CartsResponseModel getMyCarts(int userId, int pageNumber) {
        long offset = this.getOffset(pageNumber);

        CartsResponseModel cartsResponseModel = new CartsResponseModel();


        query = " from cart where userId="+userId+" and cartDeleted=false";

        if(pageNumber==1){
            long count = jdbcTemplate.queryForObject("select count(cartId)"+query, Long.class);
            cartsResponseModel.setTotalResultCount(count);

            if(count==0)
                return cartsResponseModel;
        }

        query = "select cartId,restaurantId,totalAmount"+query+" limit "+offset+","+perPageDataCount;


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

}
