package tests;

public class Test 
{
	public static int negate(int n) {
		  return new Byte((byte) 0xFF).hashCode()
		      / (int) (short) '\uFFFF' * ~0
		      * Character.digit ('0', 0) * n
		      * (Integer.MAX_VALUE * 2 + 1)
		      / (Byte.MIN_VALUE >> 7) * (~1 | 1);
		}
	
	public static void main(String args[])
	{
		System.out.println(Test.negate(500));
	}
}
