package farrant.christopher.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="album")
public class Album {

	@Id
	@Column(name="album_id", nullable=false)
	private String id;
	
	@Column(name="name", nullable=false)
	private String name;
	
	@Column(name="type", nullable=false)
	private String type;
	
	@Column(name="release_code", nullable=false)
	private String releaseCode;
	
	@Column(name="genre_primary", nullable=false)
	private String genreprimary;
	
	@Column(name="genre_secondary", nullable=false)
	private String genresecondary;
	
	@Column(name="cover_url", nullable=false)
	private String coverURL;
	
	@OneToOne
	@JoinColumn(name="artist_name", referencedColumnName="artist_name", nullable=false)
	private Artist artist;
	
	public Album() {}
	
	public Album(String id, String name, String type, String release, String primary, String secondary, String url, Artist a) {
		this.id = id;
		this.name = name;
		this.coverURL = url;
		this.type = type;
		this.releaseCode = release;
		this.genreprimary = primary;
		this.genresecondary = secondary;
		this.artist = a;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getReleaseCode() {
		return releaseCode;
	}

	public void setReleaseCode(String releaseCode) {
		this.releaseCode = releaseCode;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCoverURL() {
		return coverURL;
	}

	public void setCoverURL(String coverURL) {
		this.coverURL = coverURL;
	}

	public Artist getArtist() {
		return artist;
	}

	public void setArtist(Artist artist) {
		this.artist = artist;
	}
}
