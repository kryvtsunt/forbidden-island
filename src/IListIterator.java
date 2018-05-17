
interface Iterator<T> {

    boolean hasNext();



    T next();

}



class IListIterator<T> implements Iterator<T> {



    IList<T> list;



    IListIterator(IList<T> list) {

        this.list = list;

    }



    public boolean hasNext() {

        return this.list.isCons();

    }



    public T next() {

        Cons<T> cons = this.list.asCons();

        T result = cons.first;

        this.list = cons.rest;

        return result;

    }

}



interface Iterable<T> {

    Iterator<T> getIterator();

}