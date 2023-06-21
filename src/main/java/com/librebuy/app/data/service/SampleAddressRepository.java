package com.librebuy.app.data.service;

import com.librebuy.app.data.entity.SampleAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SampleAddressRepository
        extends
            JpaRepository<SampleAddress, Long>,
            JpaSpecificationExecutor<SampleAddress> {

}
