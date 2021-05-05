package pages;

import components.Product;

import java.util.DoubleSummaryStatistics;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class Page<T extends Product> {

    private Set<String> getDistinctBrands(List<T> productList) {
        return new HashSet<String>(productList.stream().map(T::getBrand).collect(Collectors.toList()));
    }

    private Set<String> getDistinctNames(List<T> productList) {
        return new HashSet<String>(productList.stream().map(T::getName).collect(Collectors.toList()));
    }

    private DoubleSummaryStatistics getPriceStatistics(List<T> productList) {
        return productList.stream().map(T::getPrice).mapToDouble(Double::doubleValue).summaryStatistics();
    }
}
