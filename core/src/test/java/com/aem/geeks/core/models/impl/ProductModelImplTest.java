package com.aem.geeks.core.models.impl;


import com.aem.geeks.core.models.ProductModel;
import com.aem.geeks.core.services.BhargavOsgiConfigService;
import com.aem.geeks.core.services.DemoProductService;
import com.aem.geeks.core.services.OSGiFactoryConfig;
import com.aem.geeks.core.services.ProductService;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;





@ExtendWith(AemContextExtension.class)
class ProductModelImplTest {

    private final AemContext context = new AemContext();

    // Mocking all OSGi Services
    private ProductService productService = mock(ProductService.class);
    private DemoProductService demoProductService = mock(DemoProductService.class);
    private BhargavOsgiConfigService configService = mock(BhargavOsgiConfigService.class);
    private OSGiFactoryConfig factoryConfig = mock(OSGiFactoryConfig.class);

    @BeforeEach
    void setUp() {

        // Register model
        context.addModelsForClasses(ProductModelImpl.class);

        // Register mocked services
        context.registerService(ProductService.class, productService);
        context.registerService(DemoProductService.class, demoProductService, "component.name", "ServiceTwo");
        context.registerService(BhargavOsgiConfigService.class, configService);
        context.registerService(OSGiFactoryConfig.class, factoryConfig);

        // Create test resource (component node)
        context.create().resource("/content/test", "productName", "iPhone", "manufacturerName", "Apple", "productPrice", "1000",
                "productImageReference", "/content/dam/image.png", "multifieldvalues", new String[]{"one", "two"});

        context.currentResource("/content/test");
    }

    @Test
    void testBasicValueMapFields() {

        ProductModel model = context.request().adaptTo(ProductModel.class);

        assertEquals("Apple", model.getManufacturerName());
        assertEquals("/content/dam/image.png", model.getProductImageReference());
    }

    @Test
    void testProductNameValidation_Valid() {

        when(productService.validateProductName("iPhone")).thenReturn(true);

        ProductModel model = context.request().adaptTo(ProductModel.class);

        assertEquals("iPhone", model.getProductName());
    }


    @Test
    void testProductNameValidation_Invalid() {

        when(productService.validateProductName("iPhone")).thenReturn(false);

        ProductModel model = context.request().adaptTo(ProductModel.class);

        assertEquals("Please Provide Valid Name", model.getProductName());
    }


    @Test
    void testProductPriceValidation() {

        when(productService.validateProductPrice("1000")).thenReturn(true);

        ProductModel model = context.request().adaptTo(ProductModel.class);

        assertEquals("1000", model.getProductPrice());
    }


    @Test
    void testDemoProductService() {

        when(demoProductService.returnName()).thenReturn("Bhargav");

        ProductModel model = context.request().adaptTo(ProductModel.class);

        assertEquals("Bhargav", model.isReturnName());
    }


    @Test
    void testSimpleMultifield() {

        ProductModel model = context.request().adaptTo(ProductModel.class);

        assertArrayEquals(new String[]{"one", "two"}, model.getMultifieldvalues());
    }


    @Test
    void testCompositeMultifield() {

        // Create composite multifield structure
        context.create().resource("/content/test/productdetails/item1",
                "productnamemultifield", "Laptop",
                "buyerName", "John",
                "quantity", "2");

        context.create().resource("/content/test/productdetails/item2",
                "productnamemultifield", "Mobile",
                "buyerName", "Sam",
                "quantity", "5");

        ProductModel model = context.request().adaptTo(ProductModel.class);

        List<Map<String, String>> result = model.getProductDetailsWithMap();

        assertEquals(2, result.size());
        assertEquals("Laptop", result.get(0).get("productname"));
        assertEquals("John", result.get(0).get("buyername"));
        assertEquals("2", result.get(0).get("quantity"));
    }


    @Test
    void testNormalOSGiConfiguration() {

        when(configService.getPhoneNumber()).thenReturn(12345);
        when(configService.getName()).thenReturn("Bhargav");
        when(configService.getFavMovie()).thenReturn("Inception");
        when(configService.getCountries()).thenReturn(new String[]{"India", "USA"});
        when(configService.getRunModes()).thenReturn("author");

        ProductModel model = context.request().adaptTo(ProductModel.class);

        assertEquals(12345, model.getNumber());
        assertEquals("Bhargav", model.getName());
        assertEquals("Inception", model.getFavMovie());
        assertArrayEquals(new String[]{"India", "USA"}, model.getCountries());
        assertEquals("author", model.getRunModes());
    }


    @Test
    void testFactoryConfiguration() {

        List<OSGiFactoryConfig> configList = new ArrayList<>();
        configList.add(factoryConfig);

        when(factoryConfig.getAllConfigs()).thenReturn(configList);

        ProductModel model = context.request().adaptTo(ProductModel.class);

        assertEquals(1, model.getAllOSGiConfigs().size());
    }

}