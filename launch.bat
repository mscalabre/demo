"runtime\bin\java" -jar app/update4j-1.5.9.jar --remote http://localhost/demo/setup.xml --archive %1\update.zip


set modulepath=^
app\update4j-1.5.9.jar;^
%1\bootstrap\javafx-base-19-win.jar;^
%1\bootstrap\javafx-graphics-19-win.jar;^
%1\bootstrap\javafx-fxml-19-win.jar;^
%1\bootstrap\javafx-controls-19-win.jar;^
%1\bootstrap\controlsfx-9.0.0.jar

set modules=org.update4j,^
javafx.controls,^
javafx.graphics,^
javafx.fxml,^
controlsfx

set exports=^
--add-exports javafx.base/com.sun.javafx.runtime=controlsfx^
 --add-exports javafx.base/com.sun.javafx.event=controlsfx^
 --add-exports javafx.graphics/com.sun.javafx.scene=controlsfx^
 --add-exports javafx.graphics/com.sun.javafx.scene.traversal=controlsfx

echo %modulepath%

"runtime\bin\java" ^
	-p %modulepath% ^
	--add-modules %modules% ^
	%exports% ^
	-jar app/update4j-1.5.9.jar ^
	--remote http://localhost/demo/setup.xml ^
	-- ^
	launch