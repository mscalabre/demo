rd /s /q "target/java-runtime"
"%java17%/bin/jlink" --add-modules java.xml,jdk.zipfs,java.scripting,java.desktop,jdk.unsupported,java.logging  --output target/java-runtime --no-header-files --no-man-pages --compress=2

mkdir "target/app"

xcopy /Y target\config\business\update4j-1.5.9.jar target\app\
xcopy /Y "launch.bat" "target\app\"
xcopy /Y starter\target\starter-1.0.0.jar target\app\

"%java17%\bin\jpackage" ^
  --type msi ^
  --dest target/installer ^
  --input target/app ^
  --name demo17 ^
  --main-class org.update4j.demo.starter.Starter ^
  --main-jar starter-1.0.0.jar ^
  --java-options "-Xmx2048m -Dglass.win.uiScale=1" ^
  --runtime-image target/java-runtime ^
  --vendor "BURGER" ^
  --copyright "DeckGenius" ^
  --win-dir-chooser ^
  --win-shortcut