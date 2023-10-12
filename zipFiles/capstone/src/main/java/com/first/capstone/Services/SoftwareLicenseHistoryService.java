package com.first.capstone.Services;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.first.capstone.Entity.SoftwareLicenseHistory;
import com.first.capstone.Respositories.SoftwareLicenseHistoryRepository;

@Service
public class SoftwareLicenseHistoryService {

    private final SoftwareLicenseHistoryRepository softwareLicenseHistoryRepository;

    @Autowired
    public SoftwareLicenseHistoryService(SoftwareLicenseHistoryRepository softwareLicenseHistoryRepository) {
        this.softwareLicenseHistoryRepository = softwareLicenseHistoryRepository;
    }

    public List<SoftwareLicenseHistory> findAllSoftwareLicenseHistory() {
        return softwareLicenseHistoryRepository.findAll();
    }

    public SoftwareLicenseHistory saveSoftwareLicenseHistory(SoftwareLicenseHistory softwareLicenseHistory) {
        return softwareLicenseHistoryRepository.save(softwareLicenseHistory);
    }


}
