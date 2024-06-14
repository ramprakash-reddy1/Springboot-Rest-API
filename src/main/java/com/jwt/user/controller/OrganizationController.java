package com.jwt.user.controller;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.user.config.EmailUtil;
import com.jwt.user.dto.MemberRequest;
import com.jwt.user.entity.Member;
import com.jwt.user.entity.Organization;
import com.jwt.user.entity.Role;
import com.jwt.user.entity.User;
import com.jwt.user.repository.MemberRepository;
import com.jwt.user.repository.OrganizationRepository;
import com.jwt.user.repository.RoleRepository;
import com.jwt.user.repository.UserRepo;

import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/org")
public class OrganizationController {

	@Autowired
	private OrganizationRepository organizationRepository;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private EmailUtil emailUtil;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private MemberRepository memberRepository;

	@Value("${spring.mail.username}")
	private String fromMail;

	@GetMapping("/test")
	public String demo() {
		return "hello";
	}

	@PostMapping("/create-organization")
	public ResponseEntity<Organization> createOrganization(@RequestBody Organization organization) {
		Organization save = organizationRepository.save(organization);
		return ResponseEntity.status(HttpStatus.CREATED).body(save);
	}

	@PostMapping("/{organizationId}/members")
	public ResponseEntity<String> addMemberToOrganization(@PathVariable Integer organizationId,
			@RequestBody MemberRequest memberRequest) throws MessagingException {
		Optional<Organization> byId = organizationRepository.findById(organizationId);
		if (!byId.isPresent())
			return ResponseEntity.badRequest().body("Organization not found.");

		Organization organization = byId.get();

		Optional<User> userOptional = userRepo.findByEmail(memberRequest.getEmail());

		if (!userOptional.isPresent()) {

			String subject = "Inviting User to the organizatin";
			String text = "http://localhost:6060/auth/signup";
			emailUtil.sendEmail(memberRequest.getEmail(), subject, "For signup please, click the link below:\n" + text);
			return ResponseEntity.status(HttpStatus.PRECONDITION_REQUIRED).body("User needs to register first.");
		}

		User user = userOptional.get();
		Optional<Role> roleOptional = roleRepository.findByName(memberRequest.getRole());

		if (!roleOptional.isPresent())
			return ResponseEntity.badRequest().body("Role not found.");

		Role role = roleOptional.get();

		Optional<Member> byId2 = memberRepository.findById(user.getId());

		if (byId2.isPresent())
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body("Memeber already exist");

		Member member = new Member();
		member.setOrganization(organization);
		member.setUser(user);
		member.setRole(role);
		memberRepository.save(member);

		return ResponseEntity.ok("Member added successfully.");
	}

}
