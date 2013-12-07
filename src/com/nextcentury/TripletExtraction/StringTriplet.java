package com.nextcentury.TripletExtraction;

class StringTriplet {
	protected String entity1;
	protected String relation;
	protected String entity2;

	public StringTriplet(String subj, String pred, String obj) {
		entity1 = subj;
		relation = pred;
		entity2 = obj;
	}
	
	public StringTriplet(String entity) {
		entity1 = entity;
	}
	
	public void setEntity1(String entity) {
		entity1 = entity;
	}
	
	public void setRelationship(String relationship) {
		relation = relationship;
	}
	
	public void setEntity2(String entity) {
		entity2 = entity;
	}

	public String getEntity1() {
		return entity1;
	}

	public String getEntity2() {
		return entity2;
	}

	public String getRelation() {
		return relation;
	}

	public String toString() {
		return "{" + entity1.toString() + " " + relation.toString() + " " + entity2.toString() + "}";
	}
}