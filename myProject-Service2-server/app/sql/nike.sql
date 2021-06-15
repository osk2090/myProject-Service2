-- 회원
DROP TABLE IF EXISTS client RESTRICT;

-- 게시글
DROP TABLE IF EXISTS board RESTRICT;

-- 드로우참여
DROP TABLE IF EXISTS draw RESTRICT;

-- 회원
CREATE TABLE client (
                        cno      INTEGER      NOT NULL COMMENT '회원번호', -- 회원번호
                        id       VARCHAR(40)  NOT NULL COMMENT '아이디', -- 아이디
                        password VARCHAR(100) NOT NULL COMMENT '비밀번호', -- 비밀번호
                        name     VARCHAR(50)  NOT NULL COMMENT '이름', -- 이름
                        tel      INTEGER      NOT NULL COMMENT '전화번호', -- 전화번호
                        birth    DATE         NOT NULL COMMENT '생년월일', -- 생년월일
                        admin    INTEGER      NOT NULL COMMENT '관리자권한' -- 관리자권한
)
    COMMENT '회원';

-- 회원
ALTER TABLE client
    ADD CONSTRAINT PK_client -- 회원 기본키
        PRIMARY KEY (
                     cno -- 회원번호
            );

-- 회원
ALTER TABLE client
    ADD CONSTRAINT CK_client -- 회원 체크 제약
        CHECK (admin = 1 or admin = 0);

ALTER TABLE client
    MODIFY COLUMN cno INTEGER NOT NULL AUTO_INCREMENT COMMENT '회원번호';

-- 게시글
CREATE TABLE board (
                       bno        INTEGER      NOT NULL COMMENT '글번호', -- 글번호
                       cno        INTEGER      NOT NULL COMMENT '회원번호', -- 회원번호
                       btitle     VARCHAR(255) NOT NULL COMMENT '제목', -- 제목
                       bcontent   VARCHAR(255) NOT NULL COMMENT '내용', -- 내용
                       bprice     INTEGER      NOT NULL COMMENT '가격', -- 가격
                       bmodel     VARCHAR(255) NOT NULL COMMENT '모델명', -- 모델명
                       bstartDate DATE         NOT NULL COMMENT '시작일자', -- 시작일자
                       bentDate   DATE         NOT NULL COMMENT '종료일자', -- 종료일자
                       bselStart  DATE         NOT NULL COMMENT '구매시작시간', -- 구매시작시간
                       bselEnd    DATE         NOT NULL COMMENT '구매종료시간', -- 구매종료시간
                       bpho1      VARCHAR(255) NOT NULL COMMENT '사진1', -- 사진1
                       bpho2      VARCHAR(255) NOT NULL COMMENT '사진2', -- 사진2
                       bpho3      VARCHAR(255) NOT NULL COMMENT '사진3', -- 사진3
                       bpho4      VARCHAR(255) NOT NULL COMMENT '사진4', -- 사진4
                       bpho5      VARCHAR(255) NOT NULL COMMENT '사진5', -- 사진5
                       bpho6      VARCHAR(255) NOT NULL COMMENT '사진6', -- 사진6
                       bpho7      VARCHAR(255) NOT NULL COMMENT '사진7', -- 사진7
                       bpho8      VARCHAR(255) NOT NULL COMMENT '사진8', -- 사진8
                       bpho9      VARCHAR(255) NOT NULL COMMENT '사진9', -- 사진9
                       bpho10     VARCHAR(255) NOT NULL COMMENT '사진10' -- 사진10
)
    COMMENT '게시글';

-- 게시글
ALTER TABLE board
    ADD CONSTRAINT PK_board -- 게시글 기본키
        PRIMARY KEY (
                     bno -- 글번호
            );

ALTER TABLE board
    MODIFY COLUMN bno INTEGER NOT NULL AUTO_INCREMENT COMMENT '글번호';

-- 드로우참여
CREATE TABLE draw (
                      cno     INTEGER     NOT NULL COMMENT '회원번호', -- 회원번호
                      bno     INTEGER     NOT NULL COMMENT '글번호', -- 글번호
                      size    VARCHAR(10) NOT NULL COMMENT '사이즈', -- 사이즈
                      winning INTEGER     NULL     COMMENT '당첨여부' -- 당첨여부
)
    COMMENT '드로우참여';

-- 드로우참여
ALTER TABLE draw
    ADD CONSTRAINT PK_draw -- 드로우참여 기본키
        PRIMARY KEY (
                     cno, -- 회원번호
                     bno  -- 글번호
            );

-- 드로우참여
ALTER TABLE draw
    ADD CONSTRAINT CK_draw -- 드로우참여 체크 제약
        CHECK (winning = 0 or winning =1);

-- 게시글
ALTER TABLE board
    ADD CONSTRAINT FK_client_TO_board -- 회원 -> 게시글
        FOREIGN KEY (
                     cno -- 회원번호
            )
            REFERENCES client ( -- 회원
                               cno -- 회원번호
                );

-- 드로우참여
ALTER TABLE draw
    ADD CONSTRAINT FK_client_TO_draw -- 회원 -> 드로우참여
        FOREIGN KEY (
                     cno -- 회원번호
            )
            REFERENCES client ( -- 회원
                               cno -- 회원번호
                );

-- 드로우참여
ALTER TABLE draw
    ADD CONSTRAINT FK_board_TO_draw -- 게시글 -> 드로우참여
        FOREIGN KEY (
                     bno -- 글번호
            )
            REFERENCES board ( -- 게시글
                              bno -- 글번호
                );