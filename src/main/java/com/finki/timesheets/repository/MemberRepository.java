package com.finki.timesheets.repository;

import com.finki.timesheets.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findAllByIsDeletedFalse();

    Member findByEmbg(String embg);
}