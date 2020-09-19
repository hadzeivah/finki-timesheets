package com.finki.timesheets.service.impl;

import com.finki.timesheets.model.WorkingHoursSummary;
import com.finki.timesheets.repository.WorkingHoursSummaryRepository;
import com.finki.timesheets.service.WorkingHoursSummaryByMemberService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service("workingHoursSummaryByMemberService")
public class WorkingHoursSummaryByMemberServiceImpl implements WorkingHoursSummaryByMemberService {

    private WorkingHoursSummaryRepository workingHoursSummaryByMemberRepository;

    public WorkingHoursSummaryByMemberServiceImpl(WorkingHoursSummaryRepository workingHoursSummaryByMemberRepository) {
        this.workingHoursSummaryByMemberRepository = workingHoursSummaryByMemberRepository;
    }

    @Override
    public WorkingHoursSummary findByMemberIdAndDate(Long memberId, LocalDate date) {
        return this.workingHoursSummaryByMemberRepository.findWorkingHoursSummaryByMemberIdAndDate(memberId, date);
    }

    @Override
    public List<WorkingHoursSummary> findByMemberId(Long memberId) {
        return this.workingHoursSummaryByMemberRepository.findWorkingHoursSummaryByMemberId(memberId);
    }
}
