## Blackjack Project

### Week 4 Homework Project for Skill Distillery

### Overview

### Technologies Used

### Lessons Learned

 In the enum Rank, ACE was given the int value of 11. Changing that value once in my program to convert from a 'hard' ace of value 11 to a 'soft' ace of value 1 per the rules of Blackjack, it would permanently chance all Rank.ACE's int value to 1. To workaround this flaw, I would decrease the value of the hand by 10 only if it is over 21 AND the hand contains a card with the Rank.ACE.
