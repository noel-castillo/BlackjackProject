## Blackjack Project

### Week 4 Homework Project for Skill Distillery

### Overview

### Technologies Used

### Lessons Learned

 In the enum Rank, ACE was given the int value of 11. Changing that value once in my program to convert from a 'hard' ace of value 11 to a 'soft' ace of value 1 per the rules of Blackjack, it would permanently chance all Rank.ACE's int value to 1. To workaround this flaw, I would decrease the value of the hand by 10 only if it is over 21 AND the hand contains a card with the Rank.ACE.

 The criteria for the dealer to hit or stand needs to follow quite a bit of rules. It was only through testing multiple times that I got to understand the loopholes that would cause the dealer to make an incorrect decision. Most significantly, I realized later on that if all the other players BUSTED but got high values, then the dealer would still try to beat them because the dealer's hand was lesser and below 17. This, among other loopholes, significantly prioritized the importance of testing my program very thoroughly and often. It is because I do not know what is wrong with it, that I must tinker with it to discover what is wrong with it. 
