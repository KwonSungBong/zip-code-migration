# zipCodeMigration

docker-compose up -d

http://localhost:9999/test/index

------------------------------------------------------------------

신우편번호
- https://www.epost.go.kr/search/zipcode/areacdAddressDown.jsp
- 지역별 주소 DB 다운로드 후 압축해제 한 후 data/new_area 디렉토리에 추가

구우편번호
- http://www.epost.go.kr/search/zipcode/newAddressDown.jsp#
- 지역별 도로명주소 DB 다운로드 후 압축해제 한 후 data/old_area 디렉토리에 추가

인코딩
- http://kwanseob.blogspot.kr/2013/02/zip.html
- http://linuxmint.kr/tip/9119

unzip -O cp949 

------------------------------------------------------------------

1. 신주소, 구주소 테이블

------------------------------------------------------------------

show variables like 'char%';

ALTER DATABASE testtest CHARACTER SET utf8;