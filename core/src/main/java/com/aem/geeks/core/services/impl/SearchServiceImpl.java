package com.aem.geeks.core.services.impl;

import com.aem.geeks.core.services.SearchService;
import com.aem.geeks.core.utils.ResolverUtil;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.Page;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Session;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//BhargavSeshadri : Step : 1 Creating a Service
//Step : 2 - Servlet : com/aem/geeks/core/servlets/GeeksSearchServlet.java
@Component(service = SearchService.class, immediate = true)
public class SearchServiceImpl implements SearchService{

    private static final Logger LOG= LoggerFactory.getLogger(SearchServiceImpl.class);

    @Reference
    QueryBuilder queryBuilder; // Getting querybuilder

    @Reference
    ResourceResolverFactory resourceResolverFactory;

    @Activate
    public void activate(){
        LOG.info("\n ----ACTIVATE METHOD----");
    }


    /*Putting the query in key value pair
    * path=/content/we-retail
    * type=cq:Page
    * fulltext=women
    * p.offset=
    * p.limit=-1
    * */
    public Map<String,String> createTextSearchQuery(String searchText,int startResult,int resultPerPage){
        Map<String,String> queryMap=new HashMap<>();
        queryMap.put("path","/content/we-retail");
        queryMap.put("type","cq:Page");
        queryMap.put("fulltext",searchText);
        queryMap.put("p.offset",Long.toString(startResult));
        queryMap.put("p.limit",Long.toString(resultPerPage));
        return queryMap;
    }

    @Override
    public JSONObject searchResult(String searchText,int startResult,int resultPerPage){
        LOG.info("\n ----SEARCH RESULT--------");
        JSONObject jsonObject=new JSONObject();
        try {
            ResourceResolver resourceResolver = ResolverUtil.newResolver(resourceResolverFactory);
            final Session session = resourceResolver.adaptTo(Session.class);

            //here we are creating the query using queryBuilder, and we are giving the Predicates using PredicateGroup.
            Query query = queryBuilder.createQuery(PredicateGroup.create(createTextSearchQuery(searchText,startResult,resultPerPage)), session);

            //Using the above line, we are getting the results.
            SearchResult result = query.getResult();

            //Each SearchResult is a HIT. below we are getting all the hits
            List<Hit> hits =result.getHits();
            JSONArray resultArray=new JSONArray();
            //Here we are iterating all the hits
            for(Hit hit: hits){

                //here from hit we are getting the Resource and using Resource we are adapting it to page. So we can get the complete info about the page
                Page page=hit.getResource().adaptTo(Page.class);  
                JSONObject resultObject=new JSONObject(); // we are crating the data into a Json object.
                resultObject.put("title",page.getTitle());
                resultObject.put("path",page.getPath());
                resultArray.put(resultObject);
                LOG.info("\n Page {} ",page.getPath());
            }
            jsonObject.put("results",resultArray);


            int perPageResults = result.getHits().size();
            long totalResults = result.getTotalMatches();
            long startingResult = result.getStartIndex();
            double totalPages = Math.ceil((double) totalResults / (double) resultPerPage);

            jsonObject.put("pages",totalPages);
            jsonObject.put("perpageresult",perPageResults);
            jsonObject.put("totalresults",totalResults);
            jsonObject.put("startingresult",startingResult);


        }catch (Exception e){
            LOG.info("\n ----ERROR -----{} ",e.getMessage());
        }
        return jsonObject;
    }
}
