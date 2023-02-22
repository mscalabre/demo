# Basic Launch :


## How to : 

To launch with hybrid classpath/modulepath (classpath bootstrap, modulepath business), use this command in a folder with both `update4j-1.5.9.jar` and `launch.sh` :

`java -jar update4j-1.5.9.jar --remote http://localhost/demo/setup.xml && . launch.sh`

## How it works :

update4j runs 2 times throught `bootstrap.DownloadOrLaunchBootstrap` main method. <br>
First time without any args, then it stop after download of bootstrap deps.  <br>
Second time (in launch script) with arg "launch", then it launch bootstrap.  <br>
 <br>
#### Why ? 
With hybrid classpath and modulepath we need a second launch, after downloading common deps beetween `bootstrap` and `business`, to have a correct modulepath at start that includes the common deps.


# Create Windows installer :


## How to :

- In module create-config, file CreateConfig.java, change variable `local` from `true` to `false`
- `mvn clean install` the whole project
- Launch `package.bat`
- The MSI installer is in `target/installer`

## How it works :

jpackage needs a jar but we need to launch 2 times (look at `Basic Launch / How it works` for more details) with a batch file.
We use a basic jar (`starter.jar`) which only launch the batch file
