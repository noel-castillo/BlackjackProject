## Blackjack Project

### Week 4 Homework Project for Skill Distillery

### Overview

User is prompted whether to use a single deck of cards, or a multi-shoe deck with variations of 2, 4, or 8 decks.

User is then prompted to enter the number of players proceeded by entering the names for each player.

Game begins with each player's initial chip count starting at $100.00. Each round requires each player to bet $5.00 against the house. Then a hand is dealt to each player participating in the round.

Players can then select from a variety of options: hit, stand, display hand, or print chip count. Selecting hit will result in a player drawing another card. If the player BUSTS, meaning their hand value is over 21, then their turn ends and they will lose the round. The game proceeds to the next player. If the player selects stand, then they maintain their current hand value and the game proceeds to the next player. Selecting display hand will print out the current player's hand. Selecting print chip count will display the current player's chip count.

Once all player's turn has concluded. The dealer will then make a decision based on her/his current hand and all of the player's hands as well. If the dealer's hand is below any player's hand who hasn't busted, and if the dealer's hand is below 17, then the dealer will hit and draw another card.

Any player whose hand is greater than the dealer's and hasn't busted, will win the round and win $10.00 chips. If the player's hand is equal to the dealer's then that results in a push and neither side wins and the chips are returned.

After the game is concluded. The option is given for a player to return home and for the rest to continue playing. Or for all to continue playing to the next round. Everything is preserved moving onto the next round. If a player decides to go home, then s/he will no longer participate onto the next round.

Game will continue looping until all players go home.

### Lessons Learned

Implementing comments to explain functionality or purpose of methods & certain variables will not only help someone in reading the code, but it aided me as the programmer in further understanding how everything worked and provided me insight in ways to better present my code and cut back on certain redundancies.

Implementing classes for nouns found within the original BlackjackApp decluttered my code and helped me further understand the functionality of the code written. It also bolstered my capabilities in implementing a major change to code already written.

In the enum Rank, ACE was given the int value of 11. Changing that value once in my program to convert from a 'hard' ace of value 11 to a 'soft' ace of value 1 per the rules of Blackjack, would result in permanently changing all Rank.ACE's int value to 1. To workaround this flaw, I decided to not alter the value. Instead, I would change the getValue method and implement code to decrease the value of the hand by 10 only if it is over 21 and per each card with the Rank.ACE and only until it is below 21. In the case of double ACES, the hand value would result in 12.

The criteria for the dealer to hit or stand needs to follow quite a bit of rules. It was only through testing multiple times that I got to understand the loopholes that would cause the dealer to make an incorrect decision. Most significantly, I realized later on that if all the other players BUSTED but got high values, then the dealer would still try to beat them because the dealer's hand was lesser and below 17. This, among other loopholes, significantly prioritized the importance of testing my program very thoroughly and often.
