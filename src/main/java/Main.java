import javax.swing.*;
import javax.swing.JOptionPane;
import java.awt.*;
import java.awt.event.*;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

/**
 * @author yako
 *
 */

public class Main extends JFrame {
	public Main(){
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		setDefaultCloseOperation( JFrame.DO_NOTHING_ON_CLOSE );
		addWindowListener(new WindowClosing());
		setState(JFrame.ICONIFIED);
		setIconImage(new ImageIcon("icon.jpg").getImage());
		canvas.setSize(800, 600);
		canvas.setBackground(Color.WHITE);
		setTitle("b3zemi");
		add(canvas);
		pack();
		setVisible( true );

		canvas.addMouseListener(
				new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						drawPoint(e.getX(),e.getY());
					}
				}
		);

	}

	/**
	 * ウィンドウを閉じる時の確認ダイアログ
	 */
	class WindowClosing extends WindowAdapter{
		public void windowClosing(WindowEvent e ){
			int ans = JOptionPane.showConfirmDialog(Main.this, "Are you sure you want to finish?");
			if(ans == JOptionPane.YES_OPTION){
				System.exit(0);
			}
		}
	}

	/**
	 *
	 * @param _x x座標
	 * @param _y y座標
	 */

	public void drawPoint(int _x, int _y){
		Graphics g = canvas.getGraphics();
		g.drawOval(_x, _y, 2, 2);
	}

	/**
	 *
	 * @param args the command line arguments
	 */

	public static void main(String[] args){ new Main(); }

	private Canvas canvas = new Canvas();

}

