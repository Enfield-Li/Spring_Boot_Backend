package com.example.reddit.user;

import com.example.reddit.user.dto.db.UserInfo;
import com.example.reddit.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);
  Optional<User> findByEmail(String email);

  Optional<UserInfo> findByid(Long id);
}
