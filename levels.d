Thesis Game Levels:
====================

TUTORIAL
- use A and D to walk to the exit!
- press W to jump
- ladder, jump exit
- "hey! if you could program me to get to that computer, i could hack the mainframe and open the exit for you"
  press the command buttons on the sidebar to add them to the program slots, then press (button) to execute the program
  if you need help, click the robot or the ? icon
- move left right to pad thing
- "these computers contain valuable data that i need in order to unlock the gates"
  "the (computer) command allows me to access a computer if i am standing on its block"

VARIABLES

Assign
- 0 comp: store # to color/s
- 1 computer: store comp to color
- 2 computers: store 2 comps and/or 2 numbers to 2 colors
- 3 computers: store 3 comps and/or 3 numbers to 3 colors

Operators
- 0 comp:
	operation numbers (e.g. add # and #, sum to slot)
- 1 comp
	operation comp and number (sum of computer and # to slot)
- 2 comps
	operation all comps
	average of all comps
	swap
- 3 comps
	operation all comps
	average of all comps
	red = comp, yellow = comp * 2, blue = comp * 3
- 3+ comps
	operation of all comps
	average of all comps

CONDITIONAL BRANCHING
- 1 comp:
	check if comp > < == != value, put to red, else to yellow
- 2 comps:
	if comp1 == comp2, put to slot
	find larger/smaller numb place to slot
	get sum/avg of comps. if > #, put to red, else to yellow
- 3 comps:
	find largest/smallest place to slot
	sort 3 numbers red to blue
	get sum/avg of comps. if > #, put to red, else to yellow
- 3+ comps
	find largest/smallest place to slot

LOOPS
- 1 comp:
	movement patterns that use loops
- 1+ comps:
	a # value minus the values of all comps
- 3+ comps:
	find largest/smallest number
	add/multiply etc computers using loops
	get avg

FUNCTIONS
- 3+ comps:
	find largest/smallest number
	add all comps if comp is less than #

====

//- store comp to color (9 combinations)
//	'I need to store data 1 to red slot'
//	'Help me get the (computer) to yellow slot'
//	'All right, this time, my blue slot needs the data.'
//- store comp1 to red and comp2 to blue etc (9? combinations)
//	'Im gonna need one computer on my red slot, and another on my blue slot'
//	'Cool. It looks like I need the top computer on yellow, and the bottom one on red'
//	'Youre doing good. Now take the left one on blue, and the other on red'
//	'If you could put one computer data on red and another on blue thatd be awesome!'
//- store comp1 to red, comp2 to blue, comp3 to yellow etc (9 combinations)
//	'Im gonna need to store that computers data to each of my slots'
//	'That one on the left, I think that belongs to my red slot. The others can go on either blue or yellow'
//	'The first and 3rd one should go to red and blue. Also, I need any one of those on the bottom for yellow slot'