package com.mazhen.datacollector.repository;

import com.mazhen.datacollector.entity.PinganPuhuiUserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PinganPuhuiUserRepository extends JpaRepository<PinganPuhuiUserInfo, Long> {
}
