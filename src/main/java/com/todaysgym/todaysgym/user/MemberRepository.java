package com.todaysgym.todaysgym.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {


    Optional<Member> findByEmail(String email);

    Optional<Member> findByNickName(String nickname);

    Optional<Member> getByMemberId(long memberId);
}
