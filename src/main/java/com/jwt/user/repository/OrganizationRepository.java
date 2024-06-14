package com.jwt.user.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jwt.user.entity.Organization;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization,Integer> {

}
