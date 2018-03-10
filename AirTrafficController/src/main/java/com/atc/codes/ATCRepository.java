package com.atc.codes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ATCRepository extends JpaRepository<AirCraft, Long>{

}
