# 三国杀卡牌游戏开发文档

---

---

先从最基本的简单结构开始;  

_！~~~~ **这一版本只做结构，不写具体逻辑** ~~~~~_

1. 四人游戏，轮流出一张牌
2. 总是打出手中第一张牌
3. 目标都是下一个玩家
4. 只有杀和桃，没有点数花色
5. 牌堆数量固定，没有弃牌堆  
6. 没有身份
7. 在控制台输出，不做前端
8. 所有文件放在一起，先不考虑项目结构
  
跑起来再说  ！！！

---

### 需要的Class  
**玩家**、**卡牌** 是最基本的两个类，此外，需要一个管理卡牌的 **牌堆**，
和一个管理游戏进程的 **游戏类**

---

***Player: 玩家类***  
- **field：**  
    - name（String）
    - health (int) 体力/血量
    - handCard (List) &nbsp;手牌
- **method:**  
    - drawCar 摸牌
    - playCar 出牌
    - loseHealth 掉血
    - heal 回血
---

***Card: 卡牌类***
- **field：**
  - name（String） 牌名
- **method:**
  - use 被使用/效果
---

***Deck: 牌堆类***
- **field：**
  - drawPile（List）摸牌堆 
- **method:**
  - addCard 为牌堆加牌
  - deal  发牌
---

***Game: 游戏类***
- **field：**
  - players(List) 玩家表
  - deck 牌堆
- **method:**
  - gameStart 游戏运行逻辑
---