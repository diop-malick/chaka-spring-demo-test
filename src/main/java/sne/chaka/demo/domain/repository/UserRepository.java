package sne.chaka.demo.domain.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import sne.chaka.demo.domain.User;

public interface UserRepository extends CrudRepository<User, Long> {

	List<User> findByLastName(String lastName);
}
