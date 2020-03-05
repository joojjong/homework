package com.joojognhun.homework.store;

import com.joojognhun.homework.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserEntity, String> {
    UserEntity findByUserIdAndPassword(String id, String pw);

}
