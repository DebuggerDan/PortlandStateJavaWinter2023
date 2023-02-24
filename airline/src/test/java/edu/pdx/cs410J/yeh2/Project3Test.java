//package edu.pdx.cs410J.yeh2;
//
//import org.junit.jupiter.api.Test;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//
//import static org.hamcrest.CoreMatchers.*;
//import static org.hamcrest.MatcherAssert.assertThat;
//
///**
// * A unit test for code in the <code>Project3</code> class.  This is different
// * from <code>Project3IT</code> which is an integration test (and can capture data
// * written to {@link System#out} and the like.
// */
//class Project3Test {
//
//  @Test
//  void readmeCanBeReadAsResource() throws IOException {
//    try (
//      InputStream readme = Project3.class.getResourceAsStream("README3.txt")
//    ) {
//      assertThat(readme, not(nullValue()));
//      BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
//      String line = reader.readLine();
//      assertThat(line, containsString("CS410P: Advanced Java Programming, Winter 2023 - Dan Jang, February 2023 [#3]"));
//    }
//  }
//
////  @Test
////  void runNormally()
////  {
////
////  }
//}
