package com.aem.geeks.core.services.impl;

import com.adobe.cq.dam.cfm.*;
import com.aem.geeks.core.services.RecipeSchedulerService;
import com.aem.geeks.core.utils.CfRecipeRLTD.Recipe;
import com.aem.geeks.core.utils.CfRecipeRLTD.RecipeResponse;
import com.aem.geeks.core.utils.ResolverUtil;
import com.google.gson.Gson;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ResourceUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;


/*BhargavSeshadri-
step:4 (CURRENT) (Last Step)- ServiceImpl - com/aem/geeks/core/services/impl/RecipeSchedulerServiceImpl.java
After this step we just create a helper classes

STEP: 3 - Create a Scheduler - com/aem/geeks/core/schedulers/RecipeScheduler.java

*/
@Component(service = RecipeSchedulerService.class)
public class RecipeSchedulerServiceImpl implements RecipeSchedulerService {

    private static final Logger LOG = LoggerFactory.getLogger(RecipeSchedulerServiceImpl.class);


    private static final String API_URL = "https://dummyjson.com/recipes";
    private static final String CF_PARENT_PATH = "/content/dam/bhargav-cf-model/bhargav-cf-recipes-folder";
    private static final String CF_MODEL_PATH = "/conf/Bhargav-Content-Fragments-config/settings/dam/cfm/models/bhargav-receipe-cf-model";

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    private List<Recipe> recipes;
    private int currentIndex = 0; // was 1 — was skipping recipe id 1

    @Override
    public void createNextRecipeContentFragment() {
        try {
            if (recipes == null) {
                loadRecipes();
            }
            if (recipes == null || recipes.isEmpty()) {
                return;
            }

            if (currentIndex >= recipes.size()) {
                currentIndex = 0;
            }

            Recipe recipe = recipes.get(currentIndex);

            try (ResourceResolver resolver = ResolverUtil.newResolver(resourceResolverFactory)) {
                createContentFragment(resolver, recipe);
                resolver.commit();
            }

            currentIndex++;
        } catch (Exception e) {
            LOG.error("Failed to create content fragment for recipe index {}", currentIndex, e);
        }
    }

    private void loadRecipes() throws Exception {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(API_URL);
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                Reader reader = new InputStreamReader(response.getEntity().getContent());
                Gson gson = new Gson();
                RecipeResponse recipeResponse = gson.fromJson(reader, RecipeResponse.class);
                recipes = recipeResponse.getRecipes();
                LOG.debug("Loaded {} recipes from API", recipes == null ? 0 : recipes.size());
            }
        }
    }

    private void createContentFragment(ResourceResolver resolver, Recipe recipe) throws Exception {
        String fragmentName = "recipe-" + recipe.getId();
        Resource existingResource = resolver.getResource(CF_PARENT_PATH + "/" + fragmentName);
        if (existingResource != null) {
            LOG.debug("Fragment {} already exists, skipping", fragmentName);
            return;
        }

        Resource modelResource = resolver.getResource(CF_MODEL_PATH);
        if (modelResource == null) {
            throw new IllegalStateException("CF Model not found at " + CF_MODEL_PATH);
        }
        FragmentTemplate template = modelResource.adaptTo(FragmentTemplate.class);
        if (template == null) {
            throw new IllegalStateException(CF_MODEL_PATH + " did not adapt to FragmentTemplate — check it's really a CF model");
        }

        // auto-create the parent folder instead of assuming it exists
        Resource parentFolder = ResourceUtil.getOrCreateResource(resolver, CF_PARENT_PATH,
                "sling:OrderedFolder", "sling:OrderedFolder", true);

        ContentFragment fragment = template.createFragment(parentFolder, fragmentName, recipe.getName());

        updateElement(fragment, "recipeId", recipe.getId());
        updateElement(fragment, "name", recipe.getName());
        updateElement(fragment, "servings", recipe.getServings());
        updateElement(fragment, "difficulty", recipe.getDifficulty());
        updateElement(fragment, "cuisine", recipe.getCuisine());
        updateElement(fragment, "mealType", String.join(",", recipe.getMealType()));

        LOG.debug("Created content fragment {} for recipe id {}", fragmentName, recipe.getId());
    }

    private void updateElement(ContentFragment fragment, String elementName, Object value) throws ContentFragmentException {
        ContentElement element = fragment.getElement(elementName);
        if (element == null) {
            LOG.debug("Element '{}' not found on CF Model — check the technical name matches exactly", elementName);
            return;
        }
        FragmentData fragmentData = element.getValue();
        fragmentData.setValue(value);
        element.setValue(fragmentData);
    }
}