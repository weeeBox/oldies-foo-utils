package utils.psd.struct;

import java.io.IOException;

import utils.psd.PsdInputStream;

public class PsdHelper
{
	public static boolean checkSignature(PsdInputStream stream, String signature) throws IOException
	{
		String str = stream.readString(signature.length());
		return str.equals(signature);
	}
}
