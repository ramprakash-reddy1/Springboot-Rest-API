package com.jwt.user.controller;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.user.entity.Organization;
import com.jwt.user.repository.OrganizationRepository;

@RestController
@RequestMapping("/api/organization")
public class OrganizationController {

	@Autowired
	private OrganizationRepository organizationRepository;

	@GetMapping("/test")
	public String demo() {
		return "hello";
	}

	@PostMapping("/create-organization")
	public ResponseEntity<Organization> createOrganization(@RequestBody Organization organization) {
		Optional<Organization> byOrgName = organizationRepository
				.findByOrganizationName(organization.getOrganizationName());
		if (byOrgName.isPresent()) {
			throw new RuntimeException("Organistion is already exist");
		}

		Organization save = organizationRepository.save(organization);
		return ResponseEntity.status(HttpStatus.CREATED).body(save);
	}

	@GetMapping("/getOrganizationList")
	public ResponseEntity<List<Organization>> getOrganizationList() {
		List<Organization> list = organizationRepository.findAll();
		return ResponseEntity.ok().body(list);

	}

}
