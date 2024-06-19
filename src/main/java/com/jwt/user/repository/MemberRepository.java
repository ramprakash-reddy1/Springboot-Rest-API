package com.jwt.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jwt.user.entity.Member;
import com.jwt.user.entity.User;

public interface MemberRepository extends JpaRepository<Member, Integer> {
	Optional<User> findByUserId(Integer id);

}
