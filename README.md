The Big Data Easy
=================

All the big ones do it: Big Data, also known as Semantic Web, Resource
Description Framework (RDF), Resource Description Architecture (RDA), or Linked
Data / Linked Open Data (LOD). The remainder, however, manage their data in
spreadsheets or uses rigid software solutions to manage it. The former never
have a homogenous structure (even if you make an effort), and are quickly
becoming the famous spreadsheet chaos. The latter are always behind reality,
and there is always a lack of a way to record a particular case, because they
have a too rigid structure that does not dynamically adapt to reality. The fact
is, there is nothing that does _not_ exist. Big Data takes that into account
from the beginning, and that’s what makes those who use it so much better.
Drawback: The handling of these frameworks felt so far unnecessarily
complicated. One might get the impression that only seven people around the
world have understood how this works.
**Big data easy** has appeared to cure this shortcoming on the code level, and
in the form of instructive explanations.

The concepts
------------

So far you have learned to program with data that you know. Now you have to
learn to program with data that you do not know.

Is that possible at all? It is a compromise. Of course, your program must
continue to make demands on the data. Let’s call it valid input. But at the
same time it should try to be as unpretentious as possible. And this is how it
works:

So far we had objects with certain fields. Big Data, on the other hand, builds
on a data structure that is a multi-map. That is, everything we describe can
have any properties, and they can have any number of values. Of course, if your
program needs a specific piece of information, it must be in the right place in
the data. Nobody wants you to do magic. And if you expect only one value as a
result, then there is only one value allowed to exist. You cannot drive in two
different directions at the same time. Nevertheless, this model already allows
us a number of possibilities that we otherwise wouldn’t have: 

* First, if a _different_ program needed other information in our object, we
still had to adjust the file format, database model, and object in _this_
program so they both could still handle the same data. In Big Data, another
program can put arbitrarily different data into our object, and our program
will not be affected.

* Second, in many cases, one can consider from the start that there can be zero
or many values from one property. For example, if you want to send Christmas
e-mails, you can also send an e-mail to two addresses in case a person uses two
different e-mail addresses, and a person who does not have one gets no e-mail.
Even though it did not significantly complicate the sending of Christmas mail,
this would not be possible with a database with just one field for exactly one
person’s email address.

* A third point is added if you have heterogeneous data. If you have a list of
things you have received from friends, the different things can have very
different properties. Try creating a database table for it. The line with the
CD would then have a field for eye color, and the teddy bear would then have a
field for playing time. In business, this situation is much more common than a
programmer is aware of.

The second big difference in Big Data is that things are clarified, especially
the properties. Simple example: If you get a record that has a “title” field,
what information do you find in it? In short, we never knew that. One needed a
documentation of the file format, or a human had to look at sample data to find
out, for example, whether it was a name component (Sir, MD), a track on a CD,
or a prize won.

In Big Data, every thing and property is declared in a namespace (this is a WWW
address that can be fictitious). So I can say: My title here has this or that
meaning. And if you declare a title field, that can be something completely
different. But if you use my title field, you have to use it according to my
definition. This leads to a good interchangeability of data, if one refrains as
far as possible to define own properties, but, where possible, uses a common
collection of properties, a so-called vocabulary. There are many public ones
for many purposes.

The same is true for the data objects themselves. If you want to store a piece
of information about Paris (be it the capital of France, or the Prince of Troy)
you can just use Paris in a public namespace (e.g. Wikipedia), and everyone
knows which one of the both Paris you mean.

The technology
--------------

The multi-map containing our data is processed as a so-called graph. This means
that the values of each property can be objects again. That can be arbitrarily
complex. But they can also be strings. The objects are called nodes and the
strings are called leaves.

---
<small>Contributors: Matthias Ronge ‹matthias.ronge@freenet.de›  
License: Creative Commons Attribution-ShareAlike 4.0 International (CC BY-SA 4.0)</small>
