package org.github.txq.spider.reflection.util;

import lombok.Getter;

import java.util.Iterator;

/**
 * @author tangxinqi
 * @date 2019/12/3 19:22
 */
public class PropertyTokenizer implements Iterator<PropertyTokenizer> {

    @Getter
    private String name;
    @Getter
    private final String indexName;
    @Getter
    private String index;
    @Getter
    private final String children;

    public PropertyTokenizer(String fullName) {
        int delim = fullName.indexOf('.');
        if (delim > -1) {
            name = fullName.substring(0, delim);
            children = fullName.substring(delim + 1);
        } else {
            name = fullName;
            children = null;
        }
        indexName = name;
        delim = name.indexOf('[');
        if (delim > -1) {
            index = name.substring(delim + 1, name.length() - 1);
            name = name.substring(0, delim);
        }
    }

    @Override
    public boolean hasNext() {
        return children != null;
    }

    @Override
    public PropertyTokenizer next() {
        return new PropertyTokenizer(children);
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Remove is not supported, as it has no meaning in the context of properties.");
    }
}

