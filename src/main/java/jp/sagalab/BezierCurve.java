package jp.sagalab;

/**
 * n次のBezier曲線を表す.
 */
public class BezierCurve {

	/**
	 * 制御点列を指定してBezier曲線オブジェクトを生成する.
	 * @param _controlPoints 制御点列
	 */
	public BezierCurve(java.util.List<java.awt.Point> _controlPoints){
		m_controlPoints = _controlPoints;
	}

	public static BezierCurve create(java.util.List<java.awt.Point> _controlPoints){
		return new BezierCurve(_controlPoints);
	}

	/**
	 * パラメータtに対応する評価点を De Casteljau のアルゴリズムで評価する.
	 * パラメータtは閉区間 [0,1] の値をとる.
	 * @param _t 閉区間[0,1]内のパラメータ
	 * @return パラメータtに対応する評価点
	 */
	public java.awt.Point evaluate(Double _t){
		Double x = 0.0;
		Double y = 0.0;
		final Integer n = getDegree();

		for(int i = 0; i<=n; ++i){
			Double bernstein = bernsteinBasisPolynomial(n, i, _t);

			x += m_controlPoints.get(i).getX()*bernstein;
			y += m_controlPoints.get(i).getY()*bernstein;
		}

		return new java.awt.Point(x.intValue(), y.intValue());
	}

	/**
	 * 制御点列を取得する.
	 * @return 制御点列
	 */
	public java.util.List<java.awt.Point> getControlPoints(){
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
	private java.util.List<java.awt.Point> m_controlPoints;

	/**
	 * バーンスタイン基底関数の実装.
	 * @param _n 次数
	 * @param _i インデックス
	 * @param _t 閉区間[0,1]内のパラメータ
	 * @return  バーンスタイン基底関数の値
	 */
	private java.lang.Double bernsteinBasisPolynomial(java.lang.Integer _n, java.lang.Integer _i, java.lang.Double _t){
		java.lang.Integer binomialCoeff = binomialCoefficient(_n, _i);
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

}

