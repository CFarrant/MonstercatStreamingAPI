package farrant.christopher.api.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import farrant.christopher.api.model.Playlist;

public interface PlaylistJpaRepository extends JpaRepository<Playlist, Integer>{

}
