package faq.service;

import faq.model.User;

/**
 * @author jcvidal
 *
 * Service using UserDao
 *
 */
public interface UserService {
	/**
	 * 
	 * Get User by name
	 * 
	 * @param name User name
	 * @return Unqiue User
	 */
	public User getUserByName(String name);
}
