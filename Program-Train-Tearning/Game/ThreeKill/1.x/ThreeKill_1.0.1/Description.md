# 三国杀卡牌游戏开发文档

---

---

**_在 1.0.0 的基础上，写入具体逻辑；_**
+ Card、Player 写入构造方法，用于生成牌和玩家
+

---

1. 四人游戏，轮流出一张牌
2. 目标都是下一个玩家
3. 只有杀和桃，没有点数花色
4. 牌堆数量固定，没有弃牌堆  
5. 没有身份
6. 所有文件放在一起，先不考虑项目结构

---

### 需要的Class  
**玩家**、**卡牌** 是最基本的两个类，此外，需要一个管理卡牌的 **牌堆**，
和一个管理游戏进程的 **游戏类**

---

***Player: 玩家类***  
- **field：**  
    - name（String）
    - handCard (List) &nbsp;手牌
- **method:**  
    - drawCar 摸牌
    - playCar 出牌
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
  - deal 发牌
---

***Game: 游戏类***
- **field：**
  - players(List) 玩家表
  - deck 牌堆
- **method:**
  - gameStart 游戏运行逻辑
---