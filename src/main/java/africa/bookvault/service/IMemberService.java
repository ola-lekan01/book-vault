package africa.bookvault.service;

import africa.bookvault.models.Member;

import java.util.Optional;
import java.util.UUID;

public interface IMemberService {

    Member registerMember(Member member);

    Optional<Member> findById(UUID memberId);
}
