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
import farrant.christopher.api.model.Song;
import farrant.christopher.api.model.repository.SongJpaRepository;
import farrant.christopher.api.model.repository.UserJpaRepository;

@RestController
@RequestMapping(path="/song")
public class SongRestController {
	
	@Autowired
	AuthenticationService auth;
	
	@Autowired
	SongJpaRepository songs;
	
	@Autowired
	UserJpaRepository users;
	
	@RequestMapping(method=RequestMethod.GET, path="")
	public List<Song> getAllSongs() {
		return songs.findAll();
	}
	
	@RequestMapping(method=RequestMethod.GET, path="/{songId}")
	public Song getSongById(@PathVariable String songId) {
		Song song = null;
		for(Song s : songs.findAll()) {
			if (s.getId().equals(songId)) {
				song = s;
			}
		}
		return song;
	}
	
	@RequestMapping(method=RequestMethod.GET, path="/album/{albumId}")
	public List<Song> getAllSongsByAlbumId(@PathVariable String albumId) {
		List<Song> song = new ArrayList<Song>();
		for(Song s : songs.findAll()) {
			if (s.getAlbum().getId().equals(albumId)) {
				song.add(s);
			}
		}
		return song;
	}
	
	@RequestMapping(method=RequestMethod.POST, path="")
	@Transactional
	public Song createSong(@RequestBody Song song, HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		boolean[] authCheck = auth.authenticateUser(header, users);
		if(authCheck[0] == true && authCheck[1] == true) {
			if (song.getId() != null && song.getTitle() != null && song.getGenreprimary() != null && song.getGenresecondary() != null 
					&& song.getTracknumber() >= 1 && song.getArtist() != null && song.getSongURL() != null) {
				songs.saveAndFlush(song);
				return song;			
			}
			else {
				return null;
			}			
		}
		else {
			throw new AuthenticationException();
		}
	}
	
	@RequestMapping(method=RequestMethod.PATCH, path="/{songId}")
	public Song updateSongById(@PathVariable String songId, @RequestBody Map<String, Object> newValues, HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		boolean[] authCheck = auth.authenticateUser(header, users);
		if(authCheck[0] == true && authCheck[1] == true) {
			Song song = null;
			for(Song s : songs.findAll()) {
				if (s.getId().equals(songId)) {
					song = s;
				}
			}
			if (song != null) {
				for(String s : newValues.keySet().toArray(new String[] {})){
					switch(s) {
					case "tracknumber":
						song.setTracknumber((int)newValues.get(s));
						break;
					case "title":
						song.setTitle((String)newValues.get(s));
						break;
					case "genreprimary":
						song.setGenreprimary((String)newValues.get(s));
						break;
					case "genresecondary":
						song.setGenresecondary((String)newValues.get(s));
						break;
					case "songURL":
						song.setSongURL((String)newValues.get(s));
						break;
					default: 
						break;
					}
				}
				songs.saveAndFlush(song);	
				return song;
			}
			else {
				return null;
			}
		}
		else {
			throw new AuthenticationException();
		}
	}
	
	@RequestMapping(method=RequestMethod.DELETE, path="/{songId}")
	public void deleteSongById(@PathVariable String songId, HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		boolean[] authCheck = auth.authenticateUser(header, users);
		if(authCheck[0] == true && authCheck[1] == true) {
			for(Song s : songs.findAll()) {
				if (s.getId().equals(songId)) {
					songs.delete(s);
				}
			}
		}
		else {
			throw new AuthenticationException();
		}
	}
}
