package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) //데이터 변경이 필요한 작업엔 Transactional 어노테이션이 있어야한다.
// 가급적이면 읽기 전용일때 readOnly true로 해주어야 과부하가 적다
@RequiredArgsConstructor //final 붙은 객체를 Autowired해줌
public class MemberService {

    private final MemberRepository memberRepository;

    //회원 가입
    @Transactional //직접 설정을 해주면 우선권을 가진다 @Transactional은 기본 옵션이 readOnly = false임
    public Long join(Member member) {
        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        //EXCEPTION
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원 전체 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    //한명만 조회
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

}
