package farrant.christopher.api.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="playlist")
public class Playlist {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="playlist_id", nullable=false)
	private int playlist_id;
	
	@Column(name="playlist_name", nullable=false)
	private String name;
	
	@Column(name="user_id", nullable=false)
	private int user_id;
	
	@ManyToMany
	@Column(name="songs_list", nullable=false)
	private List<Song> songs;
	
	public Playlist() {
		songs = new ArrayList<Song>();
	}
	
	public Playlist(int user_id, String name, List<Song> songs) {
		this.user_id = user_id;
		this.name = name;
		this.songs = songs;
	}

	public int getPlaylist_id() {
		return playlist_id;
	}

	public void setPlaylist_id(int playlist_id) {
		this.playlist_id = playlist_id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public List<Song> getSongs() {
		return songs;
	}

	public void setSongs(List<Song> songs) {
		this.songs = songs;
	}
}
