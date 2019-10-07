package farrant.christopher.api.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import farrant.christopher.api.AuthenticationService;
import farrant.christopher.api.exceptions.AuthenticationException;
import farrant.christopher.api.exceptions.ReferencedByForeignKeyException;
import farrant.christopher.api.model.Album;
import farrant.christopher.api.model.Song;
import farrant.christopher.api.model.repository.AlbumJpaRepository;
import farrant.christopher.api.model.repository.SongJpaRepository;
import farrant.christopher.api.model.repository.UserJpaRepository;

@RestController
@RequestMapping("/album")
public class AlbumRestController {

	@Autowired
	private AuthenticationService auth;
	
	@Autowired
	AlbumJpaRepository albums;
	
	@Autowired
	SongJpaRepository songs;
	
	@Autowired
	UserJpaRepository users;
	
	@RequestMapping(path="", method=RequestMethod.GET)
	public List<Album> getAllAlbums() {
		return albums.findAll();
	}
	
	
	@RequestMapping(path="/{albumId}", method=RequestMethod.GET)
	public List<Song> getAlbumById(@PathVariable String albumId) {
		List<Album> allAlbums = albums.findAll();
		List<Song> albumSongs = null;
		for(Album album : allAlbums) {
			if (album.getId().equals(albumId)) {
				albumSongs = new ArrayList<Song>();
				for(Song s : songs.findAll()) {
					if (s.getAlbum().getId().equals(albumId)) {
						albumSongs.add(s);
					}
				}
			}
		}
		return albumSongs;
	}

	@RequestMapping(method=RequestMethod.PUT, path="/{albumId}")
	@Transactional
	public Album updateAlbumById(@PathVariable String albumId, @RequestBody Map<String, Object> newValues, HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		boolean[] authCheck = auth.authenticateUser(header, users);
		if(authCheck[0] == true && authCheck[1] == true) {
			List<Album> allAlbums = albums.findAll();
			Album a = null;
			for(Album album : allAlbums) {
				if (album.getId().equals(albumId)) {
					a = album;
				}
			}
			if (a != null) {
				for(String s : newValues.keySet().toArray(new String[] {})){
					switch(s) {
					case "name":
						a.setName((String)newValues.get(s));
						break;
					case "type":
						a.setType((String)newValues.get(s));
						break;
					case "releaseCode":
						a.setReleaseCode((String)newValues.get(s));
						break;
					case "genreprimary":
						a.setGenreprimary((String)newValues.get(s));
						break;
					case "genresecondary":
						a.setGenresecondary((String)newValues.get(s));
						break;
					case "coverURL":
						a.setCoverURL((String)newValues.get(s));
						break;
					default: 
						break;
					}
				}			
				albums.saveAndFlush(a);
				return a;
			}
			else {
				return null;
			}			
		}
		else {
			throw new AuthenticationException();
		}
	}
	
	@RequestMapping(method=RequestMethod.DELETE, path="/{albumId}")
	@Transactional
	public void deleteAlbumByAlbumId(@PathVariable String albumId, HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		boolean[] authCheck = auth.authenticateUser(header, users);
		if(authCheck[0] == true && authCheck[1] == true) {
			List<Album> allAlbums = albums.findAll();
			for(Album album : allAlbums) {
				if (album.getId().equals(albumId)) {
					int i = 0;
					for(Song s : songs.findAll()) {
						if (s.getAlbum().getId().equals(albumId)) {
							i++;
						}
					}
					if (i == 0) {						
						albums.delete(album);						
					}
					else {
						throw new ReferencedByForeignKeyException();
					}
				}
			}		
		}
		else {
			throw new AuthenticationException();
		}
	}
	
	@RequestMapping(method=RequestMethod.POST, path="")
	@Transactional
	public Album createAlbum(@RequestBody Album album, HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		boolean[] authCheck = auth.authenticateUser(header, users);
		if(authCheck[0] == true && authCheck[1] == true) {
			if (album.getId() != null && album.getName()!= null && album.getType() != null && album.getReleaseCode() != null 
					&& album.getGenreprimary() != null && album.getGenresecondary() != null && album.getCoverURL() != null) {
				albums.saveAndFlush(album);
				return album;			
			}
			else {
				return null;
			}			
		}
		else {
			throw new AuthenticationException();
		}
	}
}
