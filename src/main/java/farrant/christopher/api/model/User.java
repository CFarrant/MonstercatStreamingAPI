package farrant.christopher.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="user_id", nullable=false)
	private int id;
	
	@Column(name="username", unique=true, nullable=false)
	private String username;
	
	@Column(name="hash", nullable=false)
	private String hash;
	
	@Column(name="salt", nullable=false)
	private byte[] salt;
	
	@Column(name="email", unique=true, nullable=false)
	private String email;
	
	@Column(name="admin", nullable=false)
	private boolean admin = false;
	
	@Column(name="recovery", nullable=false)
	private boolean recovery = false;

	public User() {}
	
	public User(boolean recovery) {
		this.recovery = recovery;
	}
	
	public User(String username, String hash, byte[] salt, String email) {
		this.username = username;
		this.email = email;
		this.hash = hash;
		this.salt = salt;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public byte[] getSalt() {
		return salt;
	}

	public void setSalt(byte[] salt) {
		this.salt = salt;
	}

	public boolean getAdmin() {
		return this.admin;
	}
	
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	
	public boolean isRecovery() {
		return recovery;
	}

	public void setRecovery(boolean recovery) {
		this.recovery = recovery;
	}
}
