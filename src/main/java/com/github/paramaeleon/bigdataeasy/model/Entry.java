/*
 * Licensed under the terms of the Apache License 2.0. For terms and conditions,
 * see http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.paramaeleon.bigdataeasy.model;

import java.util.*;
import java.util.stream.*;

import javax.annotation.*;

import org.apache.commons.lang3.tuple.*;

/**
 * One piece of information.
 */
public interface Entry {
    /**
     * Provides access to the {@code rdfs:ContainerMembershipProperty}. This is
     * a filtered view of the entries referenced as ordered list entries.
     *
     * @return
     * @see "https://www.w3.org/TR/rdf-schema/#ch_containermembershipproperty"
     */
    ContainerMembershipPropertyView containerMembershipProperty();

    /**
     * Tests whether an element is directly referenced from this entry.
     *
     * @param o object to search
     * @return whether the object is contained
     */
    boolean contains(Object o);

    /**
     * Tests whether this entry has an outgoing edge labeled by the given
     * identifier.
     *
     * @param identifier identifier to look for
     * @return whether the identifier is contained
     */
    default boolean containsKey(Entry edge) {
        return containsKey(edge.getIdentifier());
    }

    /**
     * Tests whether this entry has an outgoing edge labeled by the given
     * identifier.
     *
     * @param identifier identifier to look for
     * @return whether the identifier is contained
     */
    boolean containsKey(String identifier);

    /**
     * Returns all entries in this entry. Returns a set, that means, multiple
     * linked entries are returned only once, and there is no order.
     *
     * @return all entries in this entry
     */
    default Collection<Entry> elements() {
        return entriesStream()
            .flatMap(λ ->
                λ.getValue().parallelStream()
            )
        .collect(Collectors.toSet());
    }

    /**
     * Provides the entries of this node as stream grouped by relation.
     *
     * @return the entries node as stream
     * @throws StackOverflowError if the underlying implementation does neither
     *                            implement {@code entriesStream()} nor
     *                            {@code entryStream()}. If reasonable, the
     *                            underlying implementation should implement
     *                            {@code entriesStream()}, and there is nothing
     *                            wrong with implementing both.
     */
    default Stream<Pair<String, Collection<Entry>>> entriesStream() {
        Map<String, Collection<Entry>> collector = new HashMap<>();
        entryStream().forEachOrdered(λ ->
            collector.computeIfAbsent(λ.getKey(), μ -> new LinkedList<>())
                     .add(λ.getValue())
        );
        return collector.entrySet().parallelStream()
            .map(λ ->
                Pair.of(λ.getKey(), λ.getValue())
            )
        ;
    }

    /**
     * Provides the entries of this node as stream one by one.
     *
     * @return the entries node as stream
     * @throws StackOverflowError if the underlying implementation does neither
     *                            implement {@code entriesStream()} nor
     *                            {@code entryStream()}. If reasonable, the
     *                            underlying implementation should implement
     *                            {@code entriesStream()}, and there is nothing
     *                            wrong with implementing both.
     */
    default Stream<Pair<String, Entry>> entryStream() {
        return entriesStream()
            .flatMap(λ ->
                λ.getRight().parallelStream()
                    .map(μ -> Pair.of(λ.getLeft(), μ)
                )
            )
        ;
    }

    /**
     * Returns all entries referenced by the given relation.
     *
     * @param relation relation to look up
     * @return the value of the literal.
     */
    default Collection<Entry> get(Entry relation) {
        return get(relation.getIdentifier());
    }

    /**
     * Gets all nodes referenced by relation which have a certain type.
     *
     * @param relation relation to look up
     * @param type     node type to filter by
     * @return the nodes
     */
    default Collection<Entry> get(Entry relation, Entry type) {
        return get(relation.getIdentifier(), type.getIdentifier());
    }

    /**
     * Gets all entries referenced by the given relation which have a certain
     * type.
     *
     * @param relation relation to look up
     * @param type     type to filter by
     * @return the entries
     */
    default Collection<Entry> get(Entry relation, String type) {
        return get(relation.getIdentifier(), type);
    }

    /**
     * Returns all entries referenced by a relation.
     *
     * @param relation relation to look up
     * @return entries referenced by this relation
     */
    Collection<Entry> get(String relation);

    /**
     * Returns all entries referenced by a given relation which have a certain
     * type.
     *
     * @param relation relation to look up
     * @param type     type to filter by
     * @return the entries
     */
    default Collection<Entry> get(String relation, Entry type) {
        return get(relation, type.getIdentifier());
    }

    /**
     * Returns all entries referenced by a given relation which have a certain
     * type.
     *
     * @param relation relation to look up
     * @param type     type to filter by
     * @return the entries
     */
    Collection<Entry> get(String relation, String type);

    /**
     * Returns an entry by its identifier.
     *
     * @param identifier to identify the object
     * @return the identifier
     * @throws NoSuchElementException    if not found
     * @throws IndexOutOfBoundsException if several ones found
     */
    Entry getByIdentifier(String identifier);

    /**
     * Returns all entries of the given type.
     *
     * @param type type of entries to look for
     * @return all entries of the given type
     */
    default Collection<Entry> getByType(Entry type) {
        return getByType(type.getIdentifier());
    }

    /**
     * Returns all entries whose type is equal to the given type.
     *
     * @param type type of entries to look for
     * @return all entries with that type
     */
    Collection<Entry> getByType(String type);

    /**
     * Returns the identifier of this entry.
     *
     * @return the identifier of this entry
     */
    @CheckForNull
    String getIdentifier();

    /**
     * Returns the IETF BCP 47 language tag, if this is a language-tagged
     * string.
     *
     * @return a BCP47 language tag representing the locale of the
     *         language-tagged string
     * @throws UnsupportedOperationException if this isn’t a language-tagged
     *                                       string
     */
    default String getLanguageTag() { return getLocale().toLanguageTag(); }

    /**
     * Returns the locale, if this is a language-tagged string.
     *
     * @return the locale
     * @throws UnsupportedOperationException if this isn’t a language-tagged
     *                                       string
     */
    default Locale getLocale() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns all outgoing relations from this node.
     *
     * @return all outgoing relations
     */
    Set<String> getRelations();

    /**
     * Returns all types of this entry.
     *
     * @return all types of this entry
     */
    Collection<String> getTypes();

    /**
     * If this is a literal, returns its value.
     *
     * @return the literal value
     * @throws UnsupportedOperationException if this isn’t a literal
     */
    String getValue();

    /**
     * Checks whether this node has the given type.
     *
     * @param type type to check for
     * @return whether this node has the type
     */
    default boolean hasType(Entry type) {
        return hasType(type.getIdentifier());
    }

    /**
     * Checks whether this node has the given type.
     *
     * @param type type to check for
     * @return whether this node has the type
     */
    default boolean hasType(String type) {
        return getTypes().contains(type);
    }

    /**
     * Returns whether this entry is anonymous.
     *
     * @return whether this entry is anonymous
     */
    default boolean isAnonymous() { return !isNamed(); }

    /**
     * Returns whether this entry does not contain any relations.
     *
     * @return whether the entry is empty
     */
    boolean isEmpty();

    /**
     * Returns whether this entry is a language-tagged string.
     *
     * @return whether this entry is a language-tagged string
     */
    boolean isLangString();

    /**
     * Returns whether this entry is a leaf.
     *
     * @return whether this entry is a leaf
     */
    boolean isLeaf();

    /**
     * Returns whether this entry is a literal.
     *
     * @return whether this entry is a literal
     */
    boolean isLiteral();

    /**
     * Returns whether this entry is named.
     *
     * @return whether this entry is named
     */
    default boolean isNamed() { return getIdentifier() != null; }

    /**
     * Returns whether this entry is a node.
     *
     * @return whether this entry is a node
     */
    default boolean isNode() { return !isLiteral(); }

    /**
     * Adds an entry by relation.
     *
     * @param relation Relation the object shall be added under
     * @param object   object to add
     * @return this, for method chaining
     */
    default Entry put(Entry relation, Entry object) {
        put(relation.getIdentifier(), object);
        return this;
    }

    /**
     * Adds a literal by relation.
     *
     * @param relation Relation the object shall be added under
     * @param object   object to add
     * @return this, for method chaining
     */
    default Entry put(Entry relation, String object) {
        put(relation.getIdentifier(), object);
        return this;
    }

    /**
     * Adds a node by relation.
     *
     * @param <T>      possible subclass of Node. When invoked on a
     *                 {@code NamedNode}, will return a named node instead.
     *
     * @param relation Relation the object shall be added under
     * @param object   object to add
     * @return this, for method chaining
     */
    Entry put(String relation, Entry object);

    /**
     * Adds a literal by relation.
     *
     * @param relation Relation the object shall be added under
     * @param object   object to add
     * @return this, for method chaining
     */
    Entry put(String relation, String object);

    /**
     * Adds all of the objects by the given relation.
     *
     * @param relation relation to add the objects on
     * @param objects  objects to add
     */
    default void putAll(Entry relation, Collection<? extends Entry> objects) {
        putAll(relation.getIdentifier(), objects);
    }

    /**
     * Adds all of the objects by the given relation.
     *
     * @param relation relation to add the objects on
     * @param objects  objects to add
     */
    void putAll(String relation, Collection<? extends Entry> objects);

    /**
     * Removes an object from all relations. If a relations becomes
     * destinationless by this it will be removed, too.
     *
     * @param object Object to remove
     * @return whether the collection was changed
     */
    boolean remove(Object object);

    /**
     * Removes all objects linked by the given relation.
     *
     * @param relation relation to remove
     * @return the previously linked objects
     */
    default Collection<Entry> removeAll(Entry relation) {
        return removeAll(relation.getIdentifier());
    }

    /**
     * Removes all objects linked by the given relation.
     *
     * @param relation relation to remove
     * @return the previously linked objects
     */
    Collection<Entry> removeAll(String relation);

    /**
     * Removes all relations of the given kind and replaces them by relations to
     * the set of objects provided.
     *
     * @param relation relation to replace
     * @param objects  new objects for this relation
     * @return the objects previously related
     */
    default Collection<Entry> replaceAll(Entry relation, Collection<Entry> objects) {
        return replaceAll(relation.getIdentifier(), objects);
    }

    /**
     * Removes all relations of the given kind and replaces them by a relations
     * to the objects provided.
     *
     * @param relation relation to replace
     * @param object   new object for this relation
     * @return the objects previously related
     */
    default Collection<Entry> replaceAll(Entry relation, Entry object) {
        return replaceAll(relation.getIdentifier(), Collections.singleton(object));
    }

    /**
     * Removes all relations of the given kind and replaces them by relations to
     * the set of objects provided.
     *
     * @param relation relation to replace
     * @param objects  new objects for this relation
     * @return the objects previously related
     */
    Collection<Entry> replaceAll(String relation, Collection<Entry> objects);

    /**
     * Removes all relations of the given kind and replaces them by a relations
     * to the objects provided.
     *
     * @param relation relation to replace
     * @param object   new object for this relation
     * @return the objects previously related
     */
    default Collection<Entry> replaceAll(String relation, Entry object) {
        return replaceAll(relation, Collections.singleton(object));
    }

    /**
     * Sets a literal as only enumerated child of this entry. Removes all other
     * enumerated relations, but will keep non-enumerated relations.
     *
     * <p>
     * This is a convenience method to <em>set text content</em> when building
     * XML-like data structure. All <em>tag content</em> will be removed, but
     * <em>attributes will be kept</em>.
     *
     * @param value literal value to set
     * @return this, for method chaining
     */
    Entry setValue(String value);
}
