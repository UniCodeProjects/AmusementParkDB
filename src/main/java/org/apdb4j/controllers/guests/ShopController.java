package org.apdb4j.controllers.guests;

import lombok.NonNull;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class ShopController implements ParkServiceController {
    @Override
    public Collection<String> getSortFields() {
        return null;
    }

    @Override
    public Collection<Map<String, String>> getMainInfo() {
        return null;
    }

    @Override
    public Map<String, String> getAllInfo(final String parkServiceName) {
        return null;
    }

    @Override
    public Map<String, Collection<String>> getAllFiltersWithValues() {
        return null;
    }

    @Override
    public Collection<Map<String, String>> filterBy(@NonNull String attribute, @NonNull String value) {
        return null;
    }

    @Override
    public Collection<Map<String, String>> filterByAverageRating(int maxRating, boolean ranged) {
        return null;
    }

    @Override
    public List<Map<String, String>> sortByAscending(@NonNull String sortField) {
        return null;
    }

    @Override
    public List<Map<String, String>> sortByDescending(@NonNull String sortField) {
        return null;
    }
}
