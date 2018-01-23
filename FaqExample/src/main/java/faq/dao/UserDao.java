package faq.dao;

import faq.model.User;

/**
 * @author jcvidal
 *
 * Interface for UserDao
 *
 */
public interface UserDao {
	/**
	 * 
	 * Get a User by name
	 * 
	 * @param name name of User
	 * @return unique User
	 */
	public User getUserByName(String name); 
}
