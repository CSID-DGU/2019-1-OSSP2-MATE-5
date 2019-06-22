# OCR-based Illegal Advertising Block <br/> 
  using Text Recognition API Overview ,
  DDoS attack<br/>
<br/>

> ## Developer
<pre>
신유경 (2015112162) - 서버, 자동 전화 시스템 구축
이주원 (2017112064) - 서버, 자동 전화 시스템 구축, 팀장
정지연 (2017112078) - UI제작, 회원관리 시스템 구축
신성현 (2017112084) - 전화번호 추출, 회원관리 시스템 구축
</pre><br/>
 
> ## Development Environment
* Android studio 3.4
* Ubuntu 18.04 (SERVER)
* MySQL (DATABASE)
* WAS (for PHP to JSON)
![SERVICE](https://user-images.githubusercontent.com/48276522/59757414-a91de780-92c6-11e9-9544-9bb51c722df7.PNG)
<br/>

> ## How to run<br/>
<pre>
1. Run the Android studio
2. Select 'Check out project from Version Control'
3. git clone https://github.com/CSID-DGU/2019-1-OSSP2-MATE-5.git
4. Put the URL in Clone Repository
5. Run the application
</pre></br>

> ## Structure
![image](https://user-images.githubusercontent.com/48276633/59965267-a5989380-9546-11e9-900b-facbb88dec3b.png)
</br></br>

> ## How to use
<b>MATE_1 /Android-OCRSample-master</b></br>
<pre>
1. CHOOSE FROM GALLERY를 통해 앨범에 있는 전단지 사진을 가져오거나 TAKE A PHOTO을 통해 전단지 사진을 찍는다.
2. 전단지의 번호와 인식된 번호를 비교하며 일치하였을 경우, SUBMIT 버튼을 클릭해 데이터베이스에 번호를 전송한다.
3. 데이터베이스에 번호가 전송된 후 전송 성공을 알리는 success가 나타난다.
</pre>
<b>MATE_2 /Timer-master</b></br>
<pre>
1. 회원가입을 진행한다. 
2. 가입한 정보를 이용하여 로그인한다.
3. START 버튼을 클릭함으로써 데이터베이스에서 전화번호를 랜덤하게 읽어와 발신번호 표시 제한으로 전화를 걸기 시작하고 시간을 카운트한다.
4. STOP 버튼을 통해 자동 전화 시스템을 종료한다.
5. SEND 버튼을 클릭하여 시간을 데이터베이스에 전송하고 0으로 초기화한다.
</pre>
<br/>

> ## View
* <b>MATE_1 /Android-OCRSample-master<br/>Application to taking pictures of illegal advertising<br/><br/>
  ![mate_1](https://user-images.githubusercontent.com/48276522/59553640-6dd39e00-8fd2-11e9-807d-57a38ba0adc5.PNG)<br/>

* MATE_2 /Timer-master<br/>Automatic phone call application<br/><br/></b>
- 자원봉사자가 사용중인 애플리케이션 화면
  <img src ="https://user-images.githubusercontent.com/48276633/59965644-f2329d80-954b-11e9-9d3d-641808179e65.png" width="600" height="400"/><br/>
  ![그림2](https://user-images.githubusercontent.com/48276633/59965691-87ce2d00-954c-11e9-814b-73375b8a397d.png)<br/></br>

- 자원봉사자가 전화를 건 수신자 화면(발신번화표시제한)<br/>
  <img src= "https://user-images.githubusercontent.com/48276633/59965651-0c6c7b80-954c-11e9-863d-ffd7ca861233.png" width="200" height="400"/><br/>
<br/>

> ## Contact
<pre>
신유경 roodwl@naver.com
이주원 2017112064@dongguk.edu
정지연 jjy1000@dongguk.edu
신성현 xltmqp32@dongguk.edu
</pre></br>

> ## Original Opensource

* https://github.com/komamitsu/Android-OCRSample<br/>
* https://github.com/KayReid/Timer<br/>
* https://github.com/GaKaRi/gakari_android/tree/master/Registration_v4

<br/>
