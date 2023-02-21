To launch with hybrid classpath/modulepath (classpath bootstrap, modulepath business), use this command in a folder with both update4j-1.5.9.jar and launch.sh :

`java -jar update4j-1.5.9.jar --remote http://localhost/demo/setup.xml && . launch.sh`

update4j runs 2 time throught bootstrap.Starter main method. First time without any args, then it stop after download. Second time (in launch script) with arg "launch", then it launch bootstrap.
