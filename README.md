# MechMania25 Java Starter Pack

Here's all the code you need to get started with making a bot for MechMania in Java. Just do these steps:

* Pre-Setup -- install Java, Node and the `mm` command line tools
* Setup -- Clone this repository and start running your bot!

# Pre-Setup

1. Install Java, Node, and the `mm` tools with the instructions on the [main wiki](https://github.com/HoelzelJon/MechMania-25-Wiki/wiki#pre-setup). 

3. Make sure your `JAVA_HOME` environment variable is set correctly.
    * To check this on Windows, you can run `echo %JAVA_HOME%`. You should see a result similar to `C:\Program Files\Java\jdk1.8.0_171`. Note that this does NOT end with `\bin`.
    * For Mac and Linux users, you can run `echo $JAVA_HOME`. You should see a result similar to `/Library/Java/JavaVirtualMachines/jdk1.8.0_45.jdk/Contents/Home`.

    * If it is not set correctly, you will need to edit your environment variables.
        * For Windows, you can edit environment variables by searching "edit environment variables" in the start menu or by going to Control Panel -> System and Security -> System -> Advanced System Settings ->  Environment Variables (bottom right). 
        
            Find `Path` in the System Variables list (bottom list) and double click on it. Select `New` and paste in the `directory you want to add to PATH. Press OK until you are out of the menus.
            
        * For Linux and Mac users, run `export JAVA_HOME=<path/to/jdk/>`, replacing `<path/to/jdk>` so that the command refers to the correct directory.
                    
        * Advice for Windows users:
            * Make sure to set the `JAVA_HOME` variable as a SYSTEM environment variable, rather than a user environment variable.
            * Make sure to NOT have `\bin` at the end of your `JAVA_HOME` environment variable.

2. For the Java starter pack, you will have to set up Maven.
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

```dif
**IMPORTANT**: Run `mvn clean install` after any changes made to your Java code in order to build the file `target/Bot.jar`. This step should be completed whenever you want to run _any_ `mm` commands with a new strategy.
```
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
