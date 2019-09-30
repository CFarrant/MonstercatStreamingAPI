package farrant.christopher.api.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import farrant.christopher.api.AuthenticationService;
import farrant.christopher.api.exceptions.AuthenticationException;
import farrant.christopher.api.exceptions.NoSuchEntityException;
import farrant.christopher.api.exceptions.ReferencedByForeignKeyException;
import farrant.christopher.api.model.Artist;
import farrant.christopher.api.model.repository.ArtistJpaRepository;
import farrant.christopher.api.model.repository.UserJpaRepository;

@RestController
@RequestMapping("/artist")
public class ArtistRestController {
	
	@Autowired
	private AuthenticationService auth;
	
	@Autowired
	ArtistJpaRepository artists;
	
	@Autowired
	UserJpaRepository users;
	
	@RequestMapping(path="", method=RequestMethod.GET)
	public List<Artist> getAllArtists() {
		return artists.findAll();
	}
	
	@RequestMapping(path="/{artistId}", method=RequestMethod.GET)
	public Artist getArtistById(@PathVariable int artistId) {
		Optional<Artist> result = artists.findById(artistId);
		return result.orElseThrow(() -> new NoSuchEntityException(artistId, Artist.class));
	}
	
	@RequestMapping(path="", method=RequestMethod.POST)
	public Artist createArtist(@RequestBody Artist artist, HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		boolean[] authCheck = auth.authenticateUser(header, users);
		if(authCheck[0] == true && authCheck[1] == true) {
			if(artist.getName() != null) {
				artists.saveAndFlush(artist);
				return artist;
			}
			else {
				return null;
			}
		}	
		else {
			throw new AuthenticationException();
		}
	}
	
	@RequestMapping(path="/{artistId}", method=RequestMethod.DELETE)
	public void deleteArtist(@PathVariable String artistId, HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		boolean[] authCheck = auth.authenticateUser(header, users);
		if(authCheck[0] == true && authCheck[1] == true) {
			for(Artist a : artists.findAll()) {
				if (a.getName().equals(artistId)) {
					try {
						artists.delete(a);
					}
					catch (Exception ex) {
						throw new ReferencedByForeignKeyException();
					}
				}
			}
		}	
		else {
			throw new AuthenticationException();
		}
	}
	
	
}
