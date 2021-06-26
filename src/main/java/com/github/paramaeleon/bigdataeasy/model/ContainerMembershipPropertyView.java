/*
 * Licensed under the terms of the Apache License 2.0. For terms and conditions,
 * see http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.paramaeleon.bigdataeasy.model;

import java.math.*;
import java.util.*;
import java.util.stream.*;

import javax.annotation.*;

import org.apache.commons.lang3.tuple.*;

/**
 * A filtered view of the entries of an entry that are referenced as ordered
 * list members.
 *
 * @see "http://www.w3.org/TR/rdf-schema/#ch_containermembershipproperty"
 */
public interface ContainerMembershipPropertyView extends Entry, List<Collection<Entry>> {
    /**
     * The first allowed index for the ContainerMembershipProperty. By
     * specification, list indices in the RDF are 1-based.
     */
    short FIRST_INDEX = 1;
    BigInteger FIRST_INDEX_BIG_INTEGER = BigInteger.valueOf(FIRST_INDEX);

    /**
     * The prefix that, followed by a integer <i>n</i> > 0, is used to indicate
     * an element’s position.
     */
    String INDEX_URI_PREFIX = "http://www.w3.org/1999/02/22-rdf-syntax-ns#_";
    int INDEX_URI_PREFIX_LENGTH = INDEX_URI_PREFIX.length();

    static BigInteger indexOf(String uri) {
        if (!uri.startsWith(INDEX_URI_PREFIX)) throw new IllegalArgumentException();
        BigInteger bigIntValue = new BigInteger(uri.substring(INDEX_URI_PREFIX_LENGTH));
        if (bigIntValue.compareTo(BigInteger.ONE) < 0) throw new IllegalArgumentException();
        return bigIntValue;
    }

    /**
     * Returns an URI that encodes the given index.
     *
     * @param index index to encode
     * @return an URI for that index
     */
    static String toUri(BigInteger index) {
        assert index.compareTo(FIRST_INDEX_BIG_INTEGER) >= 0 : "index out of range";
        return INDEX_URI_PREFIX.concat(index.toString());
    }

    /**
     * Returns an URI that encodes the given index.
     *
     * @param index index to encode
     * @return an URI for that index
     */
    static String toUri(long index) {
        assert index >= FIRST_INDEX : "index out of range";
        return INDEX_URI_PREFIX.concat(Long.toString(index));
    }

    /**
     * Adds multiple entries to a new list position whose index is 1 higher than
     * the highest index in use.
     *
     * @return always {@code true}
     */
    @Override
    boolean add(Collection<Entry> e);

    /**
     * Adds one entry to a new list position whose index is 1 higher than the
     * highest index in use.
     *
     * @return always {@code true}
     */
    default boolean add(Entry e) {
        return add(Collections.singleton(e));
    }

    /**
     * Adds several entries to the specified list position, and shifts all list
     * entries from the specified position upwards by one, until a free space
     * has been occupied. Since this list may be holey, this does not
     * necessarily mean that this must increase the highest index in the list.
     *
     * @param index    where the elements are to be inserted
     * @param elements elements to be inserted
     */
    @Override
    void add(int index, Collection<Entry> elements);

    /**
     * Adds an entry to the specified list position, and shifts all list entries
     * from the specified position upwards by one, until a free space has been
     * occupied. Since this list may be holey, this does not necessarily mean
     * that this must increase the highest index in the list.
     *
     * @param index   where the elements are to be inserted
     * @param element element to be inserted
     */
    default void add(int index, Entry element) {
        add(index, Collections.singleton(element));
    }

    @Override
    default boolean addAll(Collection<? extends Collection<Entry>> collections) {
        for (Collection<Entry> collection : collections) {
            add(collection);
        }
        return true;
    }

    @Override
    default boolean addAll(int index, Collection<? extends Collection<Entry>> collection) {
        for (Collection<Entry> collections : collection) {
            add(index, collections);
        }
        return true;
    }

    /**
     * Adds several entries to the specified list position, without moving the
     * other list items.
     *
     * @param index    where the elements are to be inserted
     * @param elements elements to be inserted
     */
    void addTo(int index, Collection<Entry> elements);

    /**
     * Adds an entry to the specified list position, without moving the other
     * list items.
     *
     * @param index   where the element is to be inserted
     * @param element element to be inserted
     */
    default void addTo(int index, Entry element) {
        addTo(index, Collections.singleton(element));
    }

    @Override
    default void clear() {
        BigInteger[] range = range();
        if (range != null) {
            for (BigInteger i = range[0]; i.compareTo(range[1]) <= 0; i = i.add(BigInteger.ONE)) {
                removeAll(toUri(i));
            }
        }
    }

    default Stream<Pair<String, Collection<Entry>>> containerMembershipPropertyEntriesStream() {
        return entriesStream().filter(λ -> λ.getKey().startsWith(INDEX_URI_PREFIX));
    }

    default Stream<Pair<String, Entry>> containerMembershipPropertyEntryStream() {
        return entryStream().filter(λ -> λ.getKey().startsWith(INDEX_URI_PREFIX));
    }

    @Override
    default boolean contains(Object o) {
        return containerMembershipPropertyEntryStream().anyMatch(λ -> λ.getValue().equals(o));
    }

    @Override
    default boolean containsAll(Collection<?> c) {
        return c.parallelStream().allMatch(this::contains);
    }

    /**
     * Returns all numbered entries in this entry. The order of the elements
     * corresponds to the order of the indices, there are several elements per
     * index, no particular order is defined for these elements.
     *
     * @return all numbered entries in this entry
     */
    @Override
    default Collection<Entry> elements() {
        return stream().flatMap(λ -> λ.parallelStream()).collect(Collectors.toList());
    }

    /**
     * Finds the first index in use. Returns {@code null} if
     * {@code this.}{@link #isEmpty()}.
     *
     * @return the first index in use
     */
    @CheckForNull
    default BigInteger first() {
        BigInteger[] range = range();
        return range == null ? null : range[0];
    }

    @Override
    default Collection<Entry> get(int index) {
        return get(toUri(index));
    }

    @Override
    default int indexOf(Object o) {
        Optional<BigInteger> found = containerMembershipPropertyEntryStream().filter(λ -> o.equals(λ.getValue()))
                .map(λ -> indexOf(λ.getKey())).min(BigInteger::compareTo);
        if (found.isPresent()) return found.get().intValueExact();
        else
            throw new NoSuchElementException();
    }

    /**
     * If this entry has no numbered values.
     */
    @Override
    default boolean isEmpty() { return getRelations().parallelStream().noneMatch(λ -> λ.startsWith(INDEX_URI_PREFIX)); }

    @Override
    default Iterator<Collection<Entry>> iterator() {
        return containerMembershipPropertyEntriesStream().map(Pair::getValue).iterator();
    }

    /**
     * Finds the first index in use. Returns {@code null} if
     * {@code this.}{@link #isEmpty()}.
     *
     * @return the first index in use
     */
    @CheckForNull
    default BigInteger last() {
        BigInteger[] range = range();
        return range == null ? null : range[1];
    }

    @Override
    default int lastIndexOf(Object o) {
        Optional<BigInteger> found = containerMembershipPropertyEntryStream()
            .filter(λ -> o.equals(λ.getValue()))
            .map(λ -> indexOf(λ.getKey()))
        .max(BigInteger::compareTo);

        if (found.isPresent())
            return found.get().intValueExact();
            else throw new NoSuchElementException();
    }

    @Override
    default ListIterator<Collection<Entry>> listIterator() {
        throw new UnsupportedOperationException("not yet implemented");
    }

    @Override
    default ListIterator<Collection<Entry>> listIterator(int index) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    /**
     * Returns the first and last numeric index in use. Returns {@code null} if
     * {@code this.}{@link #isEmpty()}.
     *
     * @return a {@code BigInteger[2]] with the first and last numeric index
     */
    @CheckForNull
    default BigInteger[] range() {
        BigInteger[] result = null;
        for (String relation : getRelations()) {
            if (relation.startsWith(INDEX_URI_PREFIX)) {
                BigInteger bigIntValue = new BigInteger(relation.substring(INDEX_URI_PREFIX_LENGTH));
                if (bigIntValue.compareTo(FIRST_INDEX_BIG_INTEGER) < 0) continue;
                if (result == null) {
                    result = new BigInteger[] { bigIntValue, bigIntValue };
                } else {
                    result[0] = result[0].min(bigIntValue);
                    result[1] = result[1].min(bigIntValue);
                }
            }
        }
        return result;
    }

    @Override
    default Collection<Entry> remove(int index) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    @Override
    default boolean remove(Object o) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    @Override
    default boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    @Override
    default boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    @Override
    default Collection<Entry> set(int index, Collection<Entry> element) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    /**
     * Returns the number of indices in use. That is the number of elements an
     * iterator through this list would return. Due to the specialty of this
     * list, this does not necessarily have to be 1 more than the last index. To
     * determine the number of individual elements, use
     * {@link #elements()}{@code .size()}.
     *
     * @return the number of elements an iterator through this list would return
     */
    @Override
    int size();

    @Override
    default List<Collection<Entry>> subList(int fromIndex, int toIndex) {
        List<Collection<Entry>> result = new LinkedList<>();
        for (int i = fromIndex; i < toIndex; i++) {
            Collection<Entry> elementsAtIndex = get(i);
            if (!elementsAtIndex.isEmpty()) {
                result.add(elementsAtIndex);
            }
        }
        return result;
    }

    @Override
    default Object[] toArray() {
        ArrayList<Collection<Entry>> arrayList = new ArrayList<>(size());
        Iterator<Collection<Entry>> i = iterator();
        while (i.hasNext()) {
            arrayList.add(i.next());
        }
        return arrayList.toArray();
    }

    @Override
    default <T> T[] toArray(T[] a) {
        ArrayList<Collection<Entry>> arrayList = new ArrayList<>(size());
        Iterator<Collection<Entry>> i = iterator();
        while (i.hasNext()) {
            arrayList.add(i.next());
        }
        return arrayList.toArray(a);
    }

}
