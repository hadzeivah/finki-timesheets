package com.finki.timesheets.service.impl;

import com.finki.timesheets.model.Member;
import com.finki.timesheets.model.Project;
import com.finki.timesheets.repository.MemberRepository;
import com.finki.timesheets.repository.ProjectRepository;
import com.finki.timesheets.service.MemberService;
import com.finki.timesheets.service.ProjectService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "memberService")
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    @Override
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    @Override
    public Member findById(Long id) {
        return memberRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid member Id:" + id));
    }
}
