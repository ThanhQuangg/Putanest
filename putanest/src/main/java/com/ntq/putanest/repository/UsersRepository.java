package com.ntq.putanest.repository;

import com.ntq.putanest.pojo.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {
    Optional<Users>  findByEmail(String email);
    Optional<Users>  findByEmailAndPassword(String email, String password);
    Optional<Users> findByUsername(String username);
    List<Users> findByRole(String role);
}

