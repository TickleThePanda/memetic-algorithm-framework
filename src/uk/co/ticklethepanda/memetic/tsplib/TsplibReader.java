package uk.co.ticklethepanda.memetic.tsplib;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.primitives.Doubles;

import uk.co.ticklethepanda.memetic.problem.solutions.tsp.Cities;
import uk.co.ticklethepanda.memetic.problem.solutions.tsp.City;

public class TsplibReader implements Iterable<String> {

  private static interface CityFactory<E extends City<E>> {
    public static final CityFactory<City.Euclidian> EUCLID_CITY_FACTORY = cityValues -> {
      final int xPos = (int) Math.floor(CityFactory.extractCoordinate(cityValues, 0));
      final int yPos = (int) Math.floor(CityFactory.extractCoordinate(cityValues, 1));
      return new City.Euclidian(xPos, yPos);
    };
    
    public static final CityFactory<City.Geo> GEO_CITY_FACTORY = cityValues -> {
      final double xPos = CityFactory.extractCoordinate(cityValues, 0);
      final double yPos = CityFactory.extractCoordinate(cityValues, 1);
      return new City.Geo(xPos, yPos);
    };
    
    static Double extractCoordinate(final String string, final int dimension) {
      Matcher matcher =
          Pattern.compile("(" + REGEX_NUMBER + ")" + REGEX_SPACES + "(" + REGEX_NUMBER + ")").matcher(string);
      matcher.find();
      String dimensionText = matcher.group(dimension + 1);
      return Doubles.tryParse(dimensionText);
    }

    public E createCityFromString(String cityValues);
  }

  private static final String REGEX_NUMBER = "[0-9]+[.]*[0-9]*";
  private static final String REGEX_SPACES = "[ ]*";

  public static Cities<?> readProblem(final File file) throws IOException {
    return new TsplibReader(file).getCities();
  }

  private final String contents;

  private final Map<String, String> metadata;

  private TsplibReader(final File file) throws IOException {
    metadata = new HashMap<String, String>();
    final byte[] bytes = Files.readAllBytes(file.toPath());
    contents = new String(bytes);
  }

  private <E extends City<E>> Cities<E> createCities(final CityFactory<E> factory) {
    final List<E> list = new ArrayList<E>();

    for (String string : this) {
      list.add(factory.createCityFromString(string));
    }

    return new Cities<E>(list);
  }

  private Cities<?> getCities() {
    final String edgeWeightType = getMetadata("EDGE_WEIGHT_TYPE");

    switch (edgeWeightType) {
    case "GEO":
      return createCities(CityFactory.GEO_CITY_FACTORY);
    case "EUC_2D":
      return createCities(CityFactory.EUCLID_CITY_FACTORY);
    default:
      throw new UnsupportedOperationException(
          "EDGE_WEIGHT_TYPE \"" + edgeWeightType + "\" is not supported.");
    }
  }

  private String getMetadata(final String key) {
    if (metadata.get(key) == null) {
      Matcher matcher =
          Pattern.compile(key + "\\s*:\\s*(.*)", Pattern.CASE_INSENSITIVE).matcher(contents);
      matcher.find();
      final String value = matcher.group(1);
      metadata.put(key, value);
      return value;
    }
    return metadata.get(key);
  }

  @Override
  public Iterator<String> iterator() {
    return new Iterator<String>() {

      private static final String INDEX_TAG = "%index";
      private static final String REGEX_WITHOUT_INDEX =
          INDEX_TAG + REGEX_SPACES + "(" + REGEX_NUMBER + REGEX_SPACES + REGEX_NUMBER + ")";
      
      private static final int CITY_COORDINATES_REGEX_GROUP = 1;

      private int currentCityIndex = 0;

      @Override
      public boolean hasNext() {
        return createCityMatcher(currentCityIndex + 1).find();
      }

      @Override
      public String next() {
        currentCityIndex++;

        Matcher matcher = createCityMatcher(currentCityIndex);
        matcher.find();
        String numbers = matcher.group(CITY_COORDINATES_REGEX_GROUP);

        return numbers;
      }

      private Matcher createCityMatcher(int cityNumber) {
        String regex = REGEX_WITHOUT_INDEX.replace(INDEX_TAG, String.valueOf(cityNumber));

        return Pattern.compile(regex).matcher(contents);
      }

    };
  }

}
