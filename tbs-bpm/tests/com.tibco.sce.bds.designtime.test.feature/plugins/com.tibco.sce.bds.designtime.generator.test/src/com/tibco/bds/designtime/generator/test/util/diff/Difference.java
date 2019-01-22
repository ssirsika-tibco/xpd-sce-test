package com.tibco.bds.designtime.generator.test.util.diff;
import java.io.File;

public class Difference {

    public enum DifferenceType {
        ADD, REMOVE, CHANGE
    };

    private DifferenceType type;

    private File file;

    public Difference(DifferenceType type, File file) {
        this.type = type;
        this.file = file;
    }
    
    public File getFile() {
        return file;
    }
    
    public DifferenceType getType() {
        return type;
    }
    
    public String toString()
    {
        return String.format("%s %s", type, file); //$NON-NLS-1$
    }

}
