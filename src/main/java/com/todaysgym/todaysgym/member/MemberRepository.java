package com.todaysgym.todaysgym.member;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import com.todaysgym.todaysgym.member.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {


    Optional<Member> findByEmail(String email);

    Optional<Member> findByNickName(String nickname);

    Optional<Member> getByMemberId(long memberId);
}
