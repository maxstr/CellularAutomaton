Cellular Automaton
=====

This is a Java program implementing two different types of cellular automata.

Type 1
----
Cells are of two values: occupied(+), unoccupied(-). They obey the following rules:
- if an occupied cell has more than 5 neighbors, it "starves" (i.e. becomes unoccupied)
- if an occupied cell is an "edge" cell (located on the border of the grid), it starves if it has more than 3 neighbors.
- if an unoccupied cell has more than 2 neighbors, it has a 30% chance to spawn, based on a random number generator

Type 2
----
Cells are of 3 possible values: X(x), Y(y), and unoccupied. They obey the following rules:
- if an X cell has less than 2 X neighbors, it moves to an unoccupied cell with at least two X neighbors if one can be found, otherwise it stays in place
- if an Y cell has no Y neighbors, it moves to an unoccupied cell with at least two Y neighbors if one can be found, otherwise it stays in place
- Both types of cells have a random chance of 10% to move to the cell with one neighbor or less of any kind, if one can be found, instead of following the above rules.
