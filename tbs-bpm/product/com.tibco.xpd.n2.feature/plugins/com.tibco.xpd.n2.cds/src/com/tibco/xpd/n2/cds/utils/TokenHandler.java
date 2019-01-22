package com.tibco.xpd.n2.cds.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public abstract class TokenHandler {

    protected static final HashMap<String, TokenHandler> tokenHandlers =
            new HashMap<String, TokenHandler>(2, 1);

    protected static final TokenHandler DEFAULT = new NextToken();

    static {
        tokenHandlers.put("*", new SkipNext()); //$NON-NLS-1$
        tokenHandlers.put("**", new Greedy()); //$NON-NLS-1$
    }

    public static <T> List<T> get(String pkg, TokenDataList<T> tree) {
        String[] tokens = pkg.split("\\."); //$NON-NLS-1$
        final int len = tokens.length;
        if (len == 0) {
            return Collections.emptyList();
        }
        String startToken = tokens[0];
        ArrayList<T> ret = new ArrayList<T>();
        getTokenHandler(startToken).process(tree, tokens, 0, len - 1, ret);
        return ret;
    }

    protected static TokenHandler getTokenHandler(String token) {
        TokenHandler h = tokenHandlers.get(token);
        return null == h ? DEFAULT : h;
    }

    public abstract <T> void process(TokenDataList<T> list, String[] tokens,
            int startIdx, final int lastIdx, List<T> dataList);

    static class SkipNext extends TokenHandler {

        public <T> void process(TokenDataList<T> list, String[] tokens,
                int startIdx, final int lastIdx, List<T> dataList) {
            if (startIdx == lastIdx) {
                throw new IllegalArgumentException(
                        "Wildcard '*' cannot be last in the search string!"); //$NON-NLS-1$
            }
            TokenHandler next = getTokenHandler(tokens[++startIdx]);
            for (TokenDataList<T> child : list.getChildren().values()) {
                /*
                 * Move to next child
                 */
                next.process(child, tokens, startIdx, lastIdx, dataList);
            }
        }
    }

    static class Greedy extends TokenHandler {

        public <T> void process(TokenDataList<T> list, String[] tokens,
                int startIdx, final int lastIdx, List<T> dataList) {
            if (startIdx < lastIdx) {
                throw new IllegalArgumentException(
                        "Wildcard '**' should be last in search string!"); //$NON-NLS-1$
            }
            list.getData(dataList, true);
        }
    }

    static class NextToken extends TokenHandler {
        public <T> void process(TokenDataList<T> list, String[] tokens,
                int startIdx, final int lastIdx, List<T> dataList) {
            list = list.getChild(tokens[startIdx]);
            if (startIdx == lastIdx) {
                list.getData(dataList, false);
            } else {
                String next = tokens[++startIdx];
                getTokenHandler(next).process(list,
                        tokens,
                        startIdx,
                        lastIdx,
                        dataList);
            }
        }
    }
}
