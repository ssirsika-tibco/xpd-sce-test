package com.tibco.xpd.n2.cds.utils;

import java.util.List;

public class TokenTreeMap<E> {

    protected final TokenDataList<E> root = new TokenDataList<E>("root"); //$NON-NLS-1$

    public TokenDataList<E> put(String pkg, E data) {
        TokenDataList<E> tree = createPackage(pkg);
        tree.add(data);
        return tree;
    }

    protected TokenDataList<E> createPackage(String pkg) {
        String[] tokens = pkg.split("\\."); //$NON-NLS-1$
        TokenDataList<E> tree = root;
        for (String token : tokens) {
            tree = tree.getChild(token);
        }
        return tree;
    }

    public List<E> get(String pkg) {
        return TokenHandler.get(pkg, root);
    }

}
