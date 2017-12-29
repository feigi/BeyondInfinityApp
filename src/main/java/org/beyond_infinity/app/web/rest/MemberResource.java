package org.beyond_infinity.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.beyond_infinity.app.config.Constants;
import org.beyond_infinity.app.service.MemberService;
import org.beyond_infinity.app.service.dto.MemberDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MemberResource {

    private MemberService memberService;

    @Autowired
    public MemberResource(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members")
    @Timed
    public List<MemberDTO> getMembers() {
        return memberService.getMembers();
    }

    @GetMapping("/members/{userId}")
    @Timed
    public MemberDTO getMember(@PathVariable Long userId) {
        return memberService.getMember(userId);
    }
}
