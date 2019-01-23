package com.tibco.xpd.process.js.parser.transform;

import java.util.ArrayList;

public class CodeBlock {

	private boolean commentBlock = false;

	/**
	 * List of variable which are defined in this block
	 */
	private ArrayList<String> blockVar = new ArrayList<String>();

	private String blockName = null;

	private CodeBlock parentBlock = null;

	private ArrayList<String> codeToInsert = new ArrayList<String>();

	private ArrayList<String> blockEndCode = new ArrayList<String>();

	private int childBlockCount = 0;

	/**
	 * List of variables which should be commented out.
	 */
	private ArrayList<String> ignoreIdentifiers = new ArrayList<String>();

	public CodeBlock(String blockName) {
		this.blockName = blockName;
	}

	public void commentBlock(boolean bool) {
		this.commentBlock = bool;
	}

	public boolean isBlockToBeCommented() {
		boolean toReturn = false;
		if (commentBlock) {
			toReturn = true;
		} else {
			CodeBlock parent = this.getParentBlock();
			while (parent != null) {
				boolean bool = parent.isBlockToBeCommented();
				if (bool) {
					toReturn = true;
					break;
				}
				parent = parent.getParentBlock();
			}
		}
		return toReturn;
	}

	public void addBlockVariable(String varName) {
		this.blockVar.add(varName);
	}

	public boolean isBlockVariable(String varName) {
		return this.blockVar.contains(varName);
	}

	public void clearBlockVariable() {
		this.blockVar = new ArrayList<String>();
	}

	public boolean isIdentifierToIgnore(String varName) {
		ArrayList<String> tempIdentifiersList = new ArrayList<String>();
		tempIdentifiersList.addAll(this.ignoreIdentifiers);
		CodeBlock parent = this.getParentBlock();
		while (parent != null) {
			ArrayList<String> tempList = parent.getIgnoreIdentifiers();
			tempIdentifiersList.addAll(tempList);
			parent = parent.getParentBlock();
		}
		return tempIdentifiersList.contains(varName);
	}

	public void addIdentifierToIgnore(String varName) {
		this.ignoreIdentifiers.add(varName);
	}

	public void clearIdentifierToIgnore() {
		this.ignoreIdentifiers = new ArrayList<String>();
	}

	public ArrayList<String> getIgnoreIdentifiers() {
		return this.ignoreIdentifiers;
	}

	public String getBlockName() {
		return this.blockName;
	}

	public void setBlockName(String blockName) {
		this.blockName = blockName;
	}

	public void setParentBlock(CodeBlock block) {
		this.parentBlock = block;
	}

	public CodeBlock getParentBlock() {
		return this.parentBlock;
	}

	public void appendCode(String statement) {
		codeToInsert.add(statement);
	}

	public ArrayList<String> getAppendCode() {
		/*
		 * StringBuffer toReturn = new StringBuffer(); for (Iterator iter =
		 * codeToInsert.iterator(); iter.hasNext();) { String element = (String)
		 * iter.next(); toReturn.append(element); toReturn.append('\n'); } if
		 * (toReturn.length() > 0) { return toReturn.toString(); } else { return
		 * null; }
		 */
		if (codeToInsert.size() > 0) {
			return codeToInsert;
		} else {
			return null;
		}

	}

	public void clearAppendCode() {
		codeToInsert = new ArrayList<String>();
	}

	public void appendBlockEndCode(String statement) {
		blockEndCode.add(statement);
	}

	public ArrayList<String> getBlockEndCode() {
		/*
		 * StringBuffer toReturn = new StringBuffer(); for (Iterator iter =
		 * blockEndCode.iterator(); iter.hasNext();) { String element = (String)
		 * iter.next(); toReturn.append(element); toReturn.append('\n'); } if
		 * (toReturn.length() > 0) { return toReturn.toString(); } else { return
		 * null; }
		 */
		if (blockEndCode.size() > 0) {
			return blockEndCode;
		} else {
			return null;
		}
	}

	public void clearBlockEndCode() {
		blockEndCode = new ArrayList<String>();
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer
				.append("BlockName::" + this.getBlockName() + " ParentBlock::" + this.getParentBlock().getBlockName()); //$NON-NLS-1$ //$NON-NLS-2$
		return buffer.toString();
	}

	/**
	 * increments the count of childBlock
	 */
	public void incrementChildBlockCount() {
		this.childBlockCount++;
	}

	/**
	 * decrements the count of childBlock
	 */
	public void decrementChildBlockCount() {
		this.childBlockCount--;
	}

	/**
	 * @return the childBlockCount
	 */
	public int getChildBlockCount() {
		return childBlockCount;
	}

}
