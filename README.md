# MechMania25 Java Starter Pack

Here's all the code you need to get started with making a bot for MechMania in Java. Just do these steps:

* Pre-Setup -- install Node and the `mm` command line tools
* Setup -- Clone this repository and start running your bot!

# Pre-Setup

1. First, install Java. To do this, see [this guide](https://docs.oracle.com/en/java/javase/13/install/overview-jdk-installation.html#GUID-8677A77F-231A-40F7-98B9-1FD0B48C346A) for help.
    * Advice for Windows users:
        * Make sure to set the `JAVA_HOME` variable as a SYSTEM environment variable, rather than a user environment variable.
        * Make sure to NOT have `bin` at the end of your `JAVA_HOME` environment variable.
    * Check that the `JAVA_HOME` environment variable is set correctly.
        * For Windows, you can run `echo %JAVA_HOME%`. You should see a result similar to `C:\Program Files\Java\jdk1.8.0_171`. Note that this does NOT end with `\bin`.
        * For Mac and Linux users, you can run `echo $JAVA_HOME`. You should see a result similar to `/Library/Java/JavaVirtualMachines/jdk1.8.0_45.jdk/Contents/Home`.
        
2. Install Node. To do this, go [here](https://nodejs.org/en/download/) and download the appropriate installer for your operating system.
    * Run the installer with all the defaults.

3. Run `npm install -g mechmania`.  This gets the `mm` command line tools, which are used to run the game, test and submit bots for the tournament.

4. Run `mm download` to download required files.

5. For the Java starter pack, you will have to set up Maven.
    * Install Maven from [here](https://maven.apache.org/download.cgi). We recommend using the binary zip archive.
        * Extract the zip archive to any directory
        * Add the `bin` directory of the created directory to the PATH environment variable.
            * For Windows, you can edit environment variables by searching "edit environment variables" in the start menu or by going to Control Panel -> System and Security -> System -> Advanced System Settings ->  Environment Variables (bottom right).
            
                Find `Path` in the System Variables list (bottom list) and double click on it. Select `New` and paste in the `bin` directory of the your maven installation. Press OK until you are out of the menus.
            * For Mac users, run `export PATH=/opt/apache-maven-<version>/bin:$PATH`, replacing `<version>` so that the command refers to the correct directory.
                
        * Check that you have done this correctly by running `mvn -v`, which will print the version of your maven installation. If it runs successfully, you are done with the Pre-Setup!

# Setup

1. Clone this repo (or fork it or download it somewhere as a ZIP)

2. Modify the script at `Strategy.java`.
    * Write your code in the `doTurn` method.
    * You may also add other files or dependencies. If you have any questions about this, we're here to help!

3. **IMPORTANT**: Run `mvn clean install` to build the file `target/Bot.jar`. This step should be completed whenever you want to run _any_ `mm` commands with a new strategy.

4. Run `mm play .`
    * This will build the bot in the given directory (`.`) and then starts a game in which your bot fights against itself.
    * On Windows, if an `mm play` command fails, make sure to close any Java SE Runtime Binary processes with Task Manager.
5. To run two different bots against each other, run `mm play bot1_directory bot2_directory`.
6. To submit your bot, run `mm push .`

Use `mm help` for more information!

# Game API Information
Within the package `mech.mania.API`, there are a variety of helpful functions for writing your bot. You can use the `GameState` object to run any of these functions.

The `mech.mania.API` package has a few other classes as well.  We recommend only accessing the fields in these classes, since changing them might cause the `GameState` object to not properly represent the current state of the game.

See the [documentation](https://hoelzeljon.github.io/MM25-Java-Starter-Pack/) for detailed descriptions of all defined classes and all available functions.
