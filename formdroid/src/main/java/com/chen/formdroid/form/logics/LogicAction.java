package com.chen.formdroid.form.logics;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * logic object to hold logic information about a field
 *
 * Created by chen on 8/5/15.
 */
public class LogicAction {

    @JsonProperty
    public String action;

    @JsonProperty
    public String target;

    @JsonProperty
    public String targetFormId;

    @JsonProperty
    public String targetFieldId;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getTargetFormId() {
        return targetFormId;
    }

    public void setTargetFormId(String targetFormId) {
        this.targetFormId = targetFormId;
    }

    public String getTargetFieldId() {
        return targetFieldId;
    }

    public void setTargetFieldId(String targetFieldId) {
        this.targetFieldId = targetFieldId;
    }
}
