package com.demo.wms.util.vaadin;

import com.vaadin.data.validator.AbstractValidator;

import java.util.Collection;

public class NonEmptyCollectionValidator extends AbstractValidator<Collection> {

    public NonEmptyCollectionValidator(String errorMessage) {
        super(errorMessage);
    }

    @Override
    protected boolean isValidValue(Collection value) {
        return value != null && !value.isEmpty();
    }

    @Override
    public Class<Collection> getType() {
        return Collection.class;
    }
}