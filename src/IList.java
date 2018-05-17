
interface IList<T> extends Iterable<T> {

    boolean isCons();



    Cons<T> asCons();

}



// Represents an empty list of objects of a single type

class Empty<T> implements IList<T> {

    public Iterator<T> getIterator() {

        return new IListIterator<T>(this);

    }



    public boolean isCons() {

        return false;

    }



    public Cons<T> asCons() {

        return new Cons<T>(null, this);

    }

}



// Represents a non-empty list of objects

class Cons<T> implements IList<T> {

    T first;

    IList<T> rest;



    Cons(T first, IList<T> rest) {

        this.first = first;

        this.rest = rest;

    }



    public Iterator<T> getIterator() {

        return new IListIterator<T>(this);

    }



    public boolean isCons() {

        return true;

    }



    public Cons<T> asCons() {

        return (Cons<T>) this;

    }

}