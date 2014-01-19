Thesis Game Levels:
====================

TUTORIAL
- let player walk to exit
- meet robot, move commands: left right
- interact command (get the data from the computer)

VARIABLES
Assign
- store comp to color (9 combinations)
- store comp1 to red and comp2 to blue etc (9? combinations)
- store comp1 to red, comp2 to blue, comp3 to yellow etc (9 combinations)

Operators
- red = comp1 + comp2
- red = comp1, blue = comp2, yellow = red + blue
- store comp1 to red, red to blue
- sum of all computers
- prodcut of all computers
- swap?

CONDITIONAL BRANCHING
- find the largest/smallest computer value and place it in a slot
- sort 3 numbers ascending ?
- sort 2 numbers descending ?
- check value of computer if > a value, if yes then do something
- check value of computer if > a value, if yes do something, else do something
- check if computer is divisible by x (comp%x store to red, if red == 0, true)

Largest Value of 3 computers solution:
store largest in yellow

yellow = comp1

move to comp2
if comp2 > yellow
	yellow = comp2

move to comp3
if comp3 > yellow
	yellow = comp3