package utils.psd.struct.res.blocks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import utils.psd.PsdInputStream;
import utils.psd.path.BezierKnot;
import utils.psd.path.PsdPath;
import utils.psd.struct.res.PsdResBlock;
import utis.psd.stuff.Point;
import utis.psd.stuff.Rect;

public class PathBlock extends PsdResBlock
{
	private static final int RECORD_CLOSED_SUBPATH_LENGTH = 0;
	private static final int RECORD_CLOSED_SUBPATH_BEZIER_KNOT_LINKED = 1;
	private static final int RECORD_CLOSED_SUBPATH_BEZIER_KNOT_UNLINKED = 2;
	private static final int RECORD_OPEN_SUBPATH_LENGTH = 3;
	private static final int RECORD_OPEN_SUBPATH_BEZIER_KNOT_LINKED = 4;
	private static final int RECORD_OPEN_SUBPATH_BEZIER_KNOT_UNLINKED = 5;
	private static final int RECORD_PATH_FILL_RULE = 6;
	private static final int RECORD_CLIPBOARD = 7;
	private static final int RECORD_INITIAL_FILL_RULE = 8;

	private int pathId;
	
	private List<PsdPath> subPathes;
	private Rect bounds;

	public PathBlock(int id, String name, int size)
	{
		super(id, name, size);
		pathId = id - 2000;
		subPathes = new ArrayList<PsdPath>();
	}

	@Override
	public void read(PsdInputStream stream) throws IOException
	{
		PathRecordsReader reader = new PathRecordsReader(stream, getSize());		
		while (reader.hasNextRecord())
		{
			int recordId = reader.nextRecordId();
			switch (recordId)
			{
				case RECORD_PATH_FILL_RULE:
				{
					if (reader.getReadCount() > 1)
						throw new IOException("Cannot read path block: path fill rule failed");
				}
					break;
				
				case RECORD_OPEN_SUBPATH_LENGTH:
				case RECORD_CLOSED_SUBPATH_LENGTH:
				{
					int numKnots = stream.readInt16();
					boolean closed = recordId == RECORD_CLOSED_SUBPATH_LENGTH;
					
					PsdPath subPath = new PsdPath(numKnots, closed);
					for (int knotIndex = 0; knotIndex < numKnots; ++knotIndex)
					{
						int knotRecordId = reader.nextRecordId();
						checkKnotRecordId(knotRecordId, closed);
						
						boolean knotLinked = knotRecordId == RECORD_CLOSED_SUBPATH_BEZIER_KNOT_LINKED || knotRecordId == RECORD_OPEN_SUBPATH_BEZIER_KNOT_LINKED;
						BezierKnot knot = readKnot(stream, knotLinked);
						subPath.addKnot(knot);
					}
					
					subPathes.add(subPath);
				}
					break;
					
				case RECORD_CLIPBOARD:
				{
					float boundsY = stream.readFixedFloat();
					float boundsX = stream.readFixedFloat();
					float boundsHeight = stream.readFixedFloat() - boundsY + 1;
					float boundsWidth = stream.readFixedFloat() - boundsX + 1;
					bounds = new Rect(boundsX, boundsY, boundsWidth, boundsHeight);
				}
					break;
					
				case RECORD_INITIAL_FILL_RULE:
					// ignore it
					break;

			}
		}
	}

	private void checkKnotRecordId(int knotRecordId, boolean closed) throws IOException
	{
		if (closed)
		{
			if (knotRecordId != RECORD_CLOSED_SUBPATH_BEZIER_KNOT_LINKED && knotRecordId != RECORD_CLOSED_SUBPATH_BEZIER_KNOT_UNLINKED)
				throw new IOException("Cannot read path block: knot record failed");
		}
		else
		{
			if (knotRecordId != RECORD_OPEN_SUBPATH_BEZIER_KNOT_LINKED && knotRecordId != RECORD_OPEN_SUBPATH_BEZIER_KNOT_UNLINKED)
				throw new IOException("Cannot read path block: knot record failed");
		}
	}
	
	private BezierKnot readKnot(PsdInputStream stream, boolean linked) throws IOException
	{
		Point[] points = new Point[3];
		for (int i = 0; i < 3; i++)
		{
			float y = stream.readFixedFloat();
			float x = stream.readFixedFloat();
			points[i] = new Point(x, y);
		}
		
		return new BezierKnot(points[0], points[1], points[2], linked);
	}

	public int getPathId()
	{
		return pathId;
	}

	public List<PsdPath> getSubPathes()
	{
		return subPathes;
	}
	
	public int subPathesCount()
	{
		return subPathes.size();
	}

	public Rect getBounds()
	{
		return bounds;
	}
}

class PathRecordsReader
{
	private static final int BYTES_PER_RECORD = 26;
	
	private PsdInputStream stream;
	private int startPos;
	private int numRecords;
	private int readCount;

	public PathRecordsReader(PsdInputStream stream, int size)
	{
		this.stream = stream;
		startPos = -1;
		numRecords = size / BYTES_PER_RECORD;
	}
	
	public int getReadCount()
	{
		return readCount;
	}
	
	public boolean hasNextRecord()
	{
		return readCount < numRecords;
	}
	
	public int nextRecordId() throws IOException
	{	
		if (!hasNextRecord())
		{
			throw new IOException("Can read path record");
		}
		if (startPos != -1)
		{
			stream.skip(BYTES_PER_RECORD - (stream.getPos() - startPos));
		}		
		startPos = stream.getPos();
		readCount++;
		return stream.readInt16();
	}

	public PsdInputStream getStream()
	{
		return stream;
	}
}
