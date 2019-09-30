package farrant.christopher.api.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import farrant.christopher.api.model.Song;

public interface SongJpaRepository extends JpaRepository<Song, String>{

}
