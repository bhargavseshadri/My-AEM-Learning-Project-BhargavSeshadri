package com.aem.geeks.core.models.impl;

import com.aem.geeks.core.models.BhargavArticleListModel;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(
        adaptables = {Resource.class, SlingHttpServletRequest.class},
        adapters = BhargavArticleListModel.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class BhargavArticleListModelImpl implements BhargavArticleListModel {

    @ValueMapValue
    private String articleListRootPath;

    @Override
    public String getArticleListRootPath() {
        return articleListRootPath;
    }
}
