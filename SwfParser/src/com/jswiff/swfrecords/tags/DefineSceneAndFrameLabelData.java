package com.jswiff.swfrecords.tags;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;

public class DefineSceneAndFrameLabelData extends Tag 
{
	private List<Scene> scenes;
	private List<Frame> frames;
	private int size;
	
	public DefineSceneAndFrameLabelData() 
	{
		scenes = new ArrayList<Scene>();
		frames = new ArrayList<Frame>();
		code = TagConstants.DEFINE_SCENE_AND_FRAME_LABEL_DATA;
	}
	
	@Override
	void setData(byte[] data) throws IOException 
	{
		size = data.length;
		
		InputBitStream inStream = new InputBitStream(data);
		int scenesCount = (int) inStream.readEncodedU32();
		for (int sceneIndex = 0; sceneIndex < scenesCount; sceneIndex++) 
		{
			int offset = (int) inStream.readEncodedU32();
			String name = inStream.readString();
			Scene scene = new Scene(offset, name);
			
			scenes.add(scene);
		}
		
		int framesCount = (int) inStream.readEncodedU32();
		for (int frameIndex = 0; frameIndex < framesCount; frameIndex++) 
		{
			int frameNum = (int) inStream.readEncodedU32();
			String label = inStream.readString();
			
			Frame frame = new Frame(frameNum, label);
			frames.add(frame);
		}
	}

	@Override
	protected void writeData(OutputBitStream outStream) throws IOException 
	{
		int scenesCount = scenes.size();
		outStream.writeEncodedU32(scenesCount);
		for (Scene scene : scenes) 
		{
			outStream.writeEncodedU32(scene.getOffset());
			outStream.writeString(scene.getName());
		}
		
		int framesCount = frames.size();
		outStream.writeEncodedU32(framesCount);
		for (Frame frame : frames) 
		{
			outStream.writeEncodedU32(frame.getFrameNum());
			outStream.writeString(frame.getLabel());
		}
	}
	
	public List<Scene> getScenes() 
	{
		return scenes;
	}

	public List<Frame> getFrames() 
	{
		return frames;
	}

	public int getSize() 
	{
		return size;
	}

	public static class Scene
	{
		private int offset;
		private String name;

		public Scene(int offset, String name) 
		{
			this.offset = offset;
			this.name = name;
		}

		public int getOffset() 
		{
			return offset;
		}

		public String getName()
		{
			return name;
		}
	}
	
	public static class Frame
	{
		private int frameNum;
		private String label;

		public Frame(int frameNum, String label) 
		{
			this.frameNum = frameNum;
			this.label = label;
		}

		public int getFrameNum() 
		{
			return frameNum;
		}

		public String getLabel() 
		{
			return label;
		}
	}
}
