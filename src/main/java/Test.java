import javafx.embed.swt.FXCanvas;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Test {

    public static void main(String[] args) {
        Display display = new Display();
        Shell shell = new Shell(display);
        shell.setLayout(new RowLayout());

        org.eclipse.swt.widgets.Button swtButton = new org.eclipse.swt.widgets.Button(shell, SWT.PUSH);
        swtButton.setText("SWT Button");

        FXCanvas fxCanvas = new FXCanvas(shell, SWT.NONE) {
            @Override
            public Point computeSize(int wHint, int hHint, boolean changed) {
                getScene().getWindow().sizeToScene();
                int width = (int) getScene().getWidth();
                int height = (int) getScene().getHeight();
                return new Point(width, height);
            }
        };

        Button jfxButton = new Button("JFX Button");
        jfxButton.setId("ipad-dark-grey");
        jfxButton.setContextMenu(new ContextMenu(new MenuItem("Test 1"), new MenuItem("Test 2")));
        fxCanvas.setScene(new Scene(new Group(jfxButton)));

        swtButton.addListener(SWT.Selection, event -> {
            jfxButton.setText("JFX Button: Hello from SWT");
            shell.layout();
        });
        jfxButton.setOnAction(event -> {
            swtButton.setText("SWT Button: Hello from JFX");
            shell.layout();
        });

        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        display.dispose();
    }
}
