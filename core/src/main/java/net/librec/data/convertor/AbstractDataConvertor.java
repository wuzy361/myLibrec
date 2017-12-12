
package net.librec.data.convertor;

import net.librec.data.DataConvertor;
import net.librec.job.progress.ProgressReporter;
import net.librec.math.structure.SparseMatrix;
import net.librec.math.structure.SparseTensor;


public abstract class AbstractDataConvertor extends ProgressReporter implements DataConvertor {

    
    protected SparseMatrix preferenceMatrix;

    
    protected SparseMatrix datetimeMatrix;

    
    protected SparseTensor sparseTensor;

    
    public SparseMatrix getPreferenceMatrix() {
        return preferenceMatrix;
    }

    
    public SparseMatrix getDatetimeMatrix() {
        return datetimeMatrix;
    }

    
    public SparseTensor getSparseTensor() {
        return sparseTensor;
    }

}
