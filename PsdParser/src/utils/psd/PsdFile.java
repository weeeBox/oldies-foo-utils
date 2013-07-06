package utils.psd;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import utils.psd.struct.PsdColorMode;
import utils.psd.struct.PsdColorModeSection;
import utils.psd.struct.PsdHeader;
import utils.psd.struct.res.PsdResBlock;
import utils.psd.struct.res.blocks.PsdGuide;
import utils.psd.struct.res.blocks.PsdResSection;

public class PsdFile
{
	private PsdHeader header;
	private PsdColorModeSection colorMode;
	private PsdResSection resSection;
	
	private ArrayList<PsdLayer> layers;
	private List<PsdGuide> guides;
	private int[] framesDelays;
	private HashMap<Integer, Integer> framesIds;
	private PsdLayer baseLayer;

	public PsdFile(File file) throws IOException
	{
		FileInputStream fis = null;
		try
		{
			fis = new FileInputStream(file);
			read(fis);
		}
		finally
		{
			if (fis != null)
				fis.close();
		}
	}
	
	public PsdFile(InputStream inputStream) throws IOException
	{
		if (inputStream == null)
		{
			throw new IllegalArgumentException("stream is null");
		}
		
		read(inputStream);
	}

	private void read(InputStream inputStream) throws IOException
	{
		PsdInputStream stream = new PsdInputStream(inputStream);
		readHeaderSection(stream);
		readColorModeSection(stream);
		readImageResourceSection(stream);
		readLayersSection(stream);
		setupLayersGroups();
	}
	
	public int getLayersCount()
	{
		return layers.size();
	}

	public PsdLayer getLayer(int n)
	{
		return layers.get(n);
	}

	public int getFramesCount()
	{
		return framesDelays == null ? 0 : framesDelays.length;
	}

	public int getFrameDelay(int frame)
	{
		return framesDelays == null ? 0 : framesDelays[frame];
	}
	
	public int getGuidesCount()
	{
		return guides.size();
	}
	
	public List<PsdGuide> getGuides()
	{
		return guides;
	}

	public int getWidth()
	{
		return header.getWidth();
	}

	public int getHeight()
	{
		return header.getHeight();
	}

	public PsdColorMode getColorMode()
	{
		return header.getColorMode();
	}

	public int getDepth()
	{
		return header.getBitsPerChannel();
	}

	public int getNumberOfChannels()
	{
		return header.getNumChannels();
	}
	
	public List<PsdResBlock> getResBlocks()
	{
		return resSection.getBlocks();
	}

	int getFrameNum(int frameId)
	{
		return framesIds.get(frameId);
	}

	private void readHeaderSection(PsdInputStream stream) throws IOException
	{
		header = new PsdHeader(stream);
	}

	private void readColorModeSection(PsdInputStream stream) throws IOException
	{
		colorMode = new PsdColorModeSection(stream);
	}

	private void readImageResourceSection(PsdInputStream stream) throws IOException
	{
		resSection = new PsdResSection();
		resSection.read(stream);
		guides = resSection.getGuides();
	}

	private void readAnimationFramesInformation(PsdInputStream stream, int id, int sizeOfData) throws IOException
	{
		byte[] data = new byte[sizeOfData];
		stream.read(data);

		PsdInputStream st = new PsdInputStream(new ByteArrayInputStream(data));
		String key = st.readString(4);
		if (key.equals("mani"))
		{
			st.skipBytes(12 + 12);
			PsdDescriptor desc = new PsdDescriptor(st);
			PsdList delaysList = (PsdList) desc.get("FrIn");
			HashMap<Integer, Integer> delays = new HashMap<Integer, Integer>();
			for (Object o : delaysList)
			{
				PsdDescriptor frDesc = (PsdDescriptor) o;
				delays.put((Integer) frDesc.get("FrID"), (Integer) frDesc.get("FrDl"));
			}

			PsdList framesSets = (PsdList) desc.get("FSts");
			PsdDescriptor frameSet = (PsdDescriptor) framesSets.get(0);
			// int activeFrame = (Integer) frameSet.get("AFrm");
			PsdList framesList = (PsdList) frameSet.get("FsFr");
			framesIds = new HashMap<Integer, Integer>();
			framesDelays = new int[framesList.size()];
			for (int i = 0; i < framesList.size(); i++)
			{
				int frameId = (Integer) framesList.get(i);
				Integer delay = delays.get(frameId);
				framesDelays[i] = (delay == null ? 10 : delay) * 10;
				framesIds.put(frameId, i);
			}
		}
	}

	private void readLayersSection(PsdInputStream input) throws IOException
	{
		// read layer header info
		int length = input.readInt32();
		int pos = input.getPos();
		if (length > 0)
		{
			int size = input.readInt32();
			if ((size & 0x01) != 0)
			{
				size++;
			}
			if (size > 0)
			{
				int layersCount = input.readInt16();
				if (layersCount < 0)
				{
					layersCount = -layersCount;
				}
				layers = new ArrayList<PsdLayer>(layersCount);
				for (int i = 0; i < layersCount; i++)
				{
					PsdLayer layer = new PsdLayer(this, input);
					layers.add(layer);
				}
				for (PsdLayer layer : layers)
				{
					layer.readImage(input);
				}
			}

			int maskSize = length - (input.getPos() - pos);
			input.skipBytes(maskSize);
		}

		baseLayer = new PsdLayer(this);
		boolean rle = input.readInt16() == 1;
		if (rle)
		{
			int nLines = baseLayer.getHeight() * baseLayer.getNumberOfChannels();
			short[] lineLengths = new short[nLines];
			for (int i = 0; i < nLines; i++)
			{
				lineLengths[i] = input.readInt16();
			}
			baseLayer.readImage(input, false, lineLengths);
		}
		else
		{
			baseLayer.readImage(input, false, null);
		}

		if (layers == null)
		{
			layers = new ArrayList<PsdLayer>(1);
			layers.add(baseLayer);
		}
	}

	private void setupLayersGroups()
	{
		PsdLayer parentLayer = null;
		for (int i = layers.size() - 1; i >= 0; i--)
		{
			PsdLayer layer = layers.get(i);
			System.out.println("psdLayer: " + layer.getName());
			switch (layer.getType())
			{
			case NORMAL:
				layer.setParent(parentLayer);
				break;
			case FOLDER:
				layer.setParent(parentLayer);
				parentLayer = layer;
				break;
			case HIDDEN:
				if (parentLayer != null)
				{
					parentLayer = parentLayer.getParent();
				}
				break;
			}
		}
	}

}
