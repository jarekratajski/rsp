package rsp;

import rsp.dsl.DocumentPartDefinition;
import rsp.state.UseState;

/**
 * A UI component, a building block of a UI.
 * @param <S> the type of the component's state, should be an immutable class
 */
@FunctionalInterface
public interface Component<S> {
    /**
     * Constructs a UI tree, which may contain HTML tags and/or descendant components.
     * In this method the component's state is reflected to its UI presentation and events handlers registered.
     * @param us a read and write state accessor object
     * @return the result component's definition
     */
    DocumentPartDefinition render(UseState<S> us);
}
