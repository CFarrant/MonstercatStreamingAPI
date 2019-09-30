package farrant.christopher.api;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import org.springframework.stereotype.Service;

import farrant.christopher.api.model.User;
import farrant.christopher.api.model.repository.UserJpaRepository;

@Service
public class AuthenticationService {
	
	public boolean[] authenticateUser(String authHeader, UserJpaRepository users) {
		boolean authorized = false;
		String rawCredentials = new String(Base64.getDecoder().decode(authHeader.replace("Basic ", "")));
		String[] parts = rawCredentials.split(":");
		String username = parts[0].trim();
		String password = parts[1];
		String retrievedHash = "";
		byte[] retrievedSalt = null;
		boolean admin = false;
		for(User u : users.findAll()) {
			if (u != null) {
				if (u.getUsername().equals(username)) {
					retrievedHash = u.getHash();
					retrievedSalt = u.getSalt();
					admin = u.getAdmin();
				}				
			}
		}
		if (retrievedHash != null && retrievedSalt != null && retrievedHash.equals(getSecurePassword(password, retrievedSalt))) {
			authorized = true;
		}
		else {
			admin = false;
		}
		return new boolean[] {authorized, admin};
	}
	
	public String[] registerUser(String authHeader, UserJpaRepository users) {
		String rawCredentials = new String(Base64.getDecoder().decode(authHeader.replace("Basic ", "")));
		String[] parts = rawCredentials.split(":");
		parts[0] = parts[0].trim();
		return parts;
	}
	
	public byte[] getSalt() throws NoSuchAlgorithmException {
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
		byte[] salt = new byte[16];
		sr.nextBytes(salt);
		return salt;
	}

	public String getSecurePassword(String passwordToHash, byte[] salt) {
		String generatedPassword = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(salt);
			byte[] bytes = md.digest(passwordToHash.getBytes());
			StringBuilder sb = new StringBuilder();
			for(int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			generatedPassword = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return generatedPassword;
	}
}
