package com.articlemanagement.model;

public class Tags {

private String tag;
private Integer occurence;




public String getTag() {
	return tag;
}
public void setTag(String tag) {
	this.tag = tag;
}
public Integer getOccurence() {
	return occurence;
}
public void setOccurence(Integer occurence) {
	this.occurence = occurence;
}
public Tags(String tag, Integer occurence) {
	super();
	this.tag = tag;
	this.occurence = occurence;
}

/*
 * @Override public int hashCode() { final int prime = 31; int result = 1;
 * result = prime * result + ((occurence == null) ? 0 : occurence.hashCode());
 * result = prime * result + ((tag == null) ? 0 : tag.hashCode()); return
 * result; }
 * 
 * @Override public boolean equals(Object obj) { if (this == obj) return true;
 * if (obj == null) return false; if (getClass() != obj.getClass()) return
 * false; Tags other = (Tags) obj; if (occurence == null) { if (other.occurence
 * != null) return false; } else if (!occurence.equals(other.occurence)) return
 * false; if (tag == null) { if (other.tag != null) return false; } else if
 * (!tag.equals(other.tag)) return false; return true; }
 */
public Tags() {
	super();
}






}
