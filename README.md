# MechMania25 Java Starter Pack

Here's all the code you need to get started with making a bot for MechMania in Java. Just do these steps:

* Pre-Setup -- install Node and the `mm` command line tools
* Setup -- Clone this repository and start running your bot!

# Windows Pre-Setup

1. First, install Node. To do this, go [here](https://nodejs.org/en/download/) and download the Windows Installer.

3. Run `npm install -g mechmania`.  This gets the `mm` command line tools, which are used to test and submit bots for the tournament.

# Mac Pre-Setup

1. First, install Node. To do this, go [here](https://nodejs.org/en/download/) and download the macOS Installer.

3. Run `npm install -g mechmania`.  This gets the `mm` command line tools, which are used to test and submit bots for the tournament.

# Setup

1. Clone this repo (or fork it or download it somewhere as a ZIP)
2. Modify the script at `Strategy.java`.
    * Write your code in the `doTurn` method.
    * You may also add other files or dependencies. If you have any questions about this, we're here to help!
3. Run `mm play .`
    * This will build the bot in the given directory (`.`) and then starts a game in which your bot fights against itself.
4. To run two different bots against each other, run `mm play bot1_directory bot2_directory`.

Use `mm help` for more information!

# Game_API Information
Within the package `mech.mania.API`, there are a variety of helpful functions for writing your bot. You can use the `GameState` object to run any of these functions.

Within the `mech.mania.API` package, a few other classes are defined.  We recommend only accessing the fields in these classes, since changing them might cause the `GameState` object to not properly represent the current state of the game.

See the [documentation](https://hoelzeljon.github.io/MM25-Java-Starter-Pack/) for detailed descriptions of all defined classes and all available funcitons.