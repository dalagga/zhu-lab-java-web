package com.example.webjavaserver.repository;

import com.example.webjavaserver.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer> {

    @Query(
            nativeQuery = true,
            value = "SELECT * FROM users " +
                    "WHERE (:searchAge IS NULL OR age = :searchAge) " +
                    "AND (:searchCity IS NULL OR city LIKE %:searchCity%)"
    )
    List<User> findWithParam(@Param("searchAge") Integer age, @Param("searchCity") String city);

}

