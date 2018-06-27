package lcf;

/*
 * Interface for the different action types in choreographies.
 */
public interface ChoreographyNode {

    public void accept(CNVisitor v);

}
