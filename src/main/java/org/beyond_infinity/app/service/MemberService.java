package org.beyond_infinity.app.service;

import org.beyond_infinity.app.domain.User;
import org.beyond_infinity.app.domain.VehicleOwnership;
import org.beyond_infinity.app.repository.UserRepository;
import org.beyond_infinity.app.repository.VehicleOwnershipRepository;
import org.beyond_infinity.app.service.dto.MemberDTO;
import org.beyond_infinity.app.service.mapper.MemberMapper;
import org.beyond_infinity.app.web.rest.util.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

@Service
public class MemberService {

    private UserRepository userRepository;

    private VehicleOwnershipRepository vehicleOwnershipRepository;

    private MemberMapper memberMapper;

    @Autowired
    public MemberService(UserRepository userRepository, VehicleOwnershipRepository vehicleOwnershipRepository,
                         MemberMapper memberMapper) {
        this.userRepository = userRepository;
        this.vehicleOwnershipRepository = vehicleOwnershipRepository;
        this.memberMapper = memberMapper;
    }

    public Page<MemberDTO> getMembers(Pageable pageable) {
        Page<User> users = userRepository.findByHiddenFalse(pageable);
        return users.map(user -> memberMapper.toDto(user, vehicleOwnershipRepository.findByOwner(user)));
    }

    public MemberDTO getMember(Long userId) {
        Optional<User> user = Optional.ofNullable(userRepository.findOne(userId));
        if (!user.isPresent()) {
            return null;
        }
        List<VehicleOwnership> ownerships = vehicleOwnershipRepository.findByOwner(user.get());
        return memberMapper.toDto(user.get(), ownerships);
    }
}
