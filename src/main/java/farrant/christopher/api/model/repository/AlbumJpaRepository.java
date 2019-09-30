package farrant.christopher.api.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import farrant.christopher.api.model.Album;

public interface AlbumJpaRepository extends JpaRepository<Album, String>{

}
