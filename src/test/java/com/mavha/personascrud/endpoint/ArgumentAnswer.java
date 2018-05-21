package com.mavha.personascrud.endpoint;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class ArgumentAnswer<T> implements Answer<T> {

	private final int index;

	public ArgumentAnswer(int index) {
		this.index = index;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T answer(InvocationOnMock invocation) throws Throwable {
		return (T) invocation.getArguments()[index];
	}

}
