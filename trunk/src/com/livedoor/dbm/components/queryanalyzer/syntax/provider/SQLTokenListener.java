package com.livedoor.dbm.components.queryanalyzer.syntax.provider;


public interface SQLTokenListener
{
	void tableOrViewFound(String name);
}
