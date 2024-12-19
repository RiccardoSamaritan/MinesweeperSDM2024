
### Test to implement
TESTS:
- minefield
  - grid 9 by 9 (tbd if 10 by 10) DONE         
  - grid contains 10 mines DONE
  - numbers in cells are correct DONE
  - mines are placed randomly DONE
- cells
  - once a cell is marked as 'flagged' not able to be marked as 'revealed' DONE
  - once a cell is marked as 'revealed' not able to be marked as 'flagged' DONE
  - once a cell is marked as 'revealed', it can be revealed again DONE
  - If there are 3 mines in neighbour cells, getNumber() should return 3 DONE
  - Only mines in neighbour cells should be counted DONE
  - If there's no mine in neighbour cells, getNumber() should return 0 DONE
- winning conditions
  - all cells except those with mines are marked as 'revealed' DONE
- game over conditions 
  - a cell with a mine is marked as 'revealed' DONE
- user-server interaction
  - nothing happens when leftclicking or rightclicking on a revealed cell 
  - mark an unrevealed cell as 'flagged' when rightclicked on it
  - mark an unrevealed cell as 'revealed' when leftclicked on it
  - unflag a 'flagged' cell when rightclicked on it
- graphical user interface 
  - render the grid correctly 
  - show the content of the cell once marked as 'revealed'
  - show a flag on the cell once marked as 'flagged'
  - remove the flag sprite once rightclicking on a cell marked as 'flagged'

TBD
- time counter
- flag counter 
  
