package com.finki.timesheets.service.impl;

import com.finki.timesheets.model.Member;
import com.finki.timesheets.model.Timesheet;
import com.finki.timesheets.repository.MemberRepository;
import com.finki.timesheets.service.MemberService;
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

    @Override
    public void delete(Long id) {
        memberRepository.deleteById(id);
    }

    @Override
    public Member update(Member member) {
        return memberRepository.save(member);
    }

    @Override
    public Member save(Member member) {
        return memberRepository.save(member);
    }
}
