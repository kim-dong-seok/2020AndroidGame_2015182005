# 2020AndroidGame_2015182005
*****************************************************************************************************

개발 일정

|날짜|계획|세부계획|진행도|
|5/1(월)|플레이어, 적 캐릭터 리소스 수집및 편집  |||
|6/3(수)||||
|6/5(금)||||
|6/6(토)||||
|6/8(월)||||
|6/10(수)||||
|6/12(금)||||
|6/13(토)||||
|6/15(월)||||
|6/17(수)||||
|6/19(금)||||





1. 게임 이름 : Octopath_TraveRun

2. 게임 장르 : 러닝 엑션 RPG게임

3. 게임 소개
    *달리면서 진행하는 주인공 8인의 모험담
    *쿠키런과 같은 횡스크롤 러닝 엑션을 기본으로 하며
    *로그라이크 처럼 한번의 플레이가 하나의 모험담이 되도록함
    *진행 중 캐릭터의 성장 마을 스테이지 등 RPG요소 추가

4. 게임 진행 흐름
    * 캐릭터 이동과 게임 진행은 자동으로 계속 진행된다. (ex 쿠키런, 템플런)
      단 캐릭터가 상호작용 중이라면 진행이 잠시 중지된다. 

      ![캡](https://user-images.githubusercontent.com/43131612/80436290-c5080600-8939-11ea-984f-1c0f9039cc8d.jpg)
   
    
    * 전투 스테이지-점프, 공격, 액션을 사용하여 적과 장애물을 통과한다.
      ![12](https://user-images.githubusercontent.com/43131612/80436375-f54fa480-8939-11ea-81f4-038d7885c3f7.jpg)   

    * 마을 스테이지-건물, NPC와 상호작용을 통해 아이템 구입과 퀘스트 진행을 한다.  (추가 구현)
      ![i13837856103](https://user-images.githubusercontent.com/43131612/80436198-938f3a80-8939-11ea-98c2-44a1ae93b3d4.jpg)
    
    * 상호작용에는 액션게이지 사용, 상점, NPC, 이벤트 오브젝트가 있다.

    * 한번 지나간 곳은 돌아갈수 없으며 상호작용 및 게임 진행과정은
      앞으로의 게임 난이도와 게임 결과에 영향을 준다.  

    * 액션 게이지는 캐릭터가 이동 및 일반 공격 적중, 피격 시 올라가며
      액션 게이지를 사용하여 아이템과 스킬을 사용 할 수 있다. 

5. 메인화면 UI 예시

    ![gamenews_RYS9we59jUo](https://user-images.githubusercontent.com/43131612/80436417-07314780-893a-11ea-8c03-6fd8f6bab1b2.jpg)
    
    * 유저 정보 - 현재 캐릭터의 체력, 마력, 경험치의 상태를 그래프로 보여주며
      클릭시 캐릭터의 상세 스테이터스를 보여준다.
    * 옵션 - 클릭시 옵션창을 띠우며 음량 등을 조정할 수 있다.
    * 점프 - 클릭시 캐릭터가 위로 점프한다.
    * 공격 - 근거리와 원거리가 있으며 근거리는 무기에 따른 범위로 전방을 공격한다.
               원거리는 무기에 해당하는 속도, 크기의 투사체를 발사한다.  
    * 엑션 버튼 - 엑션 게이지 상태를 보여준다.
      클릭시 게임진행이 멈추며 사용 가능한 아이템, 스킬 목록을 보여준다.
    
6. 클래스 구성도
   ![asdqewfgwfeqwwqewq](https://user-images.githubusercontent.com/43131612/80442870-d573ad00-8948-11ea-93c7-8312c642b527.JPG)
