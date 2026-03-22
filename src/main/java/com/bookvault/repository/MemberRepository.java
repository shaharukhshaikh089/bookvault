package com.bookvault.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookvault.entity.Member;

public interface MemberRepository extends JpaRepository<Member, UUID> {
	
	Optional<Member> findByEmailIgnoreCase(String email);
    boolean existsByEmailIgnoreCase(String email);
    List<Member> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String name, String email);

}
