package com.aem.geeks.core.models;

import com.aem.geeks.core.beans.BhargavArticleListDataBean;

import java.util.List;

public interface BhargavArticleListModel {

    String getArticleListRootPath();

    //this getter will return the ArrayList contains all the objects
    List<BhargavArticleListDataBean> getBhargavArticleListDataBeanList();
}
