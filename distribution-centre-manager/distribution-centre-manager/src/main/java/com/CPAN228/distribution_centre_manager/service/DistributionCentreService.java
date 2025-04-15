package com.CPAN228.distribution_centre_manager.service;

import com.CPAN228.distribution_centre_manager.model.DistributionCentre;
import com.CPAN228.distribution_centre_manager.data.DistributionCentreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DistributionCentreService {

    @Autowired
    private DistributionCentreRepository distributionCentreRepository;

    public List<DistributionCentre> getAllDistributionCentres() {
        return distributionCentreRepository.findAll();
    }

    public DistributionCentre getDistributionCentreById(Long id) {
        return distributionCentreRepository.findById(id).orElse(null);
    }
    
    public DistributionCentre getDistributionCentreByName(String name) {
        return distributionCentreRepository.findByName(name);
    }

    public DistributionCentre addDistributionCentre(DistributionCentre distributionCentre) {
        return distributionCentreRepository.save(distributionCentre);
    }

    public DistributionCentre saveCentre(DistributionCentre centre) {
        return distributionCentreRepository.save(centre);
    }
    
    
    
}
