package com.yourcompany.utilities;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {
	private static Logger logger = LogManager.getLogger();
	private static final int RETRY_LIMIT = 5;
	private AtomicInteger count = new AtomicInteger(RETRY_LIMIT);

	@Override
	public boolean retry(ITestResult iTestResult) {
		boolean isRetry = false;

		if (!iTestResult.isSuccess()) {
			if (count.getAndDecrement() >= 0) {
				logger.warn(
						"Retrying test, methodName=" + iTestResult.getMethod().getMethodName() + ", status="
								+ iTestResult.getStatus() + ", count=" + count + ", retryLimit=" + RETRY_LIMIT,
						iTestResult.getThrowable());
				isRetry = true;
			} else {
				logger.warn("Reached retry limit for test, methodName=" + iTestResult.getMethod().getMethodName()
						+ ", status=" + iTestResult.getStatus() + ", count=" + count + ", retryLimit=" + RETRY_LIMIT,
						iTestResult.getThrowable());
			}
		}
		return isRetry;
	}
}
