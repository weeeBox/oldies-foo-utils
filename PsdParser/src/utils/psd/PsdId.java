package utils.psd;

public class PsdId
{
	public static final int UNDEFINED = 0;
    public static final int MAC_PRINT_INFO = 1001;
    public static final int RESOLUTION_INFO = 1005;
    public static final int ALPHA_CHANNEL_NAMES = 1006;
    public static final int DISPLAY_INFO = 1007;
    public static final int CAPTION = 1008;
    public static final int BORDER_INFO = 1009;
    public static final int BG_COLOR = 1010;
    public static final int PRINT_FLAGS = 1011; //1-byte values labels, crop marks, color bars, registration marks, negative, flip, interpolate, caption.
    public static final int MULTI_CHANNEL_HALFTONE_INFO = 1012;
    public static final int COLOR_HALFTONE_INFO = 1013;
    public static final int DUOTONE_HALFTONE_INFO = 1014;
    public static final int MULTI_CHANNEL_TRANSFER_FUNCTIONS = 1015;
    public static final int COLOR_TRANSFER_FUNCTIONS = 1016;
    public static final int DUOTONE_TRANSFER_FUNCTIONS = 1017;
    public static final int DUOTONE_IMAGE_INFO = 1018;
    public static final int BLACK_WHITE_RANGE = 1019;
    public static final int EPS_OPTIONS = 1021;
    public static final int QUICK_MASK_INFO = 1022; //2 bytes containing Quick Mask channel ID, 1 byte boolean indicating whether the mask was initially empty.
    public static final int LAYER_STATE_INFO = 1024; //2 bytes containing the index of target layer. 0=bottom layer.
    public static final int WORKING_PATH_UNSAVED = 1025;
    public static final int LAYERS_GROUP_INFO = 1026;
    public static final int IPTC_NAA = 1028;
    public static final int RAW_FORMAT_IMAGE_MODE = 1029;
    public static final int JPEG_QUALITY = 1030;
    public static final int GRID_GUIDES_INFO = 1032;
    public static final int THUMBNAIL1 = 1033;
    public static final int COPYRIGHT_INFO = 1034;
    public static final int URL = 1035;
    public static final int THUMBNAIL2 = 1036;
    public static final int GLOBAL_ANGLE = 1037;
    public static final int COLOR_SAMPLERS = 1038;
    public static final int ICC_PROFILE = 1039; //The raw bytes of an ICC format profile, see the ICC34.pdf and ICC34.h files from the Internation Color Consortium located in the documentation section
    public static final int WATERMARK = 1040;
    public static final int ICC_UNTAGGED = 1041; //1 byte that disables any assumed profile handling when opening the file. 1 = intentionally untagged.
    public static final int EFFECTS_VISIBLE = 1042; //1 byte global flag to show/hide all the effects layer. Only present when they are hidden.
    public static final int SPOT_HALFTONE = 1043; // 4 bytes for version, 4 bytes for length, and the variable length data.
    public static final int DOCUMENT_SPECIFIC_IDS = 1044;
    public static final int UNICODE_ALPHANAMES = 1045;
    public static final int INDEXED_COLOR_TABLE_COUNT = 1046; // 2 bytes for the number of colors in table that are actually defined
    public static final int TRANSPARENT_INDEX = 1047;
    public static final int GLOBAL_ALTITUDE = 1049;  // 4 byte entry for altitude
    public static final int SLICES = 1050;
    public static final int WORKFLOW_URL = 1051; //Unicode string, 4 bytes of length followed by unicode string
    public static final int JUMP_TO_XPEP = 1052; //2 bytes major version, 2 bytes minor version,
    //4 bytes count. Following is repeated for count: 4 bytes block size,
    //4 bytes key, if key = 'jtDd' then next is a Boolean for the dirty flag
    //otherwise its a 4 byte entry for the mod date
    public static final int ALPHA_IDENTIFIERS = 1053; //4 bytes of length, followed by 4 bytes each for every alpha identifier.
    public static final int URL_LIST = 1054; //4 byte count of URLs, followed by 4 byte long, 4 byte ID, and unicode string for each count.
    public static final int VERSION_INFO = 1057;
    public static final int UNKNOWN4 = 1058; //pretty long, 302 bytes in one file. Holds creation date, maybe Photoshop license number
    public static final int XML_INFO = 1060;
    public static final int UNKNOWN = 1061; //seems to be common!
    public static final int UNKNOWN2 = 1062; //seems to be common!
    public static final int UNKNOWN3 = 1064; //seems to be common!
    public static final int PATH_INFO_FIRST = 2000; //2000-2999 actually I think?
    public static final int PATH_INFO_LAST = 2999; //2000-2999 actually I think?
    public static final int CLIPPING_PATH_NAME = 2999;
    public static final int PRINT_FLAGS_INFO = 10000; //2 bytes version (=1), 1 byte center crop marks, 1 byte (=0), 4 bytes bleed width value, 2 bytes bleed width scale
}
