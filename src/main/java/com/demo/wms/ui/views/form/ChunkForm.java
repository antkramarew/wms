package com.demo.wms.ui.views.form;

import com.demo.wms.domain.ChunkItem;
import com.demo.wms.domain.Product;
import com.demo.wms.services.ProductService;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Scope;
import org.vaadin.viritin.MBeanFieldGroup;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.fields.MCheckBox;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.form.AbstractForm;
import org.vaadin.viritin.layouts.MCssLayout;
import org.vaadin.viritin.layouts.MFormLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

import javax.annotation.PostConstruct;

@SpringComponent
@UIScope
public class ChunkForm extends AbstractForm<ChunkItem> {

    public static final String CHUNK_FORM_FIELD_ID = "chunk.form.field.id";
    public static final String CHUNK_FORM_FIELD_QUANTITY = "chunk.form.field.quantity";
    public static final String CHUNK_FORM_FIELD_DEFECT = "chunk.form.field.defect";

    @Autowired
    private ProductService productService;
    @Autowired
    private MessageSource messageSource;

    private ChunkItem chunk;
    @PropertyId("id")
    private MTextField id;
    @PropertyId("quantity")
    private  MTextField quantity;
    @PropertyId("defect")
    private MCheckBox defect;


    private final MButton save = new MButton(FontAwesome.SAVE,"Save", e -> {
        productService.saveChunk(chunk);
        closePopup();
    }).withClickShortcut(ShortcutAction.KeyCode.ENTER)
            .withStyleName(ValoTheme.BUTTON_PRIMARY);

    private final MButton cancel = new MButton("Cancel", event -> {
        closePopup();
    });

    private final CssLayout actions = new MCssLayout(save, cancel).withStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
    private MFormLayout formLayout;

    @Override
    protected Component createContent() {

        formLayout = new MFormLayout(
                this.id,
                this.quantity,
                this.defect,
                this.actions).withWidth("");
        MVerticalLayout verticalLayout = new MVerticalLayout(formLayout, getToolbar()).withWidth("");
        return verticalLayout;
    }


    public interface ChangeHandler {

        void onChange();
    }



    @Override
    public MBeanFieldGroup<ChunkItem> setEntity(ChunkItem entity) {
        this.chunk = entity;
        return super.setEntity(entity);
    }

    public void setChangeHandler(ChangeHandler h) {
        save.addClickListener(e -> h.onChange());
    }

    @PostConstruct
    public void init() {
        id = new MTextField(messageSource.getMessage(CHUNK_FORM_FIELD_ID,null,null));
        quantity = new MTextField(messageSource.getMessage(CHUNK_FORM_FIELD_QUANTITY,null,null));
        defect = new MCheckBox(messageSource.getMessage(CHUNK_FORM_FIELD_DEFECT,null,null));
    }


}