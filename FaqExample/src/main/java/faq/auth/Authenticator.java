package faq.auth;

import java.util.Base64;
import java.util.List;

import javax.ws.rs.core.HttpHeaders;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import faq.model.User;
import faq.service.UserService;

/**
 * @author jcvidal
 * The Authenticator module. Use user and role tables 
 *
 */
@Component
public class Authenticator {
		private static final String AUTH_HEADER = "authorization";
		private static final String ADMIN_ROLE = "admin";
		private static final String USER_ROLE = "user";
		
		@Autowired
		private UserService userService;
		
		private Log log = LogFactory.getLog(Authenticator.class);
		
		
		/**
		 * Check if user identified in HTTP header (basic)has admin role
		 * 
		 * @param headers : HTTP header
		 * @return AuthErr object
		 */
		public AuthErr checkAdmin(HttpHeaders headers) {
			AuthTmp atmp = getUserNameFromCredentials(headers);
			if (atmp == null) {
				log.info("Probleme dans le header : impossible de récupérer les credentials");
				return new AuthErr(AuthErrorStatus.ERR_HEADER);
			}
			User user = getUserFromDataBase(atmp);
			if (user == null) {
				return atmp.getErr();
			}
			if (!user.getRole().getName().equals(ADMIN_ROLE)) {
				log.info("L'utilisateur "+user.getName()+" n'a pas les droits admin");
				return new AuthErr(AuthErrorStatus.RET_ROLE);
			}
			return new AuthErr();
		}

		/**
		 * Check if user identified in HTTP header (basic)has user (or admin) role
		 * 
		 * @param headers : HTTP header
		 * @return AuthErr object
		 */
		public AuthErr checkUser(HttpHeaders headers) {
			AuthTmp atmp = getUserNameFromCredentials(headers);
			if (atmp == null) {
				log.info("Probleme dans le header : impossible de récupérer les credentials");
				return new AuthErr(AuthErrorStatus.ERR_HEADER);
			}
			User user = getUserFromDataBase(atmp);
			if (user == null) {
				return atmp.getErr();
			}
			if (!user.getRole().getName().equals(ADMIN_ROLE) && !user.getRole().getName().equals(USER_ROLE)) {
				log.info("L'utilisateur "+user.getName()+" n'a pas les droits admin");
				return new AuthErr(AuthErrorStatus.RET_ROLE);
			}
			return new AuthErr();
		}
		
		/**
		 * 
		 * Extract User+Pwd from HTTP header (Basic) and return an AuthTmp temporary object
		 * 
		 * @param headers HTTP headers
		 * @return AuthTmp object
		 */
		private AuthTmp getUserNameFromCredentials(HttpHeaders headers) {
			List<String> creds = headers.getRequestHeader(AUTH_HEADER);
			if ((creds == null) || (creds.size() == 0)) {
				return null;
			}
			// get user:pwd (in base64) in second element of array
			String cred = creds.get(0);
			String[] ar = cred.split(" ");
			if ((ar == null) || (ar.length != 2)) {
				return null;
			}
			cred = ar[1];
			// decode user:pawd
			cred = new String(Base64.getDecoder().decode(cred));
			// get user and pawd
			ar = cred.split(":");
			if ((ar == null) || (ar.length != 2)) {
				return null;
			}
			String username = ar[0];
			String pwd = ar[1];
			
			return new AuthTmp(username,pwd);
		}

		/**
		 * 
		 * Get User object from database using service
		 * 
		 * @param atmp AuthTmp Object containing username and pwd) 
		 * @return User
		 */
		private User getUserFromDataBase (AuthTmp atmp) {
			User user = null;
			try {
				user = userService.getUserByName(atmp.getUsername());
			} catch (Exception ex) {
				ex.printStackTrace();
				return null;
			}
			if (user == null) {
				log.info("L'utilisateur "+atmp.getUsername()+" n'existe pas");
				atmp.setErr(new AuthErr(AuthErrorStatus.RET_USER));
				return null;
			}
			log.info("User is "+user.getName());
			if (!user.getPassword().equals(atmp.getPassword())) {
				log.info("Le mode de passe de l'utilisateur "+atmp.getUsername()+" est incorrect");
				atmp.setErr(new AuthErr(AuthErrorStatus.RET_PWD));
				return null;
			}
			return user;
		}
		
		/**
		 * @author jcvidal
		 *
		 * Temporary object used by some functions within Authenticator Class 
		 *
		 */
		public class AuthTmp {
			AuthErr err;
			String username;
			String password;
			
			public AuthTmp(AuthErrorStatus aes,String username) {
				this.username = username;
				this.password = "";
			}
			public AuthTmp(String username,String password) {
				this.username = username;
				this.password = password;
			}
			
			public AuthErr getErr() {
				return err;
			}
			public void setErr(AuthErr err) {
				this.err = err;
			}
			public String getUsername() {
				return username;
			}
			public void setUsername(String username) {
				this.username = username;
			}
			public String getPassword() {
				return password;
			}
			public void setPassword(String password) {
				this.password = password;
			}
		}
		
}
