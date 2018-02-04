package org.beyond_infinity.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.beyond_infinity.app.repository.VehicleOwnershipRepository;
import org.beyond_infinity.app.service.MemberService;
import org.beyond_infinity.app.service.VehicleOwnershipService;
import org.beyond_infinity.app.service.dto.MemberDTO;
import org.beyond_infinity.app.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberResource {

    private final Logger log = LoggerFactory.getLogger(MemberResource.class);

    private MemberService memberService;

    @Autowired
    public MemberResource(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    @Timed
    public ResponseEntity<List<MemberDTO>> getMembers(Pageable pageable) {
        log.debug("REST request to get Members");
        Page<MemberDTO> page = memberService.getMembers(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/members");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    @Timed
    public MemberDTO getMember(@PathVariable Long userId) {
        return memberService.getMember(userId);
    }
}
