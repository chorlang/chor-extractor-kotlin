package epp;

import org.antlr.v4.runtime.ParserRuleContext;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ChoreographyContextComparator implements Comparator {

    private List<ParserRuleContext> list;
    private int capacity;

    public List<ParserRuleContext> getList() {
        return list;
    }

    public boolean readyToCompare() {
        return (capacity==2);
    }

    public ChoreographyContextComparator() {
        this.list = new ArrayList<>(2);
        this.capacity = 0;
    }

    @Override
    public int compare(Object o1, Object o2) {
        if (o1.equals(o2))
            return 0;
        else
            return 1;
    }

    public void addToList(Object obj1) {
        //list.add(obj1);
        capacity++;
    }

    public ParserRuleContext getFirst(){
        return list.get(0);
    }

    public ParserRuleContext getSecond(){
        return list.get(1);
    }
}