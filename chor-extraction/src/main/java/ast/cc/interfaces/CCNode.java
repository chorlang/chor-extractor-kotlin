package ast.cc.interfaces;

import ast.cc.CCVisitor;

/**
 * Created by fmontesi on 03/04/17.
 */
public interface CCNode {
    <T> T accept( CCVisitor<T> visitor );
}
