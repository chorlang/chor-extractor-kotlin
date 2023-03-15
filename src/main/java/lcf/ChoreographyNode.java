package lcf;

/*
 * Interface for the different action types in choreographies.
 */
public interface ChoreographyNode {

    void accept(CNVisitor v);

}
