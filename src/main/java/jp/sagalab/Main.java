package jp.sagalab;

import javax.swing.*;
import javax.swing.JOptionPane;
import java.awt.*;
import java.awt.event.*;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.geom.Point2D;
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
							drawPoint(e.getX(),e.getY());
							m_controlPoints.add(e.getPoint());
							if(m_controlPoints.size() == 3){
								drawBezierCurve();
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
		g.drawOval(_x, _y, 8, 8);
	}

	public void drawLine(int _x, int _y, int _x2, int _y2){
		Graphics g = canvas.getGraphics();
		g.drawLine(_x, _y, _x2, _y2);
	}

	public void drawBezierCurve(){
		BezierCurve BC = BezierCurve.create(m_controlPoints);
		java.awt.Point a = BC.evaluate(0.0);
		for(double i=0.01; i<1.0; i+=0.01){
			java.awt.Point b = BC.evaluate(i);
			drawLine(a.x,a.y,b.x,b.y);
			a = b;
		}
	}
	/**
	 *
	 * @param args the command line arguments
	 */

	public static void main(String[] args){ new Main(); }

	private Canvas canvas = new Canvas();
	private List<java.awt.Point> m_controlPoints = new ArrayList<>();

}

