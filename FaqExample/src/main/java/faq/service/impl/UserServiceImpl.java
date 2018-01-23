package faq.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import faq.dao.UserDao;
import faq.model.User;
import faq.service.UserService;

/**
 * @author jcvidal
 *
 * Implementation of UserService
 */
@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	/* (non-Javadoc)
	 * @see faq.service.UserService#getUserByName(java.lang.String)
	 */
	@Override
	public User getUserByName(String name) {
		return userDao.getUserByName(name);
	}

}
