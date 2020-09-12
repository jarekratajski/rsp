package rsp.dom;

import rsp.XmlNs;

import java.util.ArrayList;
import java.util.List;

public class Tag implements Node {

    public final Path path;
    public final XmlNs xmlns;
    public final String name;

    public final List<Attribute> attributes = new ArrayList<>();
    public final List<Style> styles = new ArrayList<>();
    public final List<Node> children = new ArrayList<>();

    public Tag(Path path, XmlNs xmlns, String name) {
        this.path = path;
        this.xmlns = xmlns;
        this.name = name;
    }

    public void addChild(Node node) {
        children.add(node);
    }

    public void addAttribute(String name, String value) {
        attributes.add(new Attribute(name, value));
    }

    public void addStyle(String name, String value) {
        styles.add(new Style(name, value));
    }

    @Override
    public Path path() {
        return path;
    }

    @Override
    public List<Node> children() {
        return children;
    }

}
