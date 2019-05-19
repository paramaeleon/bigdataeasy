/*
 * Licensed under the terms of the Apache License 2.0. For terms and conditions,
 * see http://www.apache.org/licenses/LICENSE-2.0
 */
/**
 * Implements an easy-to-use library to use the concepts of Big Data in Java,
 * based on the understanding of the Java collections API.
 * <p>
 * All the big ones do, what is called Big Data, the Semantic Web, Resource
 * Description Framework (RDF), Resource Description Architecture (RDA), or
 * Linked Data / Linked Open Data (LOD). The remainder, however, manage their
 * data in spreadsheets or uses rigid software solutions to manage it. There’s
 * the lack that Big Data has not yet reached the small people. One might get
 * the impression that only seven people around the world have understood how
 * this works. One cause that I see for this is that the handling of existing
 * solutions seems unnecessarily complicated, if the amount of data you want to
 * deal with moves in orders of magnitude to fit into your PC’s memory. Big Data
 * Easy, the name is a bit of a dedication to New Orleans, has appeared to cure
 * this shortcoming. Built on the shoulders of Giants, this is heavily based on
 * Apache Jena. It reflects my understanding of the concepts of Big Bata that I
 * have gained in years of reflection about it, which does not rule out that I
 * could have misunderstood something. Similarities to previous approaches are
 * consequential, but I’ve reinvented this library from scratch, which has two
 * reasons:
 * <ol>
 * <li>Due to project requirements, previous work had been implemented under the
 * GPL, rendering it unusable for the majority of productive uses. This is an
 * unnecessary limitation that I always found a pity.</li>
 * <li>Previous work has been developed based on Java 6. Java has since taken
 * steps with seven league boots, introducing streams and {@code default}
 * methods in {@code interface}s, making much of the existing code appear
 * unnecessarily complicated and cluttered. There are better ways to do that
 * now, and that’s what I intended to do.</li>
 * </ol>
 */
package com.github.paramaeleon.bigdataeasy;