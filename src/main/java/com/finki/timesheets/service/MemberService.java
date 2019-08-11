package com.finki.timesheets.service;

import com.finki.timesheets.model.Member;
import com.finki.timesheets.model.Project;

import java.util.List;

public interface MemberService {
    List<Member> findAll();

    Member findById(Long id);

    void delete(Long id);

    Member update(Member member);

    Member save(Member member);
}
