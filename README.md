# 2019-1-OSSP2-MATE-5 <br/>
# OCR-based Illegal Advertising Block <br/> 
  using Text Recognition API Overview ,
  DDoS attack<br/>
  <pre>" 자원봉사자들로부터 수거된 전단지를 
  수사관들이 직접 프로그램에 번호를 입력하는 '단순노동' 일을 없애고 
  불법을 막는 일에 사람들을 적극적으로 참여시킨다. " </pre>
<br/>

> ## Developer
<pre>
신유경 (2015112162) - 서버 관리, 자동 전화 시스템 구축
이주원 (2017112064) - 서버 관리, 자동 전화 시스템 구축, 팀장
정지연 (2017112078) - UI제작, 회원관리 시스템 구축
신성현 (2017112084) - 전화번호 추출, 회원관리 시스템 구축
</pre><br/>
 
> ## Development Environment
* Android studio 3.4
* Ubuntu 18.04 (SERVER)
* MySQL (DATABASE)
* WAS (for PHP to JSON)
 <img src="https://user-images.githubusercontent.com/48276522/59757414-a91de780-92c6-11e9-9544-9bb51c722df7.PNG" width="600" heigth="300"/>
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
<img src="https://user-images.githubusercontent.com/48276633/59965267-a5989380-9546-11e9-900b-facbb88dec3b.png" width="500" height="350" />
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
  <img src="https://user-images.githubusercontent.com/48276633/59966144-f9a97500-9552-11e9-9968-958a4280820b.png" width="400" heigth="400"/><br/>

* MATE_2 /Timer-master<br/>Automatic phone call application<br/>
  <자원봉사자가 사용중인 애플리케이션 화면>
  <img src ="https://user-images.githubusercontent.com/48276633/59966025-1a70cb00-9551-11e9-8a1e-5aab47ecb5e7.png" width="700" height="400"/><br/>
  <img src="https://user-images.githubusercontent.com/48276633/59965992-9c142900-9550-11e9-9325-ee3c84c9750f.png" width="700" height="400"/><br/></br>

  <자원봉사자가 전화를 건 수신자 화면(발신번호표시제한)><br/></b>
  <img src= "https://user-images.githubusercontent.com/48276633/59966042-573cc200-9551-11e9-98bb-762e11c218b7.png" width="200" height="400"/><br/>
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
