package com.finki.timesheets.service.impl;

import com.finki.timesheets.model.Output;
import com.finki.timesheets.repository.OutputRepository;
import com.finki.timesheets.service.OutputService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service(value = "outputService")
public class OutputServiceImpl implements OutputService {

    private final OutputRepository outputRepository;

    public OutputServiceImpl(OutputRepository outputRepository) {
        this.outputRepository = outputRepository;
    }

    @Override
    public List<Output> saveAll(List<Output> outputs) {
        return this.outputRepository.saveAll(outputs);
    }

    @Override
    public void delete(Long id) {
        this.outputRepository.deleteById(id);
    }

    public Output findByDescription(String name) {
        return this.outputRepository.findByDescription(name);
    }
}
