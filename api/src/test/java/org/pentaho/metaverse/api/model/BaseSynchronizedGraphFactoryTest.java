/*! ******************************************************************************
 *
 * Pentaho Data Integration
 *
 * Copyright (C) 2026 by Hitachi Vantara : http://www.pentaho.com
 *
 *******************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ******************************************************************************/

package org.pentaho.metaverse.api.model;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.tg.TinkerGraph;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BaseSynchronizedGraphFactoryTest {

  @Rule
  public TemporaryFolder tmp = new TemporaryFolder();

  @Test
  public void testDefaultGraphIsSynchronizedTinkerGraph() {
    Graph graph = BaseSynchronizedGraphFactory.getDefaultGraph();
    assertTrue( graph instanceof BaseSynchronizedGraph );
    Vertex v = graph.addVertex( "a" );
    v.setProperty( "name", "step" );
    graph.addEdge( null, v, graph.addVertex( "b" ), "hops" );
    assertEquals( "step", graph.getVertex( "a" ).getProperty( "name" ) );
    assertTrue( graph.getVertex( "a" ).getEdges( com.tinkerpop.blueprints.Direction.OUT ).iterator().hasNext() );
  }

  @Test
  public void testTinkerGraphSettingsAsGraphFactoryReadsThem() throws Exception {
    Map<String, String> config = new HashMap<>();
    config.put( "blueprints.graph", TinkerGraph.class.getName() );
    // TinkerGraph reads the directory's graph file when the directory exists, so start with none
    config.put( "blueprints.tg.directory", new File( tmp.getRoot(), "tg" ).getPath() );
    config.put( "blueprints.tg.file-type", "GRAPHML" );
    TinkerGraph tg = BaseSynchronizedGraphFactory.openTinkerGraph( config );
    tg.addVertex( "x" );
    tg.shutdown();  // a persistent TinkerGraph writes its file on shutdown
    assertTrue( new File( config.get( "blueprints.tg.directory" ), "tinkergraph.xml" ).exists() );
  }

  @Test
  public void testOpenFromPropertiesFile() throws Exception {
    File file = tmp.newFile( "graph.properties" );
    try ( Writer w = new FileWriter( file ) ) {
      w.write( "blueprints.graph=com.tinkerpop.blueprints.impls.tg.TinkerGraph\n" );
    }
    Graph graph = BaseSynchronizedGraphFactory.open( file.getPath() );
    assertTrue( graph instanceof BaseSynchronizedGraph );
    graph.addVertex( "a" );
    assertEquals( "a", graph.getVertex( "a" ).getId() );
  }
}
