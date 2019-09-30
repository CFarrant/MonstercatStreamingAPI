package farrant.christopher.api.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import farrant.christopher.api.model.Artist;

public interface ArtistJpaRepository extends JpaRepository<Artist, Integer> {

}
