package com.tibiawiki.process;

import com.tibiawiki.domain.enums.InfoboxTemplate;
import com.tibiawiki.domain.factories.ArticleFactory;
import com.tibiawiki.domain.factories.JsonFactory;
import com.tibiawiki.domain.repositories.ArticleRepository;
import org.json.JSONObject;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RetrieveStreets extends RetrieveAny {

    private static final String CATEGORY = "Streets";

    public RetrieveStreets() {
        super();
    }

    public RetrieveStreets(ArticleRepository articleRepository, ArticleFactory articleFactory, JsonFactory jsonFactory) {
        super(articleRepository, articleFactory, jsonFactory);
    }

    public List<String> getStreetsList() {
        final List<String> streetsCategory = articleRepository.getPageNamesFromCategory(CATEGORY);
        final List<String> listsCategory = articleRepository.getPageNamesFromCategory(CATEGORY_LISTS);

        return streetsCategory.stream()
                .filter(page -> !listsCategory.contains(page))
                .collect(Collectors.toList());
    }

    public Stream<JSONObject> getStreetsJSON() {
        return getArticlesFromInfoboxTemplateAsJSON(getStreetsList());
    }

    public Optional<JSONObject> getStreetJSON(String pageName) {
        return super.getArticleAsJSON(pageName);
    }
}
