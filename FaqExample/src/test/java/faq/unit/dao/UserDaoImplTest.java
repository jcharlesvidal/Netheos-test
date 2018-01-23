package faq.unit.dao;

import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import faq.dao.UserDao;
import faq.model.User;



/**
 * @author jcvidal
 *
 * To Test ServiceDao (using XML files to fill H2 DB)
 *
 */
public class UserDaoImplTest extends EntityDaoImplTest{

	@Autowired
	UserDao userDao;

	/* (non-Javadoc)
	 * @see faq.unit.dao.EntityDaoImplTest#getDataSet()
	 */
	@Override
	protected IDataSet getDataSet() throws Exception{
		  IDataSet[] datasets = new IDataSet[] {
				  new FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream("Role.xml")),
				  new FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream("User.xml")),
		  };
		return new CompositeDataSet(datasets);
	}

	/**
	 * Test Dao getUserByName method
	 */
	@Test
	public void checkUser(){
		User user = userDao.getUserByName("pipo");
		Assert.assertNull(user);
		
		user = userDao.getUserByName("admin");
		Assert.assertEquals(user.getName(), "admin");
		Assert.assertEquals(user.getRole().getName(), "admin");
		
		user = userDao.getUserByName("user");
		Assert.assertEquals(user.getName(), "user");
		Assert.assertEquals(user.getRole().getName(), "user");
	}
}
