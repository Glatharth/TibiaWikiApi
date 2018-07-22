package com.tibiawiki.process;

import com.tibiawiki.domain.factories.ArticleFactory;
import com.tibiawiki.domain.factories.JsonFactory;
import com.tibiawiki.domain.repositories.ArticleRepository;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RetrieveHuntingPlaces extends RetrieveAny {

    private static final String CATEGORY_HUNTING_PLACES = "Hunting Places";

    public RetrieveHuntingPlaces() {
        super();
    }

    public RetrieveHuntingPlaces(ArticleRepository articleRepository, ArticleFactory articleFactory, JsonFactory jsonFactory) {
        super(articleRepository, articleFactory, jsonFactory);
    }

    public Stream<JSONObject> getHuntingPlacesJSON() {
        return getHuntingPlacesJSON(ONE_BY_ONE);
    }

    public Stream<JSONObject> getHuntingPlacesJSON(boolean oneByOne) {
        final List<String> huntingPlacesCategory = new ArrayList<>();
        for (String pageName : articleRepository.getMembersFromCategory(CATEGORY_HUNTING_PLACES)) {
            huntingPlacesCategory.add(pageName);
        }

        final List<String> listsCategory = new ArrayList<>();
        for (String pageName : articleRepository.getMembersFromCategory(CATEGORY_LISTS)) {
            listsCategory.add(pageName);
        }

        final List<String> pagesInHuntingPlacesCategoryButNotLists = huntingPlacesCategory.stream()
                .filter(page -> !listsCategory.contains(page))
                .collect(Collectors.toList());

        return oneByOne
                ? obtainArticlesOneByOne(pagesInHuntingPlacesCategoryButNotLists)
                : obtainArticlesInBulk(pagesInHuntingPlacesCategoryButNotLists);
    }

    public Optional<JSONObject> getHuntingPlaceJSON(String pageName) {
        return super.getArticleJSON(pageName);
    }

}
