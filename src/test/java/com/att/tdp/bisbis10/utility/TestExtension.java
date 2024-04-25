package com.att.tdp.bisbis10.utility;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

public class TestExtension implements TestInstancePostProcessor, BeforeEachCallback, ParameterResolver {

	private ApplicationContext context;

	@Override
	public void postProcessTestInstance(Object testInstance, ExtensionContext context) throws Exception {
		this.context = SpringExtension.getApplicationContext(context);
	}

	public TestHelper getTestHelper() {
		return context.getBean(TestHelper.class);
	}

	@Override
	public void beforeEach(ExtensionContext context) throws Exception {
		getTestHelper().reset();
	}

	@Override
	public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
			throws ParameterResolutionException {
		return parameterContext.getParameter().getType() == TestHelper.class;
	}

	@Override
	public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
			throws ParameterResolutionException {
		return getTestHelper();
	}

}
