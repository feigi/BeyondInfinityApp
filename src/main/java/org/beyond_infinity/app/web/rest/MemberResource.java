package org.beyond_infinity.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.beyond_infinity.app.repository.VehicleOwnershipRepository;
import org.beyond_infinity.app.service.MemberService;
import org.beyond_infinity.app.service.VehicleOwnershipService;
import org.beyond_infinity.app.service.dto.MemberDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberResource {

    private MemberService memberService;
    private VehicleOwnershipService vehicleOwnershipService;

    @Autowired
    public MemberResource(MemberService memberService, VehicleOwnershipService vehicleOwnershipService) {
        this.memberService = memberService;
        this.vehicleOwnershipService = vehicleOwnershipService;
    }

    @GetMapping
    @Timed
    public List<MemberDTO> getMembers() {
        return memberService.getMembers();
    }

    @GetMapping("/{userId}")
    @Timed
    public MemberDTO getMember(@PathVariable Long userId) {
        return memberService.getMember(userId);
    }
}
