package com.chen.formdroid.fdcore.template.form;

import java.util.List;

/**
 *
 * this will be used to parse a cvs file to json format
 *
 *
 * Created by chen on 8/5/15.
 */
public class CvsFormParser implements IFormParser{
    private List<String[]> mCvsContent;

    public CvsFormParser(List<String[]> cvsContent){
        this.mCvsContent = cvsContent;
    }

    @Override
    public Form getForm() {
        //TODO implement this
        throw new RuntimeException("not implemented");
    }
}
