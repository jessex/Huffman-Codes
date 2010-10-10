package huffmancoding;

import java.util.ArrayList;

public class PriorityQueue {

    public ArrayList<Element> elements;

    /**
     * Constructor for an "empty" queue by declaring an empty ArrayList and
     * adding a fake head element with negative frequency so as to always remain
     * at the front of the queue.
     */
    public PriorityQueue() {
        this.elements = new ArrayList<Element>();
        elements.add(new Element(-1, null));
    }

    //////////////////////////////ACCESSORS/////////////////////////////////////

    /**
     * Returns if the queue is empty (minus the head).
     * @return ifEmpty
     */
    public boolean isEmpty() {
        return elements.size() == 1;
    }

    /**
     * Returns the true size of the queue (minus the head).
     * @return size
     */
    public int getSize() {
        return elements.size() - 1;
    }

    /**
     * Returns the minimal frequency in the queue.
     * @return minimal frequency
     */
    public int getMinimumFrequency() {
        return ((Element) elements.get(1)).getFrequency();
    }

    /**
     * Returns the element with the minimal frequency in the queue.
     * @return minimal element
     */
    public Object getMinimumElement() {
        return elements.get(1);
    }

    /////////////////////////////////HELPERS////////////////////////////////////

    /**
     * Inserts the object into the priority queue while maintaining priority
     * order. In this insert, frequency is the priority value, subtrees are the
     * objects (elements).
     * @param frequency - int
     * @param element - Object
     */
    public void insert(int frequency, Object element) {
        int size = elements.size();
        int halfway = size / 2;
        elements.add(null); //Get a placeholder at the end of the queue
        Element parent = (Element) elements.get(halfway); //Parent at floor(i/2)
        //Keep drilling down the queue until our spot is found
        while (parent.getFrequency() > frequency) {
            elements.set(size, parent); //Move parent to new spot
            size = halfway;
            halfway = halfway / 2;
            parent = (Element) elements.get(halfway);
        }
        //Have our spot, overwrite with the inserted object
        elements.set(size, new Element(frequency, element));
    }

    /**
     * Removes the element with the minimal frequency in queue and returns it.
     * @return minimal element
     */
    public Object removeMinimum() {
        Object minimum = ((Element) elements.get(1)).getElement();
        Element last = (Element) elements.remove(elements.size() - 1);
        int freq = last.getFrequency();

        int index = 1;
        int marker = 2;
        while (marker < elements.size()) {
            //If the following element has a lower frequency, try that one
            if (marker + 1 < elements.size() &&
                    ((Element) elements.get(marker)).getFrequency() >
                    ((Element) elements.get(marker + 1)).getFrequency()) {
                    marker++;
            }

            //If element has greater frequency than last, ready to realign queue
            if (((Element) elements.get(marker)).getFrequency() > freq) break;
            //Otherwise, go halfway up the queue and look again
            elements.set(index, elements.get(marker));
            index = marker;
            marker *= 2;
        }

        if (index < elements.size()) elements.set(index, last);
        return minimum; //Finally return the minimum now that queue is aligned
    }


    /**
     * A generic Element structure to populate the queue. Has data fields for
     * the held object to be considered for the queue and a frequency value.
     */
    public class Element {

        private int freq;
        private Object element;

        public Element(int freq, Object element) {
            this.freq = freq;
            this.element = element;
        }

        public int getFrequency() {
            return this.freq;
        }
        public Object getElement() {
            return this.element;
        }
    }
}
