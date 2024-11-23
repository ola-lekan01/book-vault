package africa.bookvault.service.impl;

import africa.bookvault.models.Member;
import africa.bookvault.repository.MemberRepository;
import africa.bookvault.service.IMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements IMemberService {

    private final MemberRepository memberRepository;

    @Override
    public Member registerMember(Member member) {
        return memberRepository.save(member);
    }

    @Override
    public Optional<Member> findById(UUID memberId) {
        return memberRepository.findById(memberId);
    }
}
