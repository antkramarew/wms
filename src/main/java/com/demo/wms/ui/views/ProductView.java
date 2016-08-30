package com.demo.wms.ui.views;

import com.demo.wms.domain.ChunkItem;
import com.demo.wms.domain.Product;
import com.demo.wms.services.ProductService;
import com.demo.wms.ui.views.form.ChunkForm;
import com.demo.wms.ui.views.form.ProductForm;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.util.StringUtils;
import org.vaadin.viritin.button.ConfirmButton;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.fields.MTable;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.layouts.MCssLayout;
import org.vaadin.viritin.layouts.MHorizontalLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

import javax.annotation.PostConstruct;


@SpringView(name="product_view")
public class ProductView extends VerticalSplitPanel implements View {


    @Autowired
    private MessageSource messageSource;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductForm productForm;

    @Autowired
    private ChunkForm chunkForm;

    private ProductPanel productPanel;

    private ChunkPanel chunkPanel;


    public ProductView() {
        setSizeFull();
        setLocked(true);
    }


    @PostConstruct
    public void init() {
        productPanel = new ProductPanel();
        chunkPanel = new ChunkPanel();
        productForm.setChangeHandler(() -> {
            productPanel.listProducts(null);
        });
        chunkForm.setChangeHandler(() -> {
            chunkPanel.show(productPanel.getSelectedProduct());
            productPanel.listProducts(null);
            productPanel.selectProduct(chunkPanel.currentProduct);
        });

        setFirstComponent(productPanel);
        setSecondComponent(chunkPanel);
        //setSplitPosition(70, Unit.PERCENTAGE);
    }




    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        productPanel.listProducts(null);
    }


    public class ProductPanel extends MVerticalLayout{

        public static final String PRODUCT_VIEW_FILTER_PROMPT = "product.view.filter.prompt";
        public static final String PRODUCT_VIEW_TABLE_HEADER_SKU = "product.view.table.header.sku";
        public static final String PRODUCT_VIEW_TABLE_HEADER_NAME = "product.view.table.header.name";
        public static final String PRODUCT_VIEW_TABLE_HEADER_WIDTH = "product.view.table.header.width";
        public static final String PRODUCT_VIEW_TABLE_HEADER_PRICE = "product.view.table.header.price";
        public static final String PRODUCT_VIEW_TABLE_HEADER_TRADE_PRICE = "product.view.table.header.trade.price";
        public static final String PRODUCT_VIEW_TABLE_HEADER_TRADE_STOCK = "product.view.table.header.trade.stock";


        private final MTable<Product> table = new MTable(Product.class)
                .withProperties("sku", "name", "width", "price", "tradePrice", "stock")
                .withColumnHeaders(
                        messageSource.getMessage(PRODUCT_VIEW_TABLE_HEADER_SKU,null,null),
                        messageSource.getMessage(PRODUCT_VIEW_TABLE_HEADER_NAME,null,null),
                        messageSource.getMessage(PRODUCT_VIEW_TABLE_HEADER_WIDTH,null,null),
                        messageSource.getMessage(PRODUCT_VIEW_TABLE_HEADER_PRICE,null,null),
                        messageSource.getMessage(PRODUCT_VIEW_TABLE_HEADER_TRADE_PRICE,null,null),
                        messageSource.getMessage(PRODUCT_VIEW_TABLE_HEADER_TRADE_STOCK, null, null))

                .withFullWidth();

        private final MButton add = new MButton(FontAwesome.PLUS,
                event -> {
                    productForm.setEntity(new Product());
                    productForm.openInModalPopup();
                });

        private final ConfirmButton delete = new ConfirmButton(FontAwesome.TRASH_O,"Are you sure?",
                event -> {
                    productService.delete(table.getValue().getId());
                    table.setSelected(null);
                    listProducts(null);
                });

        private final MButton edit = new MButton(FontAwesome.PENCIL_SQUARE_O,
                event -> {
                    productForm.setEntity(productService.findOne(table.getValue().getId()));
                    productForm.openInModalPopup();
                });

        private final MCssLayout actions = new MCssLayout(add, edit, delete)
                .withStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

        private final MTextField filter = new MTextField()
                .withInputPrompt(messageSource.getMessage(PRODUCT_VIEW_FILTER_PROMPT,null,null))
                .withTextChangeListener(e -> {
                    table.setSelected(null);
                    listProducts(e.getText());
                });

        private final MHorizontalLayout toolbar = new MHorizontalLayout(filter, actions)
                .withSpacing(true);

        public ProductPanel() {
            addComponents(toolbar, table);
            setSpacing(true);
            setMargin(true);

            table.setSelectable(true);
            table.setPageLength(5);
            table.addMValueChangeListener(e -> {
                if (table.getValue() != null) {
                    chunkPanel.show(table.getValue());
                }else{
                    chunkPanel.setVisible(false);
                }
                applyButtonStates();
            });
        }

        public void selectProduct(Product productToSelect) {
            table.select(productToSelect);
        }

        public Product getSelectedProduct() {
            return table.getValue();
        }
        public void listProducts(String text) {
            if (StringUtils.isEmpty(text)) {
                table.setBeans(productService.findAll());
            }
            else {
                table.setBeans(productService.findBySKUStartsWithIgnoreCase(text));
            }
            applyButtonStates();
        }

        private void applyButtonStates() {
            boolean isSelected = table.getValue() != null;
            edit.setEnabled(isSelected);
            delete.setEnabled(isSelected);
        }

    }

    public class ChunkPanel extends MVerticalLayout{

        public static final String PRODUCT_VIEW_CHUNK_TABLE_HEADER_ID = "product.view.chunk.table.header.id";
        public static final String PRODUCT_VIEW_CHUNK_TABLE_HEADER_QUANTITY = "product.view.chunk.table.header.quantity";
        public static final String PRODUCT_VIEW_CHUNK_TABLE_HEADER_DEFECT = "product.view.chunk.table.header.defect";

        private final MTable<ChunkItem> chunkTable = new MTable(ChunkItem.class)
                .withProperties("id", "quantity", "defect")
                .withColumnHeaders(
                        messageSource.getMessage(PRODUCT_VIEW_CHUNK_TABLE_HEADER_ID, null, null),
                        messageSource.getMessage(PRODUCT_VIEW_CHUNK_TABLE_HEADER_QUANTITY, null, null),
                        messageSource.getMessage(PRODUCT_VIEW_CHUNK_TABLE_HEADER_DEFECT, null, null)
                        )
                .withFullWidth();

        private Product currentProduct;

        public ChunkPanel() {
            addComponents(actions, chunkTable);
            setVisible(false);
            chunkTable.setSelectable(true);
            chunkTable.setPageLength(5);
        }


                private final MButton add = new MButton(FontAwesome.PLUS,
                        event -> {
                            chunkForm.setEntity(new ChunkItem(currentProduct.getId()));
                            chunkForm.openInModalPopup();
                        });

                private final ConfirmButton delete = new ConfirmButton(FontAwesome.TRASH_O,"Are you sure?",
                        event -> {
                            productService.deleteChunk(chunkTable.getValue());
                            chunkTable.setSelected(null);
                            listOfChunks(this.currentProduct);
                            productPanel.listProducts(null);
                            productPanel.selectProduct(currentProduct);

                            });

        private void show(Product currentProduct) {
            setVisible(true);
            this.currentProduct = currentProduct;
            listOfChunks(currentProduct);
        }

        public void listOfChunks(Product currentProduct) {
            if(currentProduct != null) {
                chunkTable.setBeans(productService.findProductChunks(currentProduct.getId()));
            }
        }

        private final MButton edit = new MButton(FontAwesome.PENCIL_SQUARE_O,
                event -> {
                    chunkForm.setEntity(productService.findChunk(chunkTable.getValue().getId(), currentProduct.getId()));
                    chunkForm.openInModalPopup();
                });

        private final MCssLayout actions = new MCssLayout(add, edit, delete)
                .withStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
    }

}