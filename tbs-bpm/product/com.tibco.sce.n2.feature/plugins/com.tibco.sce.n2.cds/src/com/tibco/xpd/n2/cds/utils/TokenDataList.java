package com.tibco.xpd.n2.cds.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TokenDataList<E> {
	protected final ArrayList<E> dataList = new ArrayList<E>();

	protected final String token;

	protected final HashMap<String, TokenDataList<E>> children = new HashMap<String, TokenDataList<E>>();

	protected TokenDataList<E> parent = null;

	public TokenDataList(String token) {
		this.token = token;
	}

	public TokenDataList<E> getChild(String token) {
		TokenDataList<E> child = children.get(token);
		if (null == child) {
			child = new TokenDataList<E>(token);
			child.setParent(this);
			children.put(token, child);
		}
		return child;
	}

	public void add(E data) {
		dataList.add(data);
	}

	public void setParent(TokenDataList<E> parent) {
		this.parent = parent;
	}

	HashMap<String, TokenDataList<E>> getChildren() {
		return children;
	}

	public String getToken() {
		return token;
	}

	public TokenDataList<E> find(String token, boolean greedy) {
		for (TokenDataList<E> child : children.values()) {
			TokenDataList<E> childchild = child.getChild(token);
			if (childchild.getToken().equals(token)) {
				return childchild;
			} else if (greedy) {
				TokenDataList<E> tok = child.find(token, greedy);
				if (null != tok) {
					return tok;
				}
			}
		}
		return null;
	}

	public List<E> getData(boolean includeChildren) {
		return getData(new ArrayList<E>(), includeChildren);
	}

	public List<E> getData(List<E> list, boolean includeChildren) {
		if (null == list) {
			list = new ArrayList<E>();
		}
		list.addAll(dataList);
		if (includeChildren) {
			for (TokenDataList<E> child : children.values()) {
				child.getData(list, includeChildren);
			}
		}
		return list;
	}

	public String toString() {
		return token;
	}
}
