# BoardGame
board game making for term project of OOP.

# 2020.11.21#gurbur331

카톡으로 보내는게 읽기는 편해도, 길게 쓰면 한계가 있을 거 같아서 앞으로 작업 내용 같은 건 여기에 적기도 했어요. 추가로 작성하실 때에는 이 글이 밑으로 가도록, 이 위에 작성해주세요!

interface도 한번쯤은 써야할 거 같아서 한번 추가해 봤어요!
game이라는 패키지 안에 Game 이라는 인터페이스를 추가했는데, 게임 만드실 때 이걸 implements 해주셨으면 해요.
(안에 주석으로 간단히 설명을 적어두었어요. 참고하시면 더 좋을 것 같아요!)

그리고 게임 구조는 아래와 같이 통일했으면 좋겠어요:

1.__Tester(ex.CheckerTester, WarTester등)라는 클래스 안에 main매소드를 정의하여 실제 게임이 잘 만들어졌는지 테스트할 것.

2.__Game이라는 클래스 안에 기본적으로 Constructor, gameStart(), mainGame(), toString()을 정의할 것.

--2.1.생성자에서는 게임의 기본 설정(턴 순서 등)을 parameter로 받아서 저장할 것.
 
--2.2.gameStart():void에서는 동일 클래스의 mainGame()를 호출하는 역할. public으로 하여 외부 클래스에서는 이 메소드만 호출하여 게임을 시작하도록 해줄 것.
 
--2.3.mainGame():void에서는 실제 게임의 주요 부분을 진행하는 역할. private으로 하고, 여러가지 내부의 메소드들을 호출하여 게임이 원활히 진행되도록 해줄 것.
 
--2.4.toString():String을 오버라이딩하여 게임의 설명을 만들어 줄 것. public으로 선언하여 메인함수에서 사용할 수 있게 만들 것.

예시로 CheckerGame 오늘이나 내일 업로드 할 테니까 보시고 참고하시면 더 이해하기 편하실거라고 생각해요 :)
