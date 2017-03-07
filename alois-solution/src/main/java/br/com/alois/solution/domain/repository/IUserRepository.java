package br.com.alois.solution.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.alois.domain.entity.user.User;

@Repository
public interface IUserRepository extends JpaRepository<User, Long>
{
	@Query("FROM User u where u.username = :username AND u.password = :password")
	public User findUserByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
}
