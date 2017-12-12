
package net.librec.data;

import net.librec.common.LibrecException;
import net.librec.math.structure.SparseMatrix;


public interface DataSplitter {

    
    enum SplitterType {
        GENERIC, GIEVNN, RATIO, VALIDATION
    }

    
    public void splitData() throws LibrecException;

    
    public void setDataConvertor(DataConvertor dataConvertor);

    
    public SparseMatrix getTrainData();

    
    public SparseMatrix getTestData();

    
    public SparseMatrix getValidData();
}
