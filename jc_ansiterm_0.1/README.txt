==== JChassis ANSI Terminal Controller ====
VERSION: 0.1

Copyright (C) 2005 Sam Stainsby.


DESCRIPTION:

A pure Java API for controlling ANSI-compliant terminals and 
terminal emulators such a linux virtual terminals, xterm, Gnome Terminal 
and KDE Konsole. This library can be used to display character styles and 
colors and graphical characters in those environments.

The library uses standard input and output to control the terminal. 
To use this library under linux, you will first have to set the 
terminal line setting to to raw I/O with no echo (use "stty raw -echo" 
for example - you can use use "stty sane" after the program exists to 
restore the usual settings).

In theory, this library may also work under DOS or Windows using
something like ANSI.SYS, but this has not been tested.

To get started, read the Javadoc for org.jchassis.termctl, the terminal 
controller interface, and org.jchassis.termctl.ansi, the ANSI 
implementaton. The framework allows other types of terminal to be 
substituted without having to alter your application, provided that 
you only use the org.jchassis.termctl interface. However, ANSI/VT100 
is the only implementation available currently. You will need to 
instantiate a terminal controller implementation (in this case, 
org.jchassis.termctl.ansi.TerminalController) initially before using the 
API.

Simple examples of use can be found in the stest directory.


AUTHORS: Sam Stainsby (sam@stainsby.id.au)


GENERAL

This library is a repackaging of some JChassis modules into a simple
standalone library. As such, it does not include the Ant build files to build 
the source. If you wish to use the original build system, you should download 
the JChassis SDK and use that.

This library is *not* a JChassis module, and is usable without needing a 
JChassis framework.


LICENSE

This library is distributed under the GNU LESSER GENERAL PUBLIC LICENSE 
Version 2.1, a copy of which can be found in this directory.


DEPENDENCIES

This library incorporates the following JChassis modules:
jc_name,jc_geom_int2d,jc_event,jc_termctl_if,jc_termctl_ansi