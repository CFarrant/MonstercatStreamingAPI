package farrant.christopher.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="song")
public class Song {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="track_id", nullable=false)
	private int track_id;
	
	@Column(name="song_id", nullable=false)
	private String id;
	
	@Column(name="track_number", nullable=false)
	private int tracknumber;
	
	@Column(name="title", nullable=false)
	private String title;
	
	@Column(name="genre_primary", nullable=false)
	private String genreprimary;
	
	@Column(name="genre_secondary", nullable=false)
	private String genresecondary;
	
	@Column(name="url")
	private String songURL;
	
	@OneToOne
	@JoinColumn(name="artist_name", referencedColumnName="artist_name", nullable=false)
	private Artist artist;
	
	@OneToOne
	@JoinColumn(name="album_id", referencedColumnName="album_id", nullable=false)
	private Album album;
	
	public Song() {}
	
	public Song(String id, int track, String title, String primaryGenre, String secondaryGenre, String url, Artist artist, Album album) {
		this.id = id;
		this.tracknumber = track;
		this.title = title;
		this.genreprimary = primaryGenre;
		this.genresecondary = secondaryGenre;
		this.songURL = url;
		this.artist = artist;
		this.album = album;
	}

	public Album getAlbum() {
		return album;
	}

	public void setAlbum(Album album) {
		this.album = album;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getTracknumber() {
		return tracknumber;
	}

	public void setTracknumber(int tracknumber) {
		this.tracknumber = tracknumber;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getGenreprimary() {
		return genreprimary;
	}

	public void setGenreprimary(String genreprimary) {
		this.genreprimary = genreprimary;
	}

	public String getGenresecondary() {
		return genresecondary;
	}

	public void setGenresecondary(String genresecondary) {
		this.genresecondary = genresecondary;
	}

	public String getSongURL() {
		return songURL;
	}

	public void setSongURL(String songURL) {
		this.songURL = songURL;
	}

	public Artist getArtist() {
		return artist;
	}

	public void setArtist(Artist artist) {
		this.artist = artist;
	}
}