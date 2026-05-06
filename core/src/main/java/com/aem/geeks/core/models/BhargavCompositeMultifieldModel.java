package com.aem.geeks.core.models;

import com.aem.geeks.core.models.impl.compositemultifieldrltd.BhargavCompositeMultifieldChildModel;

import java.util.List;

public interface BhargavCompositeMultifieldModel {
    List<BhargavCompositeMultifieldChildModel> getItems();
}
