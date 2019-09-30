package farrant.christopher.api.controller;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import farrant.christopher.api.AuthenticationService;
import farrant.christopher.api.EmailService;
import farrant.christopher.api.PasswordService;
import farrant.christopher.api.exceptions.AuthenticationException;
import farrant.christopher.api.exceptions.NoSuchEntityException;
import farrant.christopher.api.exceptions.ValueNotUniqueException;
import farrant.christopher.api.model.User;
import farrant.christopher.api.model.repository.UserJpaRepository;

@RestController
@RequestMapping("/user")
public class UserRestController {
	
	@Autowired
	private AuthenticationService auth;
	
	@Autowired
	private EmailService emails;
	
	@Autowired
	private PasswordService passwords;
	
	@Autowired
	private UserJpaRepository users;
	
	@RequestMapping(method=RequestMethod.PUT, path="/change")
	private void changeUserByPATCH(@RequestBody Map<String, Object> newValues, HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		boolean[] authCheck = auth.authenticateUser(header, users);
		String[] credentials = auth.registerUser(header, users);
		if(authCheck[0] == true) {
			List<User> allUsers = users.findAll();
			User u = null;
			for (User user : allUsers) {
				if (user.getUsername().equals(credentials[0])) {
					u = user;
				}
			}
			if (u != null) {
				for(String s : newValues.keySet().toArray(new String[] {})){
					switch(s) {
					case "email":
						u.setEmail((String)newValues.get(s));
						break;
					case "password":
						if (u.isRecovery() == true) {
							u.setRecovery(false);
						}
						u.setHash(auth.getSecurePassword((String)newValues.get(s), u.getSalt()));
						break;
					default: 
						break;
					}
				}			
				users.saveAndFlush(u);
			}
		}
	}
	
	@RequestMapping(method=RequestMethod.GET, path="/login")
	private User loginUser(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		boolean[] authCheck = auth.authenticateUser(header, users);
		User us = null;
		if(authCheck[0] == true) {
			String[] credentials = auth.registerUser(header, users);
			for(User u : users.findAll()) {
				if (u.getUsername().equals(credentials[0]) && u.getHash().equals(auth.getSecurePassword(credentials[1], u.getSalt()))) {
					if (u.isRecovery() == true) {
						us = new User(true);
					}
					else us = new User(false);
				}
			}
			return us;
		}
		else throw new AuthenticationException();
	}
	
	@RequestMapping(method=RequestMethod.POST, path="/forgot")
	@Transactional
	private void forgotPassword(@RequestBody Map<String, Object> emailUsername) {
		List<User> allUsers = users.findAll();
		User u = null;
		for (User user : allUsers) {
			if (user.getUsername().equals((String)emailUsername.get("username")) 
					&& user.getEmail().equals((String)emailUsername.get("email"))) {
				u = user;
			}
		}
		if (u != null) {
			String newPassword = passwords.generateCommonLangPassword();
			try {
				u.setSalt(auth.getSalt());
			} catch (NoSuchAlgorithmException e1) {
				e1.printStackTrace();
			}
			u.setHash(auth.getSecurePassword(newPassword, u.getSalt()));
			u.setRecovery(true);
			String subject = "New Temporary Password";
			String message = "Dear "+u.getEmail()+", \n\r\n\r"
					+ "We are sorry that you forgot your password, "
					+ "here is a new temporary password that you can use to login..."
					+ "\n\r\n\r"
					+ "Password: "+newPassword+"\n\r\n\r"
					+ "Thanks for using the Monstercat Streaming Service!";
			try {
				emails.Send(u.getEmail(), subject, message);
			} catch (MessagingException e) {}
			users.saveAndFlush(u);
		}
		else {
			throw new NoSuchEntityException();
		}
	}
	
	@RequestMapping(method=RequestMethod.POST, path="")
	private void createUser(@RequestBody Map<String, Object> email, HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		String[] credentials = auth.registerUser(header, users);
		try {
			if (credentials[0] != null && credentials[1] != null && (String)email.get("email") != null) {
				String subject = "Welcome to the Monstercat Streaming Service";
				String message = "Dear "+(String)email.get("email")+", \n\r\n\r"
						+ "Thanks for your interest in using our service, "
						+ "we would like to wish you a warm welcome, "
						+ "and we also hope you enjoy the tunes..."
						+ "\n\r\n\r"
						+ "Thanks for using the Monstercat Streaming Service!";
				try {
					emails.Send((String)email.get("email"), subject, message);
				} catch (MessagingException e) {}
				byte[] salt = auth.getSalt();
				User u = new User(credentials[0], auth.getSecurePassword(credentials[1], salt), salt, (String)email.get("email"));
				if (credentials[0].equals("JSONParseTool") || credentials[0].equals("ChrisLeOtaku")) {
					u.setAdmin(true);
				}
				users.saveAndFlush(u);	
			}
		} catch (Exception e) {
			throw new ValueNotUniqueException();
		}
	}
}
