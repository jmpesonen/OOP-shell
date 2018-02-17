# OOP-shell
A simple file system and command line emulation. Emulation happens within Java's virtual machine, and doesn't make any "physical" changes to hard drive.

This was the coursework of object-oriented programming course at University of Tampere. Comments and code are in Finnish.

Files in /fi/uta/csjola/oope/lista and /apulaiset directories were given by the lecturer and were required for the coursework.

Commands:

* md <name>
  * Creates a directory with the given name
* mf <name> <size>
  * Creates a file with the given name and the given (imaginary) size
* cd <name>
  * Changes to directory with given name
* ls <name>
  * Lists contents of the current directory, if no name is given
  * If name is given, it prints out information of that directory or file
* find
  * Lists content of current directory and its sub-directories recursively
* mv <oldName> <newName>
  * Renames a directory or file with oldName to newName
* cp <fileName> <newName>
  * Creates a copy of a directory or file with fileName, and sets copy's name as newName
* rm <name>
  * Deletes a directory or file with the given name
* exit
  * Terminates the program
