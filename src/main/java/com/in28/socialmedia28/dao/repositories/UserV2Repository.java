package com.in28.socialmedia28.dao.repositories;

import com.in28.socialmedia28.dao.entities.UserV2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserV2Repository extends JpaRepository<UserV2, Long> {

}
