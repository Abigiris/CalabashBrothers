# 概述

### 情节描述

​	在二维空间的”战场“上，所有生物各自占据一个“位置”，总共分为“正”“反”两个阵营，列队在战场两侧。

- 正方：包括葫芦娃七兄弟和爷爷，大本营在战场左侧。战斗开始前，葫芦娃按照排行列队成一字长蛇阵，与爷爷一起站在左侧的大本营内。

- 反方：包括蝎子精、蛇精和若干小喽啰，大本营在战场右侧。战斗开始前，小喽啰们列队成某种阵型（如衡轭阵），与蝎子精和蛇精一起站在右侧的大本营内。

| 阵营 | 生物   | 速度       | 生命值 | 伤害值 | 技能             |
| ---- | ------ | ---------- | ------ | ------ | ---------------- |
| 正方 | 葫芦娃 | 500（快）  | 600    | 200    | 移动、攻击       |
| 正方 | 爷爷   | 1000（慢） | 1000   | 100    | 移动、攻击、治疗 |
| 反方 | 蝎子精 | 800        | 800    | 300    | 移动、攻击       |
| 反方 | 蛇精   | 800        | 800    | 100    | 移动、攻击、治疗 |
| 反方 | 小喽啰 | 500        | 400    | 200    | 移动、攻击       |

- 战斗开始后，两方生物相向进发，每个个体可以较为随意地选择下一步的方向。如果自身“上”“下”“左”“右”有距离小于1的敌人，则优先与敌人进行战斗。
- 正方阵营作为正义的代表，发现反方的敌人后优先攻击。如若被攻击方一击之后未阵亡，则会进行反击，正方受到攻击。
- 生物受到攻击，生命值将下降，下降值与敌方伤害值相等。当生物的生命值<=0时，生物阵亡。
- 正方的爷爷、反方的蛇精，生命值较高，可以在战斗进行一段时间后，定时为各自阵营“加油”进行治疗，提升全体未阵亡队友的血量。治疗的量为自身最大生命值的10%，且被治疗方生命值不会超过自身最大生命值。每次治疗会降低自身生命值，为自我保护，当自身生命值不足25%时不再进行治疗。
- 两方的大本营在战场的左右两边，中间部分划分为战斗地带。生物进行移动时，在自己的大本营内时不可后退，深入到敌方大本营后不再前进，最终保证双方在战场的中间地带快速结束战斗。
- 当战场上一方阵营的生物全部阵亡时，战斗结束。



### 操作说明

​	用户用键盘控制各种事件的发生。

| 按键  | 响应                                   |
| ----- | -------------------------------------- |
| SPACE | 战斗开始，双方生物自动战斗             |
| S     | 保存刚结束的战斗记录至外部文件中       |
| L     | 选择读取外部文件，自动回放文件中的内容 |
| C     | 清理战场，清空所有生物                 |
| R     | 重新开始                               |



# 设计

### 面向对象

​	本应用采用面向对象编程方法，主要包括“战场”、“生物”、“阵型”、“记录仪”等类进行具体的实现。

- 战场：应用的主体，包含各种生物，可以进行队形的变换等行为。

- 生物：派生出葫芦娃、爷爷、小喽啰等具体的生物类，拥有生物的属性以及“移动”等行为。每个生物在实现时具有自己的线程。

- 阵型：用于改变战场上生物的位置。

- 记录仪：记录战斗过程中每个生物在不同时间的状态，以及将记录以线程形式回放。记录仪要能跟外部文件进行交互。



# 实现

### main

​	类Main继承了Application，为GUI进行初始化。初始化的内容是新建了一个“战场”map，并通过stage、scene、group将map中的内容显示到图形界面上。

​	此外，编写scene的键盘事件scene.setOnKeyPressed()，重载其中处理事件，对键盘做出响应。

``` java
			scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
				public void handle(KeyEvent event) {
					switch (event.getCode()) {
		            case SPACE:  ...
		            case S: ...
		         	}
		         }
		     });
```



### map

- **Position**类作为一个”位置“，拥有坐标的特性，利用泛型表示其上可以站立一个生物（``<T extends Creature>``）。其方法主要是用于获得坐标信息，以及获取、放置、清除其上生物的。由于使用并发编程，需要对位置生物这个临界区资源进行处理，在此对这些方法加上``synchronized``，并且做了有关多个生物占据一个位置的异常处理。

- **Map**类包含各种生物、一个记录仪以及GUI界面的相关参数，具体来说是描述战场大小的行列数，地图上所有位置的二维数组``Position[][]``，战斗时间的记录，葫芦兄弟、爷爷、蝎子精、蛇精、集合框架``ArrayList<>``形成的若干小喽啰，一个记录仪类型``RecordPlayer``类型的对象，以及为所有生物和记录仪形成的线程。

  Map中的方法主要是对其上的生物进行调度，并显示在GUI上：

  - ``void initMap(); //初始化战场，让所有生物列队排列在两侧，记录仪初始化记录下开始位置``
  - ``void clearMap(); //清理战场，将所有生物、记录仪信息清空``
  - ``void printMap(); //打印战场，通过位置与界面的映射关系，设置生物图形的位置，将各个生物在战场上的分布状态显示在图形界面上``
  - ``void battle(); //开始自动战斗，所有生物线程执行start()``
  - ``boolean battleFinished();//判断战斗是否结束，即是否有一方全部阵亡``
  - ``void replay(); //清理战场后启动记录仪的线程，开始进行回放``


### creature

- **Creature**类作为基类``implements Runnable``，拥有编号、姓名、阵营划分、对应的图像资源、是否存活、速度、生命值、伤害值等属性。其方法主要是用于进行移动、攻击等行为：

  - ``void move(Position); //移动到一个位置``

  - ``void autoMove(); //自动移动，是用于自动战斗的重要方法``

    - 生物自动移动的时候，会判断其上下左右四个位置是否能被自身占用。当遇到边界或者是相邻位置上有队友的时候，不再向该方向前进；当遇到敌人时，优先进行攻击，随机在相邻的敌人中选择一个调用``attackCre(Creature)``；如果身边有空闲位置，也可以移动到此处，只是需要注意“集中在中间战斗地带活动”的原则；生物也可以选择停滞不前。

    - 在生物进行移动时，需要向记录仪中记录一条移动的信息，包括生物的标识、活动发生的时间和位置的变更。

    - 移动后可刷新图形界面，因为javafx主线程的问题，此处这样刷新：

      ```java
      		Platform.runLater(new Runnable() {
      		    public void run() { 
      		        map.printMap();
      		    }
      		});
      ```

  - ``void attackCre(Creature); //攻击某一生物，双方都要受到伤害``
  - ``void hurt(Creature); void beHurt(int damage); //受到伤害减少生命值，过低则死亡``
  - ``void beKilled(); //自身阵亡，需要更新一些信息，并且写入记录里``
  - ``void run(); //线程启动后，只要生物不阵亡就调用autoMove()，通过Thread.sleep()和生物的speed来模拟生物移动的速度``

- **CalabashBody**与**CalabashBrother**类是用来表示葫芦娃的，前者``extends Creature``，表示一个葫芦娃个体，后者拥有一个``ArrayList<CalabashBody>``的七兄弟集合，包含了葫芦娃的排序等内容。
- **GrandFather**和**SnakeMonster**类分别表示爷爷和蛇精，二者都``extends Creature implements Cheer``，不仅拥有生物的特性，还实现了一个“加油”的功能，即进行治疗。重写了二者的``void run()``，增加了定时自动治疗的功能。作为“奶妈”，二者的生命都较高，伤害较低。
- **ScorpionMonster**与**Minion**类是蝎子精和小喽啰，派生生物，没有较特殊的功能。



### formation

- **Formation**是一个“队形”的抽象类，``void form()``和``void loose()``进行“列队”和“解散”。
- 其余各个队形都是在map上移动一组creature。



### record

- **Record**类记录一条信息，分为“CREATE（创造生物）”、"MOVE（生物移动）"、"DIE（生物阵亡）"三类。每条信息记录了一个生物的编号、信息的类型、发生的时间、发生的位置、生物当前生命值等内容。

- **RecordPlayer**类实现了一个记录仪的功能，含有一个``ArrayList<Record>``的列表来存放一次战斗的所有内容，另外有一组独立的``ArrayList<Creature>``来用于回放。其方法主要是对于记录的读写，以及重新布置战场上的信息，此时的攻击等细节不再展示：
  - ``void readRecordFile(File); //用于读取一个外部文件，并将转化成记录列表中的内容``
  - ``void writeRecordFile(File); //将记录列表的内容写入外部文件，读写的格式要相对应``
  - ``Record newCreateRecord(); //构造一条“创造生物”的记录，要包括生物编号、图片资源、位置、生命值``
  - ``Record newMoveRecord(); //构造一条“生物生物”的记录，要包括生物编号、位置、生命值、发生的时间``
  - ``Record newDieRecord(); //构造一条“生物阵亡”的记录，要包括生物编号、发生的时间``
  - ``void run(); //遍历记录列表，在map上进行相应的操作，也需要实时刷新图形界面``



# 配置说明

- GUI框架：javafx
- java版本：jdk1.8.0
- 战斗记录文件：存放在根目录records文件夹下