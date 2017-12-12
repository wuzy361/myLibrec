
package net.librec.data;

import java.io.IOException;

import net.librec.math.structure.SparseMatrix;
import net.librec.math.structure.SparseTensor;


public interface DataConvertor {

    
    public void processData() throws IOException;
    
    
    public SparseMatrix getPreferenceMatrix();

    
    public SparseMatrix getDatetimeMatrix();

    
    public SparseTensor getSparseTensor();
}
