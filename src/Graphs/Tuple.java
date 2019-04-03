package Graphs;

public class Tuple
{

	private int n1;
	private int n2;

	public Tuple(int n1, int n2)
	{
		this.n1 = n1;
		this.n2 = n2;
	}

	public int getN1()
	{
		return n1;
	}

	public int getN2()
	{
		return n2;
	}

	@Override
	public int hashCode()
	{
		return n1 ^ n2 * n2 ^ n1;
	}

//	@Override
//	public boolean equals(Object o)
//	{
//		if (!(o instanceof Tuple))
//			return false;
//		Tuple pairo = (Tuple) o;
//		return this.left.equals(pairo.getLeft()) && this.right.equals(pairo.getRight());
//	}

}