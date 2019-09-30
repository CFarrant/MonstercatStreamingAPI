package farrant.christopher.api.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import farrant.christopher.api.model.User;

public interface UserJpaRepository  extends JpaRepository<User, Integer>{

}
