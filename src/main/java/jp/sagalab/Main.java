package jp.sagalab;

import javax.swing.*;
import javax.swing.JOptionPane;
import java.awt.*;
import java.awt.event.*;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.List;

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
		setIconImage(new ImageIcon("icon2.jpg").getImage());
		canvas.setSize(800, 600);
		canvas.setBackground(Color.WHITE);
		setTitle("BezierCurve用");
		add(canvas);
		pack();
		setVisible( true );

		canvas.addMouseListener(
				new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						if(m_controlPoints.size() < 4){
							drawPoint(e.getX(),e.getY()); //点を打つ.
							Point point = Point.create((double)e.getX(), (double)e.getY()); //点を生成.
							m_controlPoints.add(point); //打った点を制御点として追加.
							if(m_controlPoints.size() == 3){
								clean();
								drawBezierCurve();
								m_controlPoints.remove(0); //配列で一番古い点を取り除く.
							}
						}
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
	 * 打った点のx座標, y座標を表す.
	 * @param _x x座標
	 * @param _y y座標
	 */
	public void drawPoint(int _x, int _y){
		Graphics g = canvas.getGraphics();
		g.drawOval(_x-4, _y-4, 8, 8);
	}

	/**
	 * 点と点を繋げるための処理.
	 */
	public void drawLine(double _x, double _y, double _x2, double _y2){
		Graphics g = canvas.getGraphics();
		g.drawLine((int)_x, (int)_y, (int)_x2, (int)_y2);
	}

	/**
	 * 画面を一度リセットする.
	 */
	public void clean(){
		Graphics g = canvas.getGraphics();
		g.clearRect(0, 0, 800, 600);
	}

	/**
	 * 2次有理ベジェ曲線を描くメソッド.
	 */
	public void drawBezierCurve(){
		BezierCurve BC = BezierCurve.create(m_controlPoints);
		Point a = BC.evaluate(0.0);
		for(double i=0.0001; i<1.0; i+=0.0001){
			Point b = BC.evaluate(i);
			drawLine(a.getX(), a.getY(), b.getX(), b.getY());
			a = b;
		}
	}
	/**
	 *
	 * @param args the command line arguments
	 */

	public static void main(String[] args){ new Main(); }

	private final Canvas canvas = new Canvas();
	private List<Point> m_controlPoints = new ArrayList<>();
}

