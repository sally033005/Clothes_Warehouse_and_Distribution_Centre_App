package com.CPAN228.distribution_centre_manager.data;

import com.CPAN228.distribution_centre_manager.model.DistributionCentre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistributionCentreRepository extends JpaRepository<DistributionCentre, Long> {
    DistributionCentre findByName(String name);
}
