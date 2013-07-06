package utils.swf;

import java.io.File;
import java.io.IOException;

public class MainClass
{
	public static void main(String[] args) throws IOException
	{
		File input = new File(args[0]);
		File output = new File(args[1]);
		
		AnimationReader exporter = new AnimationReader();
		SwfAnimation animation = exporter.read(input);
		
		AnimationWriter writer = new AnimationWriter();
		writer.write(animation, output);
		
		System.out.println("Animation written: " + output);
	}
}
