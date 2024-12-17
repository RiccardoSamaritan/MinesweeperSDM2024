
### Test to implement
TESTS:
- minefield
  - grid 9 by 9 (tbd if 10 by 10)
  - grid contains 10 mines
  - numbers in cells are correct 
  - mines are placed randomly
- cells
  - once a cell is marked as 'flagged' not able to be marked as 'revealed'
  - once a cell is marked as 'revealed' not able to be marked as 'flagged'
- winning conditions
  - all cells except those with mines are marked as 'revealed'
- lose conditions
  - a cell with a mine is marked as 'revealed'
- game reset
  - reset the grid
  - reset the mines
  - reset the flags
  - reset the revealed cells
- endgame conditions
  - game over on game win
  - game over on game lose
  - game over on game reset
- user-server interaction
  - nothing happens when leftclicking or rightclicking on a revealed cell
  - mark an unrevealed cell as 'flagged' when rightclicked on it
  - mark an unrevealed cell as 'revealed' when leftclicked on it
  - delete the 'flagged' marker on a cell when rightclicking on an already marked as 'flagged' one
- graphical user interface 
  - render the grid correctly 
  - show the content of the cell once marked as 'revealed'
  - show a flag on the cell once marked as 'flagged'
  - remove the flag sprite once rightclicking on a cell marked as 'flagged'

TBD
- time and flag count 
  
