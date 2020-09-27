package com.finki.timesheets.service;

import com.finki.timesheets.model.Output;

import java.util.List;

public interface OutputService {
    List<Output> saveAll(List<Output> outputs);

    void delete(Long id);

    Output findByDescription(String description);

    Output save(Output output);
}
