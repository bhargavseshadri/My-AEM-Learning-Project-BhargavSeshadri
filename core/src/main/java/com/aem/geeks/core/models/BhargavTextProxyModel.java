package com.aem.geeks.core.models;

import com.adobe.cq.wcm.core.components.models.Text;

public interface BhargavTextProxyModel extends Text {
//Observe - here we are extending Text Interface

    String getCustomField();
}
