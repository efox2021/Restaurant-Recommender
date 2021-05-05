package com.mc_03.Backend;

import com.mc_03.Backend.Cookie.Cookie;
import com.mc_03.Backend.Cookie.CookieRepository;
import com.mc_03.Backend.Restaurant.Restaurant;
import com.mc_03.Backend.RestaurantHistory.RestaurantHistoryController;
import com.mc_03.Backend.User.UserController;
import com.mc_03.Backend.User.UserRepository;
import com.mc_03.Backend.User.Users;
import com.mc_03.Backend.UserReview.UserReview;
import com.mc_03.Backend.UserReview.UserReviewController;
import com.mc_03.Backend.UserReview.UserReviewRepository;
import com.mc_03.Backend.Yelp.Business.BusinessRepository;
import com.mc_03.Backend.Yelp.Business.Business;
import com.mc_03.Backend.Yelp.YelpReviews.YelpReviews;
import com.mc_03.Backend.Yelp.YelpReviews.YelpReviewsRepository;
import com.mc_03.Backend.Yelp.YelpController;
import org.junit.jupiter.api.*;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableJpaRepositories
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

class BackendApplicationTests {

	@MockBean
	private UserRepository userRepository;

	@MockBean
	private CookieRepository cookieRepository;



	@Test
	@Order(1)
	void contextLoads() {

	}

	@Test
	@Order(2)
	void creatingUser() {
		try {
			Users creatingUser = new Users();
			creatingUser.setEmail("test@test.net");
			creatingUser.setFirstName("TestingFirst");
			creatingUser.setLastName("TestingLast");
			creatingUser.setPassword("Testing My Password");
			creatingUser.setUsername("testing1234");
			userRepository.save(creatingUser);
			userRepository.delete(creatingUser);

		}
		catch(Exception e)
		{
			e.printStackTrace();
			assert(false);
		}
		assert(true);

	}
	@Test
	@Order(3)
	void creatingCookie() {
		try{
			//Make User
			Users creatingUser = new Users();
			creatingUser.setEmail("test@test.net");
			creatingUser.setFirstName("TestingFirst");
			creatingUser.setLastName("TestingLast");
			creatingUser.setPassword("Testing My Password");
			creatingUser.setUsername("testing1234");
			userRepository.save(creatingUser);
			//Make cookie
			Cookie testCookieTest = new Cookie();
			testCookieTest.setDateAdded(new Date());
			testCookieTest.setLastLoggedIn(new Date());
			testCookieTest.setUser(creatingUser);
			testCookieTest.setDeviceName("Testing Device");
			cookieRepository.save(testCookieTest);
			//Destory Cookie
			System.out.println(testCookieTest.getCookieID());
			cookieRepository.deleteBycookieId(testCookieTest.getCookieID());
			//Destroy User
			userRepository.delete(creatingUser);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			assert(false);
		}

		assert(true);
	}


	@Test
	@Order(4)
	void validEmailTesting()
	{

		Users testUser = mock(Users.class);
		when(testUser.getEmail()).thenReturn("asdf1234@test.net");

		assertEquals( true, Users.validEmail(testUser));

		Users testUser2 = mock(Users.class);
		when(testUser2.getEmail()).thenReturn("asdf 1234@test.net");
		assertEquals(false,Users.validEmail(testUser2));

		Users testUser3 = mock(Users.class);
		when(testUser3.getEmail()).thenReturn(null);
		assertEquals(false,Users.validEmail(testUser3));
	}

	@Test
	@Order(5)
	void vaildPasswordTesting()
	{
		Users testUser1 = mock(Users.class);
		when(testUser1.getPassword()).thenReturn("Testing1!");

		assertEquals(true, Users.validPassword(testUser1));

		Users testUser2 = mock(Users.class);
		when(testUser2.getPassword()).thenReturn("Testing2");

		assertEquals(false, Users.validPassword(testUser2));

		Users testUser3 = mock(Users.class);
		when(testUser3.getPassword()).thenReturn(null);

		assertEquals(false, Users.validPassword(testUser3));
	}

	@Test
	void restaurantGetTesting()
	{
		Restaurant testRest = mock(Restaurant.class);
		when(testRest.getName()).thenReturn("YesTestRest1");
		assertEquals("YesTestRest1", testRest.getName());

		Restaurant testRest0 = mock(Restaurant.class);
		when(testRest0.getPhone()).thenReturn("515-555-1234");
		assertEquals("515-555-1234", testRest0.getPhone());

		Restaurant testRest1 = mock(Restaurant.class);
		when(testRest1.getYelp_id()).thenReturn("1234567");
		assertEquals("1234567", testRest1.getYelp_id());

		Restaurant testRest2 = mock(Restaurant.class);
		when(testRest2.getAlias()).thenReturn("Food");
		assertEquals("Food", testRest2.getAlias());
	}

	@Test
	void yelpControllerFindBusinessesReviews() throws Exception {
		YelpController yelp = mock(YelpController.class);
		BusinessRepository repo = mock(BusinessRepository.class);
		Map<String, String> map = new HashMap<>();
		Business[] businesses = repo.findBusinesses(map);

		when(repo.findBusinesses(map)).thenReturn(businesses);
		yelp.setBusinessRepository(repo);

		assertEquals(businesses, yelp.findBusinesses(map));
	}

	@Test
	void yelpControllerFindReviewsTest() throws Exception {
		YelpController yelp = mock(YelpController.class);
		YelpReviewsRepository repo = mock(YelpReviewsRepository.class);
		String id = "1";
		YelpReviews[] reviews = repo.findReviews(id);

		when(repo.findReviews(id)).thenReturn(reviews);
		yelp.setUserReviewRepo(repo);

		assertEquals(reviews, yelp.findReviews(id));
	}

	@Test
	void YelpUserReviewsRepositoryFindReviewsByBusinessAndLocaleTest() throws Exception {
		Business business = mock(Business.class);
		YelpReviewsRepository repo = mock(YelpReviewsRepository.class);
		String locale = "es_ES";
		YelpReviews[] reviews = repo.findReviewsWithBusinessLocale(business, locale);

		when(repo.findReviewsWithBusinessLocale(business, locale)).thenReturn(reviews);
		assertEquals(reviews, repo.findReviewsWithBusinessLocale(business, locale));
	}

	@Test
	void YelpUserReviewsRepositoryFindReviewsByIDAndLocaleTest() throws Exception {
		YelpReviewsRepository repo = mock(YelpReviewsRepository.class);
		String id ="1";
		String locale = "es_ES";
		YelpReviews[] reviews = repo.findReviewsWithIDLocale(id, locale);

		when(repo.findReviewsWithIDLocale(id, locale)).thenReturn(reviews);
		assertEquals(reviews, repo.findReviewsWithIDLocale(id, locale));
	}

	@Test
	void userReviewsByRestaurantIDTest() throws Exception {
		UserReviewController userReviewCont = mock(UserReviewController.class);
		UserReviewRepository repo = mock(UserReviewRepository.class);
		int rid = 1;
		List<UserReview> reviews = new ArrayList<>();

		when(userReviewCont.getReviewsRID(rid)).thenReturn(reviews);
		assertEquals(reviews, userReviewCont.getReviewsRID(rid));
	}

	@Test
	void userReviewsByUserIDTest() throws Exception {
		UserReviewController userReviewCont = mock(UserReviewController.class);
		UserReviewRepository repo = mock(UserReviewRepository.class);
		Long uid = 0L;
		List<UserReview> reviews = new ArrayList<>();

		when(userReviewCont.getReviewsUID(uid)).thenReturn(reviews);
		assertEquals(reviews, userReviewCont.getReviewsUID(uid));
	}

	@Test
	void userKeywordsByRIdTest1() throws Exception {
		UserReviewController userReviewCont = mock(UserReviewController.class);
		UserReviewRepository repo = mock(UserReviewRepository.class);
		Long uid = 0L;
		int rid = 0;
		ArrayList<String> keywords = new ArrayList<>();


		when(userReviewCont.getTopKeywords(rid)).thenReturn(keywords);
		assertEquals(keywords, userReviewCont.getTopKeywords(rid));
	}

	@Test
	void userKeywordsByRIdTest2() throws Exception {
		UserReviewRepository userReviewRepository = mock(UserReviewRepository.class);
		Collection<UserReview> reviews = null;
		int rid = 0;

		when(userReviewRepository.findByreviewId(rid)).thenReturn(reviews);
		UserReviewController userReviewCont = mock(UserReviewController.class);
		Long uid = 0L;
		ArrayList<String> keywords = new ArrayList<>();


		when(userReviewCont.getTopKeywords(rid)).thenReturn(keywords);
		assertEquals(keywords, userReviewCont.getTopKeywords(rid));
	}


	@Test
	@Order(6)
	void userChangesTesting()
	{

		HashMap<String, String> test = mock(HashMap.class);

		when(test.get(anyString())).thenAnswer(
				new Answer<String>(){
					@Override
					public String answer(InvocationOnMock invocation){
						if ("user_id".equals(invocation.getArgument(0))){
							return null;
						}
						else if("cookieID".equals(invocation.getArgument(0))) {
							return "aabbccddaabbccddaabbccddaabbccdd";
						}
						else {
							return null;
						}
					}});


		UserController testing = new UserController();
		HashMap<String, Object> result = testing.logout(test);
		String getString = (String) result.get("status");
		assertEquals("missing_user_id", getString);

		HashMap<String, String> test2 = mock(HashMap.class);
		when(test.get(anyString())).thenAnswer(
				new Answer<String>(){
					@Override
					public String answer(InvocationOnMock invocation){
						if ("user_id".equals(invocation.getArgument(0))){
							return "123";
						}
						else if("cookieID".equals(invocation.getArgument(0))) {
							return null;
						}
						else {
							return null;
						}
					}});


		UserController testing2 = new UserController();
		HashMap<String, Object> result2 = testing2.logout(test);
		String getString2 = (String) result2.get("status");
		assertEquals("missing_cookie_id", getString2);


	}

	@Test
	@Order(7)
	void restaurantHistoryPassedTesting()
	{
		RestaurantHistoryController testing = new RestaurantHistoryController();
		HashMap<String, String> test = mock(HashMap.class);
		when(test.get(anyString())).thenAnswer(
				new Answer<String>(){
					@Override
					public String answer(InvocationOnMock invocation){
						if ("user_id".equals(invocation.getArgument(0))){
							return "1";
						}
						else if("cookieID".equals(invocation.getArgument(0))) {
							return "aabbccddaabbccddaabbccddaabbccdd";
						}
						else if("restaurant_id".equals(invocation.getArgument(0))) {
							return null;
						}
						else {
							return null;
						}
					}});

		HashMap<String, String> test2 = mock(HashMap.class);
		when(test.get(anyString())).thenAnswer(
				new Answer<String>(){
					@Override
					public String answer(InvocationOnMock invocation){
						if ("user_id".equals(invocation.getArgument(0))){
							return null;
						}
						else if("cookieID".equals(invocation.getArgument(0))) {
							return "aabbccddaabbccddaabbccddaabbccdd";
						}
						else if("restaurant_id".equals(invocation.getArgument(0))) {
							return "1";
						}
						else {
							return null;
						}
					}});

		HashMap<String, String> test3 = mock(HashMap.class);
		when(test.get(anyString())).thenAnswer(
				new Answer<String>(){
					@Override
					public String answer(InvocationOnMock invocation){
						if ("user_id".equals(invocation.getArgument(0))){
							return "1";
						}
						else if("cookieID".equals(invocation.getArgument(0))) {
							return null;
						}
						else if("restaurant_id".equals(invocation.getArgument(0))) {
							return "1";
						}
						else {
							return null;
						}
					}});

		HashMap<String, String> text = testing.restaurantPass(test);
		assertEquals("Missing_Data", text.get("Status"));

		HashMap<String, String> text2 = testing.restaurantPass(test2);
		assertEquals("Missing_Data", text2.get("Status"));

		HashMap<String, String> text3 = testing.restaurantPass(test3);
		assertEquals("Missing_Data", text3.get("Status"));
	}

	@Test
	@Order(8)
	void restaurantHistoryVisitedTesting()
	{
		RestaurantHistoryController testing = new RestaurantHistoryController();
		HashMap<String, String> test = mock(HashMap.class);
		when(test.get(anyString())).thenAnswer(
				new Answer<String>(){
					@Override
					public String answer(InvocationOnMock invocation){
						if ("user_id".equals(invocation.getArgument(0))){
							return "1";
						}
						else if("cookieID".equals(invocation.getArgument(0))) {
							return "aabbccddaabbccddaabbccddaabbccdd";
						}
						else if("restaurant_id".equals(invocation.getArgument(0))) {
							return null;
						}
						else {
							return null;
						}
					}});

		HashMap<String, String> test2 = mock(HashMap.class);
		when(test.get(anyString())).thenAnswer(
				new Answer<String>(){
					@Override
					public String answer(InvocationOnMock invocation){
						if ("user_id".equals(invocation.getArgument(0))){
							return null;
						}
						else if("cookieID".equals(invocation.getArgument(0))) {
							return "aabbccddaabbccddaabbccddaabbccdd";
						}
						else if("restaurant_id".equals(invocation.getArgument(0))) {
							return "1";
						}
						else {
							return null;
						}
					}});

		HashMap<String, String> test3 = mock(HashMap.class);
		when(test.get(anyString())).thenAnswer(
				new Answer<String>(){
					@Override
					public String answer(InvocationOnMock invocation){
						if ("user_id".equals(invocation.getArgument(0))){
							return "1";
						}
						else if("cookieID".equals(invocation.getArgument(0))) {
							return null;
						}
						else if("restaurant_id".equals(invocation.getArgument(0))) {
							return "1";
						}
						else {
							return null;
						}
					}});


		HashMap<String, String> text = testing.restaurantVisited(test);
		assertEquals("Missing_Data", text.get("Status"));

		HashMap<String, String> text2 = testing.restaurantVisited(test2);
		assertEquals("Missing_Data", text2.get("Status"));

		HashMap<String, String> text3 = testing.restaurantVisited(test3);
		assertEquals("Missing_Data", text3.get("Status"));

	}

}
