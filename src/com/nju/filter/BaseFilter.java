package com.nju.filter;

import javax.servlet.Filter;
import org.apache.log4j.Logger;

public abstract class BaseFilter implements Filter {
	public Logger logger;
    /**
     * Default constructor. 
     */
    public BaseFilter() {
        // TODO Auto-generated constructor stub
    	logger = Logger.getLogger(this.getClass());
    }
}
