# BoardGame_Legacy
This is Legacy of old project, BoardGame.

# 2020.12.09#gurbur331
시험이랑 대체과제가 막 겹쳐서 시간이 안나서 이제 확인했네요 ㅠㅠ;

Mancala는 시간이 좀 더 필요할 것 같아요..ㅠ

Blackjack은 확인해봤는데 일부 코드가 누락된거같아요.

BlackJackPlayer class가 없다고 하네요.. 추가로 클래스 올려 주시면 확인하고 코드 수정 후에 BlackJack은 마무리 짓는걸로 할게요!

그리고 어제 교수님이 보내신 메일(공지)에 나와있듯이,

앞으론 우리가 쓰고있는 이 깃허브가 아니고, 학교 깃 랩으로 옮겨서 진행해야 할 것 같아요.

깃허브에서 깃랩으로 옮기는 작업은 제가 이번주 주말 전까지 찾아볼게요!

작성하신 코드 보면 정말 잘해주시고 계세요ㅠㅠ 원카드도 화이팅!!!

(+기한 늘린건 좋은데 크리스마스를 낀다니...(처참))

# 2020.12.07#jhy2018444
Blackjack 완성했습니다.

한가지 오류가 있는데 걸 돈을 입력받을 때(bet()에서) nextInt()에서 InputmismatchException이 발생할 때 trycatch로 해결이 안되네여...
그래서 우선 catch(InputmismatchException)는 제외하고 만들었습니다. 
중간에 player 클래스를 추가하게 되면서 시간이 좀 오래 걸렸네요,,,다음게임은 원카드로 할게요. 빨리 만들어볼게요!

# 2020.12.03#gurbur331

YachtDice완성!!

이제 만칼라 진행해볼게요

# 2020.11.28#gurbur331

war 만드신거 기반으로 수정했습니다. 보니까 게임 중간에 생기는 에러는 전부 NullPointerException이어서..ㅎㅎ

카드 뽑아서 사용한 후에 nullChekcer로 검사해주고, null인 자리가 있으면 카드를 새로 뽑아와서 그 자리 채워주는 형식으로 해결했습니다.

또, 특정 턴마다 카드있는 칸이 1, 3이나 2, 3 이런 식으로, 중간이 비어서 카드를 뽑을 수 없는 경우가 있었어요.(비겼거나, 턴이 지나서 카드가 필드에 2개밖에 없는 턴에 진입했을 때요.)

이때는 새로 카드를 뽑는게 아니고, 뒷 칸에있는 카드를 앞으로 당겨오는 식으로 해결했습니다.

그리고 작성하신 코드 보니까, 몇가지 지켜주셨으면 하는게 있어요.

1. 한줄에 코드 여러개 쓰지 않기: 가독성이 너무 떨어져서 읽고 수정하기 힘들어요 ㅠㅠ

2. 되도록이면 들여쓰기 맞춰주기: 이것도 가독성이 너무 떨어져요.. 그리고 java말고 다른언어 쓰시면 들여쓰기를 {}대신 사용하는 언어도 있어요. 들여쓰기 맞추시는게 나중에도 더 좋을 것 같아요.

이제 Yacht Dice진행할게요.. 매주 1개 하는 속도로는 프로젝트에 있는 게임목록 다 완성 못할거같은데 다른과목 과제는 계속 쏟아져 나오기만 하네요...ㅠㅠ

아예 게임 개수를 좀 줄이고 서버 쪽을 더 파서 가산점을 더 노리는 쪽도 생각해 봐야할지도 모르겠네요.

그래도 최대한 할수있는만큼 해봐요! 화이팅!!

# 2020.11.25#jhy2018444

이렇게 올리는거 맞나 모르겠네요 ㅎㅎ 저는 아직war인데 생각보다 오류처리할 게 많아서 오래걸리는 중이에요. 늦어도 오늘 자정전까지 올릴게요!
그리고 미안하지만 부탁이 있는데...ㅜㅜ 체커 다하셨지만 주석을 좀더 써주실수 있을까요?? war 만들때도 참고할 부분이 많아보여서요! 조오금만 부탁드려요

# 2020.11.25#gurbur331

체커 완성했습니다! 서버랑 GUI 짧게 살펴보고 다음게임 제작 시작할게요!

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
