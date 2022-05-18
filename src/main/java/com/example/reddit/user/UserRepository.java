package com.example.reddit.user;

import com.example.reddit.user.dto.dbProjection.UserInfo;
import com.example.reddit.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);
  Optional<User> findByEmail(String email);

  Optional<UserInfo> findByid(Long id);

  @Modifying
  @Query(
    value = "UPDATE user u SET u.post_amounts = u.post_amounts - 1" +
    " WHERE u.id = :userId",
    nativeQuery = true
  )
  void userPostAmountMinusOne(@Param("userId") Long userId);
}
