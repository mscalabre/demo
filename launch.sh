modulepath=\
"update4j-1.5.9.jar;\
bootstrap/javafx-base-19-win.jar;\
bootstrap/javafx-graphics-19-win.jar;\
bootstrap/javafx-fxml-19-win.jar;\
bootstrap/javafx-controls-19-win.jar;\
bootstrap/controlsfx-9.0.0.jar"

modules=org.update4j,\
javafx.controls,\
javafx.graphics,\
javafx.fxml,\
controlsfx

exports="--add-exports javafx.base/com.sun.javafx.runtime=controlsfx
 --add-exports javafx.base/com.sun.javafx.event=controlsfx
 --add-exports javafx.graphics/com.sun.javafx.scene=controlsfx
 --add-exports javafx.graphics/com.sun.javafx.scene.traversal=controlsfx"

echo $modulepath

$java17/bin/java \
	-p $modulepath \
	--add-modules $modules \
	$exports \
	-jar update4j-1.5.9.jar \
	--remote http://localhost/demo/setup.xml \
	-- \
	launch