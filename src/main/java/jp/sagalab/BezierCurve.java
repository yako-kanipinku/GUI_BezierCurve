package jp.sagalab;

import java.awt.*;
import java.util.List;

/**
 * n次のBezier曲線を表す.
 */
public class BezierCurve {

	/**
	 * 制御点列を指定してBezier曲線オブジェクトを生成する.
	 * @param _controlPoints 制御点列
	 */
	public BezierCurve(List<Point> _controlPoints){
		m_controlPoints = _controlPoints;
	}

	/**
	 * 2次有理ベジェ曲線を生成するファクトリーメソッド
	 * @param _controlPoints 制御点列
	 * @return 2次有理ベジェ曲線
	 */
	public static BezierCurve create(List<Point> _controlPoints){
		return new BezierCurve(_controlPoints);
	}

	/**
	 * パラメータtに対応する評価点を De Casteljau のアルゴリズムで評価する.
	 * パラメータtは閉区間 [0,1] の値をとる.
	 * @param _t 閉区間[0,1]内のパラメータ
	 * @return パラメータtに対応する評価点
	 */
	public Point evaluate(Double _t){
		double denominator = 0.0;
		double x = 0.0;
		double y = 0.0;
		double[] w = w_create(c_w);
		final Integer n = getDegree();

		for(int i = 0; i <= n; i++) {
			denominator += w[i] * bernsteinBasisPolynomial(n, i, _t);
		}
		if(denominator==0){
			return Point.create(0.0,0.0);
		} else {
			for (int i = 0; i <= n; i++) {
				x += w[i] * m_controlPoints.get(i).getX() * bernsteinBasisPolynomial(n, i, _t);
				y += w[i] * m_controlPoints.get(i).getY() * bernsteinBasisPolynomial(n, i, _t);
			}
			return Point.create(x/denominator,y/denominator);
		}
	}

	/**
	 * 制御点列を取得する.
	 * @return 制御点列
	 */
	public List<Point> getControlPoints(){
		return m_controlPoints;
	}

	/**
	 * 次数を取得する.
	 * @return 次数
	 */
	public java.lang.Integer getDegree() {
		return m_controlPoints.size() -1;
	}

	/** 制御点列 */
	private List<Point> m_controlPoints;

	/**
	 * バーンスタイン基底関数の実装.
	 * @param _n 次数
	 * @param _i インデックス
	 * @param _t 閉区間[0,1]内のパラメータ
	 * @return  バーンスタイン基底関数の値
	 */
	private java.lang.Double bernsteinBasisPolynomial(java.lang.Integer _n, java.lang.Integer _i, java.lang.Double _t){
		double binomialCoeff = binomialCoefficient(_n, _i);
		double x = Math.pow(1.0-_t, _n-_i);
		double y = Math.pow(_t, _i);
		double z = binomialCoeff * x * y;
		return z;
	}

	/**
	 * 階乗 n! の実装.
	 * @param _n n!のn
	 * @return n!
	 */
	private java.lang.Integer factorial(java.lang.Integer _n){
		if (_n==0)
			return 1;
		return _n*factorial(_n-1);
	}

	/**
	 * 二項係数 nCi の実装.
	 * @param _n nCi の n
	 * @param _i nCi の i
	 * @return nCi
	 */
	private java.lang.Integer binomialCoefficient(java.lang.Integer _n, java.lang.Integer _i){
		return factorial(_n)/(factorial(_i)*factorial(_n-_i));
	}

	/**
	 * c_wの値を取得する.
	 * @return c_w(w[1])の値
	 */
	private static double getW(){
		return c_w;
	}

	/**
	 * 各制御点の重みを生成.
	 * ただし、w[0]=w[2]=1.0, w[1]=c_w とする
	 * @param _w w[1]の重み
	 * @return w[]の配列
	 */
	public double[] w_create(double _w) {
		int n = m_controlPoints.size();
		double[] w = new double[n];
		for (int i = 0; i < n; i++) {
			if (i == 0 || i == n - 1) {
				w[i] = 1.0;
			} else {
				w[i] = _w;
			}
		}
		return w;
	}

	/**
	 * c_w(w[1])の重み
	 */
	private static double c_w = 1.0;
}

