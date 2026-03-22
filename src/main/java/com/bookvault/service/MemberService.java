package com.bookvault.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.bookvault.entity.Member;
import com.bookvault.enums.MembershipStatus;
import com.bookvault.repository.MemberRepository;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member create(Member member) {
        if (member == null) {
            throw new RuntimeException("Member cannot be null");
        }
        if (member.getEmail() == null || member.getEmail().isBlank()) {
            throw new RuntimeException("Email is required");
        }
        if (member.getName() == null || member.getName().isBlank()) {
            throw new RuntimeException("Name is required");
        }
        if (memberRepository.existsByEmailIgnoreCase(member.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        if (member.getMembershipStatus() == null) {
            member.setMembershipStatus(MembershipStatus.ACTIVE);
        }
        member.setJoinedAt(LocalDateTime.now());
        return memberRepository.save(member);
    }

    public List<Member> getAll() {
        return memberRepository.findAll();
    }

    public Member getById(UUID id) {
        if (id == null) {
            throw new RuntimeException("Member ID cannot be null");
        }
        return memberRepository.findById(id).orElseThrow(() -> new RuntimeException("Member not found"));
    }

    public Member update(UUID id, Member updatedMember) {
        if (id == null) {
            throw new RuntimeException("Member ID cannot be null");
        }
        if (updatedMember == null) {
            throw new RuntimeException("Member cannot be null");
        }
        if (updatedMember.getEmail() == null || updatedMember.getEmail().isBlank()) {
            throw new RuntimeException("Email is required");
        }
        if (updatedMember.getName() == null || updatedMember.getName().isBlank()) {
            throw new RuntimeException("Name is required");
        }

        Member existing = memberRepository.findById(id).orElseThrow(() -> new RuntimeException("Member not found"));

        memberRepository.findByEmailIgnoreCase(updatedMember.getEmail())
                .filter(member -> !member.getId().equals(id))
                .ifPresent(member -> {
                    throw new RuntimeException("Email already exists");
                });

        existing.setEmail(updatedMember.getEmail());
        existing.setName(updatedMember.getName());
        existing.setMembershipStatus(updatedMember.getMembershipStatus() == null ? existing.getMembershipStatus() : updatedMember.getMembershipStatus());

        return memberRepository.save(existing);
    }

    public void delete(UUID id) {
        if (id == null) {
            throw new RuntimeException("Member ID cannot be null");
        }
        if (!memberRepository.existsById(id)) {
            throw new RuntimeException("Member not found");
        }
        memberRepository.deleteById(id);
    }

    public List<Member> search(String query) {
        if (query == null || query.isBlank()) {
            throw new RuntimeException("Search query cannot be empty");
        }
        return memberRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(query, query);
    }
}