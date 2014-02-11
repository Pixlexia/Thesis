GRAMMAR
==============


if := [if] [ident] [boolop] [ident] [statements...] [close if]

assignment := slot equal ident | slot equal ident arithop ident

ident := slot | interact

loop := loopX statements... endloop


========

example: right right if slot + <-- error

if slot > slot right right right endif

slot right right = 