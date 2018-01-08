# HuluwaProject
**许卓尔 151220136@smail.nju.edu.cn**

## 开发环境

* IntelliJ IDEA 2017.2;
* jdk 1.8;
* Maven 3.5.2;

## 项目介绍

* HuluwaProject是一个展现葫芦娃与妖精大战的图形化应用；
* 支持随机战斗、战斗记录、记录回放功能；

## 对象设计

### Creature
* Creature作为抽象类，是所有生物类型的基类，需要实现Runnable、ViewController接口；
* Hulwa、Loulou继承Creature，并实现其中的抽象方法，两者以枚举类CAMP区分阵营；
* XieziJing继承Loulou，作为特殊的Loulou；
> OCP设计原则：absctract class的设计，将可供子类重写的设计为抽象方法，开放扩展；其它方法不可改变，关闭修改；
> 
> 里氏替换原则：所有Creature的子类均可替换Creature而不影响程序运行；
> 
> 依赖倒置原则：将需要实现的功能抽象为接口（ViewController等），留给下层实现，倒转了依赖关系；

![CreatureUML](https://github.com/Unkrible/java-2017f-homework/raw/master/Fianl%20Project/151220136-许卓尔/Huluwa/ptcs/CreatureUML.png)

### Action
* 抽象Action为接口，以实现生物的各种动作；
* Attack、Move实现Action接口，完成了生物的攻击与移动；
> 体现了依赖倒置原则，Creature的各种动作不需要依赖于动作的实现，而是通过接口交给下层实现；

### Position
* 由于课程开始，Huluwa是一个一维数组形式，设计了数据结构Position放置葫芦娃；
* 在二维空间内，利用继承实现了多态，扩展到二维空间，设计了PositionXY；
* 模仿C语言对二维数组的实现，增加N记录一维数组大小，增加y作为二维行的标识；
> 体现了里氏替换原则，PositionXY可以完全替代Position；

![PositionUML](https://github.com/Unkrible/java-2017f-homework/raw/master/Fianl%20Project/151220136-许卓尔/Huluwa/ptcs/PositionUML.png)


### UIView

* 设计了class View用于存储视图，接口ViewController作为与View具有聚合关系的类需要实现的接口；
* 从而实现了Model与View的分离；
* 由于后期需要添加一些动画效果，设计了接口Animate，并由泛化的Action去继承接口并实现动画效果；
> 单一职责原则：数据与视图是不同的职责，将其分离；
> CARP原则：尽量使用对象的组合，而不是通过继承关系达到了软件复用的目的；

### Field

* Field作为单例模式，确保运行时只有一个Field，并方便访问； 
* Field类是HuluwaProject的核心，在此利用Aggregation关系，耦合了许多类，如Creatures、View等等；
* Field继承了javax.swing.Jpanel，作为组件实现了图形化界面；

### Log

* 单例模式，通过静态变量recordNum控制记录文件的命名；
* 利用Java的io包，在战斗过程中进行战斗记录，在Field中实现记录解析复原战斗；

## 不足之处

* Field作为类太过冗余，没能很好的分离各项职责，抽离接口；
* Creature作为抽象类，实现了许多功能，还是当初没有设计好；
* PositonXY由于y这个变量经常需要使用，利用了RTTI进行了多次类型检查，影响了程序效率；
* 虽说做了一定的动画效果，但是界面仍然蛮丑的，有机会还是要多学学PS；

## 总结

* 在课堂上第一次了解到面向对象程序该如何设计，根据曹老板的commit记录学到了一点点如何一步步进行抽象的过程；
* 通过Java课程的学习，虽然说语法什么的都是自己看书学的，但是更重要的是在课上学到了很多设计原则，这是以前不会考虑的；
* 虽然现在的大作业写的一般般，但我会在剩余的时间里不断地修改，毕竟是跟了我一个学期的葫芦娃作业了，改到最后能让我满意吧；