package com.kalanblow.school_management.model.util;

import com.kalanblow.school_management.model.PhoneNumber;
import org.apache.commons.lang3.StringUtils;

import java.beans.PropertyEditorSupport;

public class PhoneNumberPropertyEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (StringUtils.isNotBlank(text)) {
            this.setValue(new PhoneNumber(text));
        } else {
            this.setValue(null);
        }
    }

    @Override
    public String getAsText() {

        PhoneNumber value = (PhoneNumber) getValue();
        return value != null ? value.asString() : "";

    }

}
