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
	public String toString()
	{
		return n1 + " " + n2;
	}

	@Override
	public int hashCode()
	{
		return n1 ^ n2;
	}

	@Override
	public boolean equals(Object o)
	{
		if (o == null || o.getClass() != this.getClass())
			return false;
		return (this.getN1() == ((Tuple) o).getN1() && this.getN2() == ((Tuple) o).getN2())
				|| (this.getN1() == ((Tuple) o).getN2() && this.getN2() == ((Tuple) o).getN1());
	}

}