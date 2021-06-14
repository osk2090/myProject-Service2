# myProject-Service2

수정해야 될것
1.현재 1번만 뽑히는 에러가 있음
2.당첨자를 저장할 수 있는 테이블 생성하기
3.당첨자 수령하기 메뉴에서 넘어가질 않음

4.# 30-c까지 진행!
clientService.add(c);
현재 add할때 저 포인트에서 NPE이 뜬다 확인 바람!




    /*
     int no;//인덱스 번호
     String name;//응모자 이름
     String pN;//응모자 전화번호
     String bN;//응모자 생년월일
     String id;//응모자 나이키 아이디
     int cSize;//응모자 사이즈
    */


create table pms_client(
no int not null,
name varchar(30) not null,
phone_number varchar(30),
birth_number varchar(30),
id varchar(50) not null,
cSize int not null
);

alter table pms_client
add constraint oms_client_uk primary key(no);

alter table pms_client
modify column no int not null auto_increment;

alter table pms_client
add constranint pms_client_uk unique (id);
