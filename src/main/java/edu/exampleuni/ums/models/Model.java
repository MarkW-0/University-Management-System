package edu.exampleuni.ums.models;

abstract public class Model {
	public Model() {}

	public abstract boolean isEqual(Model updated);
}