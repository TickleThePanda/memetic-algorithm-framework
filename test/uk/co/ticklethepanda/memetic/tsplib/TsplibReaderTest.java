package uk.co.ticklethepanda.memetic.tsplib;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import uk.co.ticklethepanda.memetic.problem.solutions.tsp.Cities;
import uk.co.ticklethepanda.memetic.problem.solutions.tsp.City;

public class TsplibReaderTest {

  private static final String NO_CITIES_FILENAME = "no_cities.tsp";
  private static final String NO_CITIES_TEXT =
      "NAME: hello world\n"
          + "EDGE_WEIGHT_TYPE: EUC_2D\n";
  private static final Cities<?> EUC_NO_CITIES_EXPECTED =
      new Cities<City.Euclidian>(new ArrayList<City.Euclidian>());

  private static final String EUC_CITY_FILENAME = "one_city.tsp";
  private static final String EUC_CITY_TEXT =
      "NAME: hello world\n"
          + "EDGE_WEIGHT_TYPE: EUC_2D\n"
          + "1 0 1";
  private static final Cities<?> EUC_CITY_EXPECTED = new Cities<City.Euclidian>(
      Arrays.asList(new City.Euclidian[]{
          new City.Euclidian(0, 1)
      }));
  
  private static final String TWO_CITIES_FILENAME = "two_cities.tsp";
  private static final String TWO_CITIES_TEXT =
      "NAME: hello world\n"
          + "EDGE_WEIGHT_TYPE: EUC_2D\n"
          + "1 0 1\n"
          + "2 20 50";
  private static final Cities<?> EUC_TWO_CITIES_EXPECTED = new Cities<City.Euclidian>(
      Arrays.asList(new City.Euclidian[]{
          new City.Euclidian(0, 1),
          new City.Euclidian(20, 50)
      }));
  
  private static final String EUC_CITIES_DOUBLES_FILENAME = "euc_doubles_cities.tsp";
  private static final String EUC_CITIES_DOUBLES_TEXT =
      "NAME: hello world\n"
          + "EDGE_WEIGHT_TYPE: EUC_2D\n"
          + "1 0.0 1.2\n"
          + "2 20.0 50.5";
  private static final Cities<?> EUC_CITIES_DOUBLES_EXPECTED = new Cities<City.Euclidian>(
      Arrays.asList(new City.Euclidian[]{
          new City.Euclidian(0, 1),
          new City.Euclidian(20, 50)
      }));

  @Rule
  public TemporaryFolder folder = new TemporaryFolder();

  @Test
  public void testReadProblem_noCitiesInFile_emptyListReturned() throws IOException {
    File tspFile = createTsplibFile(NO_CITIES_FILENAME, NO_CITIES_TEXT);

    Cities<?> cities = TsplibReader.readProblem(tspFile);
    
    assertThat(cities, equalTo(EUC_NO_CITIES_EXPECTED));
  }

  @Test
  public void testReadProblem_eucCity_correctValues() throws IOException {
    File tspFile = createTsplibFile(EUC_CITY_FILENAME, EUC_CITY_TEXT);

    Cities<?> cities = TsplibReader.readProblem(tspFile);

    assertThat(cities, equalTo(EUC_CITY_EXPECTED));
  }

  @Test
  public void testReadProblem_twoCities_correctValues() throws IOException {
    File tspFile = createTsplibFile(TWO_CITIES_FILENAME, TWO_CITIES_TEXT);

    Cities<?> cities = TsplibReader.readProblem(tspFile);

    assertThat(cities, equalTo(EUC_TWO_CITIES_EXPECTED));
  }
  
  @Test
  public void testReadProblem_eucDoublesCities_correctValues() throws IOException {
    File tspFile = createTsplibFile(EUC_CITIES_DOUBLES_FILENAME, EUC_CITIES_DOUBLES_TEXT);

    Cities<?> cities = TsplibReader.readProblem(tspFile);

    assertThat(cities, equalTo(EUC_CITIES_DOUBLES_EXPECTED));
  }
  
  private File createTsplibFile(String filename, String text) {
    File file = null;
    try {
      file = folder.newFile(filename);
    } catch (IOException e) {
      fail("Unexpected IO error during setup, while creating file: " + e.getMessage());
    }
    try (FileWriter writer = new FileWriter(file)) {
      writer.write(text);
    } catch (IOException e) {
      fail("Unexpected IO error during setup, while writing content to file: " + e.getMessage());
    }
    return file;
  }

}
