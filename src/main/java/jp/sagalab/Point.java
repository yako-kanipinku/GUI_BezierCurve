package jp.sagalab;

public class Point {
	/**
	 * 指定した座標にある点を生成する.
	 * @param _x x座標
	 * @param _y y座標
	 * @return 点
	 */
	static Point create(Double _x, Double _y) {
		return new Point(_x, _y);
	}

	/**
	 * 点のx座標を取得する.
	 * @return x座標
	 */
	public double getX() {
		return m_x;
	}

	/**
	 * 点のy座標を取得する.
	 * @return y座標
	 */
	public double getY() {
		return  m_y;
	}

	/**
	 * コンストラクタ
	 * @param _x x座標
	 * @param _y y座標
	 */
	private Point(Double _x, Double _y) {
		m_x = _x;
		m_y = _y;
	}

	/** x座標 */
	private final Double m_x;
	/** y座標 */
	private final Double m_y;
}
