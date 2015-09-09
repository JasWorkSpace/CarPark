package com.greenorange.gooutdoor.framework.Model.Exception;

/**
 * Created by JasWorkSpace on 15/4/20.
 */
public class ApplicationException extends IllegalArgumentException {
    // we use IllegalArgumentException no Throwable
    // bc we don't want to write catch code.........
    public ApplicationException(){
        super();
    }

    public ApplicationException(String exception){
        super(exception);
    }


}
