package faq.unit.service;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import faq.dao.UserDao;
import faq.model.Role;
import faq.model.User;
import faq.service.impl.UserServiceImpl;

/**
 * @author jcvidal
 *
 * Test UserService
 */
public class UserServiceImplTest {

	@Mock
	UserDao dao;
	
	@InjectMocks
	UserServiceImpl userService;
	
	@Spy
	List<User> users = new ArrayList<User>();
	
	@BeforeClass
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		users = getUserList();
	}
	/**
	 * Test User
	 */
	@Test
	public void check(){
		User user = users.get(0);
		when(dao.getUserByName(anyString())).thenReturn(user);
		Assert.assertEquals(userService.getUserByName(anyString()), user);
	}
	/**
	 * @return mock list
	 */
	public List<User> getUserList(){
		User user = new User();
		user.setId(1);
		user.setName("admin");
		user.setPassword("admin");
		Role role = new Role();
		role.setId(1);
		role.setName("admin");
		user.setRole(role);
		users.add(user);

		user = new User();
		user.setId(2);
		user.setName("user");
		user.setPassword("user");
		role = new Role();
		role.setId(2);
		role.setName("user");
		user.setRole(role);
		users.add(user);
		
		return users;
	}
	
}
