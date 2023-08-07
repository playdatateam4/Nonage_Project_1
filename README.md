# miniproject3

프로젝트 기본 조건 
1.Spring + Mybatis 필수 사용
2. 기존 웹 서비스 + 개선하기 => SWOT 분석 필수
================
Nonage 쇼핑몰 프로젝트 개선

General Info


	i. Date : 2023.08.03.Thu - 08.08.Tue



Project & Team Info


	i. Team Name :

	ii. Members : 김정훈, 박정우, 백승주, 허찬

	iii. Project Name : T4_SHMall


Resource


i. Develop Environment : Oracle, Eclipse, GitHub

ii. 



Data


i. Oracle User :
	create user team4 identified by 1234;
	grant resource, connect to team4;


ii. 
부족한 / 없는 기능 파악 결과
사용자측 장바구니 상품 삭제 기능 (유저 담당)
- 삭제 자체는 문제가 없으나, 뒤로 가면 삭제 된 상품이 다시 장바구니 목록에 뜸.
여러 개의 상품을 한꺼번에 삭제할시 alert이 여러 번 뜸
상품 상세창에서 즉시 구매 누를 시 관리자의 결제 리스트로 넘어가야함 (유저 담당)

관리자-주문리스트에 주문번호가 내림차순으로 정렬되어 있지 않음(관리자) 
ㄴ 해결완료(order by result, oseq) => (order by result, odseq)
관리자 계정 로그아웃 후 메인 페이지로 연결되지 않음관리자
ㄴ해결완료 <a href 주소값 변경>
마이페이지 (유저 담당)
[완료]회원정보수정 기능 없음
[완료]
- [완료]회원 탈퇴 DB에 체크
- [완료]탈퇴 회원 로그인 블럭


관리자-상품리스트 기능에서 관리자
상품 수정기능 없음
상품삭제 기능 없음
사용유무를 반영하는 코드가 없음


사용자-상품 상세 페이지에 상품의 상세 설명이 없음 (유저 담당)

VI. 추가할 기능
관리자 페이지의 상품 삭제도 주문리스트처럼 바로 삭제 하지 않고 별도의 표시 후 관리(리스트에 띄운다는 뜻)
사용자 페이지의 상품 상세 창에 중고인지 여부 띄우기
관리자 페이지의 회원 정보 완전 삭제 기능 추가
상품 분류(세일,힐,운동화…)에 따라 상품 정보를 알맞은 분류창에 보내기
-> 분류가 세일이면 상품이 on sale 항목에 있어야한다는 의미임.
경로 재지정 -> 박정우,김정훈 담당
재고 체크해서 재고가 없으면 마감 등 관련 처리
할인 기능(쿠폰 등) -> 결제 창도 건드려야함


VII.	Errors

	i. 김정훈

ii. 박정우
		1. 
