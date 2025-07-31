package pl.michalsnella.mathlearning.model;

public class DivisionStep {
    protected final int segmentStartIndex;
    protected final String segment;
    protected final String multiple;
    protected final String subtraction;

    public DivisionStep(int segmentStartIndex, String segment, String multiple, String subtraction) {
        this.segmentStartIndex = segmentStartIndex;
        this.segment = segment;
        this.multiple = multiple;
        this.subtraction = subtraction;
    }


    public int getSegmentStartIndex() {
        return segmentStartIndex;
    }


    public String getSegment() {
        return segment;
    }


    public String getMultiple() {
        return multiple;
    }


    public String getSubtraction() {
        return subtraction;
    }
}

