The Big Data Easy
=================

All the big ones do it: Big Data, also known as Semantic Web, Resource
Description Framework (RDF), Resource Description Architecture (RDA), or Linked
Data / Linked Open Data (LOD). The remainder, however, manage their data in
spreadsheets, or uses rigid software solutions to manage it.
The former never have a homogenous structure (even if they make an effort), and
are quickly getting the famous “spreadsheet chaos”.
The latter are always behind reality, and there is always a lack of a way to
deal with a particular case, because their software has a too rigid structure
that does not dynamically adapt to reality.
It’s a fact that there is barely nothing that does _not_ exist.
Big Data takes this primary clue into account from the beginning, and that’s
what makes those who use it so much better.
Drawback: The handling of these frameworks felt so far unnecessarily
complicated.
One might get the impression that only seven people around the world have
understood how this works.
**Big data easy** has appeared to cure this shortcoming on the code level, and
in the form of instructive explanations.

The concepts
------------

So far you have learned to program with data that you know.
Now you have to learn to program with data that you do not know.

Is that possible at all?
It is a compromise.
Of course, your program must continue to make demands on the data.
Let’s call it valid input.
But at the same time it should try to be as unpretentious as possible.
And this is how it works:

So far, we had objects with certain fields.
Big Data, on the other hand, builds on a data structure that is a multi-map.
That is, everything we describe can have any properties, and they can have any
number of values.
Of course, if your program needs a specific piece of information, it must be in
the right place in the data.
Nobody wants you to do magic.
And, if you expect only one value as a result, then there is only one value
allowed to exist.
You cannot drive in two different directions at the same time.
Nevertheless, this model already allows us a number of possibilities that we
otherwise wouldn’t have: 

- First, if a _different_ program needed other information in our object, we
still had to adjust the file format, database model, and object in _this_
program so they both could still handle the same data.
In Big Data, another program can put arbitrarily different data into our
object, and our program will not be affected.

- Second, in many cases, one can consider from the start that there can be zero
or many values from one property.
For example, if you want to send Christmas e-mails, you can also send an e-mail
to two addresses in case a person uses two different e-mail addresses, and a
person who does not have one gets no e-mail.
Even though it did not significantly complicate the sending of Christmas mail,
this would not be possible with a database with just one field for exactly one
person’s email address.
Such situations end up in an operator adding a person several times to the
database, just to be able to send mail to two different addresses.
The chaos begins.

- A third point comes into play if you have heterogeneous data.
If you want to store a list of things you have received from friends, the
different things can have very different properties.
Try creating a database table for it.
The row with the CD would then have a field for “eye color”, and the teddy
bear would then have a field for “playback duration”.
In business, this situation is much more common than a programmer is aware of.

The second big difference in Big Data is that things are clarified, especially
the properties.
Simple example: If you get a database record that has a “title” field, what
information do you find in it?
In short, we never knew that.
You needed a documentation of the file format, or a human had to look at sample
data to find out, for example, whether “title” meant a name component (Sir,
MD), a track on a CD, or a prize won.

In Big Data, every thing and property is declared in a namespace (this is a WWW
address, that can be fictitious).
So I can say:
My title here has this or that meaning.
And if you declare a title field, that can be something completely different.
But if you use my title field declaration, you have to use it according to my
definition.
This leads to a good interchangeability of data, if one refrains as far as
possible to define own properties, but, where possible, uses a common
collection of properties, a so-called vocabulary.

[There are many public ones](https://lov.linkeddata.es/dataset/lov/) for many
purposes.

The same is true for the data objects themselves.
If you want to store a piece of information about Paris (be it the capital of
France, or the Prince of Troy) you can just use Paris in a public namespace
(e.g. Wikipedia), and everyone knows which one of the both Paris you mean.

The technology
--------------

The multi-map containing our data is processed as a so-called graph.
Imagine a board game with boxes on which you can place your pieces.
Wherever you can move from one field to another, a line is drawn.
That’s essentially what a graph looks like.
The mathematicians who came up with it called the boxes “knots” and the lines
“edges”.
This has nothing to do with the knots you can have in your headphone wiring,
nor with the edge of a table, it is just called like that.
The nodes are the things we store information about.
(In a database setting, this would be the rows of the tables.)
The edges describe the type of relationship.
(Again in the database setting, that would be the columns.)
If the information is a simple value (a single string, a number, or alike),
it is stored in a so-called leaf.
Leaves can have data types, and as a special feature, strings can be
language-tagged.
A Mozart piece may have several titles that differ only in the language in
which they were translated.
Internationalization is built in here right away.
If the information is more complex, it is again stored as a node.
A node can be identified by a URI.
That makes sense for all nodes that map some thing in the world.
But there are also anonymous nodes, which are used when a value should consist
only of several fields.
For example, the president’s eye color might be a node with the three
properties red, green, and blue, each of which then has a numeric value to
express the color.
The president should be identifiable, but this node does not have to be.
However, you can give it an identifier if you want to be able to refer to it
from elsewhere as well.

In Big Data, information is always managed as a triad:
what do I say something about, what relationship do I describe, and what is
the value of that relationship.
(In database terms: ‹row ID, column, value›. In linguistics: ‹subject,
predicate, object›.)
These pieces of information are called triple and that is what we read, store,
and edit.
This is rather complicated on large-scale Big Data databases, where you have to
learn an own query language to do so.
The Big Data Easy simplifies this a lot by simply turning the nodes into Java
objects whose properties can be manipulated using the common collections
functions (`put()`, `get()`, `removeAll()`, …).

To describe if something is a city, a human or a color, there is a special
relation `rdf:type`.
The values of this relation must also have a certain property, they must be a
`rdfs:subClassOf rdf:Class`.
Java programmer beware:
The term *class* is used here differently than in Java, think of `interface`s
instead.
Each node can have multiple types (can implement multiple interfaces).

And what about Booleans? They exist. For example: Mozart is dead.
Of course, one could describe this as: ‹Mozart, died-on, "5 December 1791"›.
But if we just want to express the Boolean property?
This is resolved with group membership.
You define an object group-of-deceased and declare ‹group-of-deceased,
has-member, Mozart›.

---
<small>Contributors: as per editing history stored in Git  
License: Creative Commons Attribution-ShareAlike 4.0 International (CC BY-SA 4.0)</small>
